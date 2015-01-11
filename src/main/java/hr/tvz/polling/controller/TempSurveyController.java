package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.bll.interfaces.OptionManager;
import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.model.Activity;
import hr.tvz.polling.model.Survey;
import hr.tvz.polling.model.User;

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
@RequestMapping("/tempSurveyRun")
public class TempSurveyController {
	private static Logger LOG = LoggerFactory.getLogger(TempSurveyController.class);
	
	@Autowired
	private SurveyManager surveyManager;

	@Autowired
	private OptionManager optionManager;

	@Autowired
	private ActivityManager activityManager;

	@RequestMapping("/activeSurveys")
	public @ResponseBody List<Survey> getActiveAnketaList() {

		// TODO: get surveys only for users group
		return surveyManager.findAllActive(true);
	}

	@RequestMapping(value = "/sendAnswer/{optionSurveyId}", method = RequestMethod.POST)
	public @ResponseBody void getAnswer(@PathVariable String optionSurveyId) {
		// anketaStudentiServiceImpl.updateResults(answer);
		String[] toSplit = optionSurveyId.split(",");
		LOG.debug("answered: " + toSplit[0] + " - " + toSplit[1]);

		// TODO: add check if already voted
		User noobUser = new User();
		noobUser.setEmail("noob@user.com");
		noobUser.setId(1337L);

		Activity activity = new Activity();
		activity.setTimestamp(new Date());
		activity.setOption(optionManager.findOne(Long.parseLong(toSplit[0])));
		activity.setUser(noobUser);

		activityManager.saveAndFlush(activity);
	}

	@RequestMapping("/layout")
	public String getPartialPage(ModelMap modelMap) {
		return "tempSurveyRun/tempSurveyRunLayout";
	}
}
