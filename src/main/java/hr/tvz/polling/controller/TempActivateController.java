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
@RequestMapping("/tempActivate")
public class TempActivateController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TempActivateController.class);

	@Autowired
	SurveyManager surveyManager;
	
	@RequestMapping("/surveys")
	public @ResponseBody List<Survey> getAnketaActivationList() {
		return surveyManager.findAll();
	}

//	@RequestMapping(value = "/statusSwitch/{nazivAnkete}", method = RequestMethod.POST)
//	public @ResponseBody void addAnketaQuestion(@PathVariable String nazivAnkete) {
//		anketaActivationServiceImpl.switchStatus(nazivAnkete);
//
//	}
//
//	@RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
//	public @ResponseBody void deleteAnketa(@PathVariable String id) {
//		surveyManager.delete(Long.parseLong(id));
//	}
//
//	@RequestMapping(value = "/exampleAnkete", method = RequestMethod.POST)
//	public @ResponseBody void getMockAnkete() {
//		anketaActivationServiceImpl.getExampleAnkete();
//	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String saveSurvey(@RequestBody Survey surv) {
//		System.out.println("anketa " + (surv == null ? "null" : surv.getQuestion()));

		surveyManager.saveAndFlush(surv);
		
		return "JSON: Survey name: " + surv.getQuestion();
	}
	
    @RequestMapping("/layout")
    public String getAnketaListPartialPage(ModelMap modelMap) {
        return "tempActivate/tempActivateLayout";
    }
}
