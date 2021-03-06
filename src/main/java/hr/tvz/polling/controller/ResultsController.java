package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.model.Survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/results")
public class ResultsController {
	
	@Autowired
	private SurveyManager surveyManager;

	@Autowired
	private ActivityManager activityManager;
	
	@RequestMapping("/activeSurveys")
	public @ResponseBody List<Survey> fetchActive() {
		List<Survey> activeSurveys = surveyManager.findAllActive(true);
		return activeSurveys;
	}

	@RequestMapping(value="/votingUsers/{optionId}", method=RequestMethod.GET)
	public @ResponseBody String fetchVotingUsers(@PathVariable String optionId) {
		return activityManager.findWhoVoted(optionId);
	}

	@RequestMapping("/layout")
	public String getPartialPage(ModelMap modelMap) {
		return "results/resultsLayout";
	}
}
