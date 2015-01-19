package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.ClassGroupManager;
import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.model.ClassGroup;
import hr.tvz.polling.model.Survey;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/template")
public class TemplateController {
	private static Logger LOG = LoggerFactory.getLogger(TemplateController.class);

	@Autowired
	private SurveyManager surveyManager;

	@Autowired
	private ClassGroupManager classGroupManager;

	// @RequestMapping("/ankete")
	// public @ResponseBody List<Survey> getSurveyList(){
	// return surveyManager.findAll();
	// }

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String saveSurvey(@RequestBody Survey surv) {
		LOG.info("anketa " + (surv == null ? "null" : surv.getQuestion()));

		surveyManager.saveAndFlush(surv);

		return "JSON: Survey name: " + surv.getQuestion();
	}

	@RequestMapping("/classGroups")
	public @ResponseBody List<ClassGroup> getClassGroups() {
		return classGroupManager.findAll();
	}
	
	@RequestMapping("/layout")
	public String getAnketaListPartialPage(ModelMap modelMap) {
		return "template/templateLayout";
	}
}
