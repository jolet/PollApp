package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.model.Survey;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/activation")
public class ActivationController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ActivationController.class);

	@Autowired
	SurveyManager surveyManager;

	@RequestMapping("/surveys")
	public @ResponseBody List<Survey> getAnketaActivationList() {
		return surveyManager.findAll();
	}

	@RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
	public @ResponseBody void deleteAnketa(@PathVariable String id) {
		// Survey toRemove = surveyManager.findOne(id);
		// toRemove.
		// surveyManager.saveAndFlush();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String saveSurvey(@RequestBody Survey surv) {
		surveyManager.saveAndFlush(surv);
		return "\"success\""; //"unExpected token lol. JSON data needs quotes around values or they can not be parsed back grrr.
//		return "JSON: Survey name: " + surv.getQuestion();
	}

	@RequestMapping("/layout")
	public String getAnketaListPartialPage(ModelMap modelMap) {
		return "activation/activationLayout";
	}
}
