package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.bll.interfaces.OptionManager;
import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.model.Activity;
import hr.tvz.polling.model.Option;
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
		return surveyManager.findAllActiveValuesStripped();
	}

	@RequestMapping(value = "/sendAnswer/{optionId}", method = RequestMethod.POST)
	public @ResponseBody void getAnswer(@PathVariable String optionId) {
		LOG.debug("answered option Id: " + optionId);
		//XXX: add check StringUtils.isNumeric
		Long answerId = Long.parseLong(optionId);
		Option answer = optionManager.findOne(answerId);
		
		if(answer != null){
			
		}
		User noobUser = new User();
		noobUser.setEmail("noob@user.com");
		noobUser.setId(1337L);

		if (activityManager.checkAlreadyVoted(answer.getSurvey().getId(), noobUser.getId())) {
			LOG.info("Noob Shall Not Pass!"); 
		} else {
			//TODO: check numeric
			//IDEA: check if JS was tampered, expected vs actual. i.e expecting not null
			// challenge : if multiple ppl pull option, option counters will have wrong values
			// should be synchronized or something
			LOG.info("User "+ noobUser.getId() +" voted, option id: " + optionId + " surveyId: " + answer.getSurvey().getId());
			Activity activity = new Activity();
			activity.setTimestamp(new Date());
			activity.setOption(answer);
			activity.setUser(noobUser);

			activityManager.saveAndFlush(activity);
		}

	}

	@RequestMapping("/layout")
	public String getPartialPage(ModelMap modelMap) {
		return "tempSurveyRun/tempSurveyRunLayout";
	}
}
