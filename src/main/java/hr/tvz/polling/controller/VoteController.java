package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.bll.interfaces.OptionManager;
import hr.tvz.polling.bll.interfaces.SecurityRealm;
import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.bll.interfaces.UserManager;
import hr.tvz.polling.controller.util.Constants;
import hr.tvz.polling.controller.util.HttpResponsePayloadWrapper;
import hr.tvz.polling.controller.util.SurveyLog;
import hr.tvz.polling.model.Activity;
import hr.tvz.polling.model.Option;
import hr.tvz.polling.model.Survey;
import hr.tvz.polling.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/vote")
public class VoteController {
	private static Logger LOG = LoggerFactory.getLogger(VoteController.class);

	@Autowired
	private SurveyManager surveyManager;

	@Autowired
	private OptionManager optionManager;

	@Autowired
	private ActivityManager activityManager;

	@Autowired
	private SecurityRealm secRealm;

	@Autowired
	UserManager userManager;

	/**
	 * survey list with "locked" surveys replaced with "secret key input"
	 * 
	 * @return
	 */
	@RequestMapping("/activeSurveys")
	public @ResponseBody List<Survey> getActiveSurveyList() {
		// XXX: TODO: return if statement for prod mode, admin should not see locked surveys

		// if (secRealm.hasRole(Constants.ROLE_ADMIN)) {
		// return getRawSurveyListForUser();
		// } else if (secRealm.hasRole(Constants.ROLE_USER)) {
		List<Survey> surveysStripped = new ArrayList<>();
		for (Survey s : getRawSurveyListForUser()) {
			if (s.getHint() == null) {
				surveysStripped.add(s);
			} else {
				surveysStripped.add(null);
			}
		}

		return surveysStripped;
		// }
		// return null;
	}

	private List<Survey> getRawSurveyListForUser() {
		List<Survey> surveyList = null;
		if (secRealm.hasRole(Constants.ROLE_ADMIN)) {
			// surveyList = surveyManager.findAllActiveValuesStripped(); // for debugging purposes
			surveyList = surveyManager.findAllActiveValuesStripped();
		} else if (secRealm.hasRole(Constants.ROLE_USER)) {
			surveyList = surveyManager.findAllActiveValuesStripped(secRealm.getCurentUsername());
		}
		return surveyList;
	}

	@RequestMapping(value = "/sendAnswer/{optionId}", method = RequestMethod.POST)
	public @ResponseBody void getAnswer(@PathVariable String optionId) {
		LOG.debug("answered option Id: " + optionId);
		// XXX: add check StringUtils.isNumeric

		if (secRealm.hasRole(Constants.ROLE_USER)) {

			Long answerId = Long.parseLong(optionId);
			Option answer = optionManager.findOne(answerId);

			if (answer != null) {
				User user = userManager.findByEmail(secRealm.getCurentUsername());

				if (activityManager.checkAlreadyVoted(answer.getSurvey().getId(), user.getId())) {
					LOG.info(SurveyLog.userLog("Shall Not Pass! (already voted for survey: " + "["
							+ answer.getSurvey().getQuestion() + ")]"));
				} else {
					LOG.info(SurveyLog.userLog("voted, survey: " + "[" + answer.getSurvey().getQuestion()
							+ "], " + "option: " + answer.getName()));

					Activity activity = new Activity();
					activity.setTimestamp(new Date());
					activity.setOption(answer);
					activity.setUser(user);

					activityManager.saveAndFlush(activity);
				}
			}
		} else if (secRealm.hasRole(Constants.ROLE_ADMIN)) {
			LOG.info(SurveyLog.userLog("Shall Not Pass!!! ooops."));
		}
	}

	@RequestMapping(value = "/unlockSurvey/{key}", method = RequestMethod.POST)
	public @ResponseBody HttpResponsePayloadWrapper unlockSurvey(@PathVariable String key) {
		boolean correctKey = false;
		List<Survey> unlockedSurveys = new ArrayList<>();
		for (Survey s : getRawSurveyListForUser()) {
			if (s.getHint() == null) {
				unlockedSurveys.add(s);
			} else if (s.getHint().equals(key)) {
				LOG.info(secRealm.getCurentUsername() + " unlocked survey " + s.getQuestion());
				correctKey = true;
				unlockedSurveys.add(s);
			} else {
				unlockedSurveys.add(null);
			}
		}
		return new HttpResponsePayloadWrapper(correctKey, unlockedSurveys);
	}

	@RequestMapping("/layout")
	public String getPartialPage(ModelMap modelMap) {
		return "vote/voteLayout";
	}
}
