package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.model.Survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/temp")
public class TempController {

	@Autowired
	SurveyManager surveyManager;
	
//	@RequestMapping("/ankete")
//	public @ResponseBody List<Survey> getSurveyList(){
//		return surveyManager.findAll();
//	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String saveSurvey(@RequestBody Survey surv) {
		System.out.println("anketa " + (surv == null ? "null" : surv.getQuestion()));

		surveyManager.saveAndFlush(surv);
		
		return "JSON: Survey name: " + surv.getQuestion();
	}
	
    @RequestMapping("/layout")
    public String getAnketaListPartialPage(ModelMap modelMap) {
        return "temp/tempLayout";
    }
}
