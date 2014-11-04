package hr.tvz.polling.controller;

import hr.tvz.polling.model.Survey;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/temp")
public class TempController {

	@RequestMapping("/ankete")
	public @ResponseBody List<Survey> getAnketaOptionsList(){
		Survey s1 = new Survey();
		s1.setQuestion("test1");
		Survey s2 = new Survey();
		s2.setQuestion("test2");
		List<Survey> srvy = new ArrayList<>();
		srvy.add(s1);
		srvy.add(s2);
		
		return srvy;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String saveSurvey(@RequestBody Survey surv) {
		System.out.println("anketa " + (surv == null ? "mull" : surv.getQuestion()));
		
		return "JSON: Survey name: " + surv.getQuestion();
		
	}
	
    @RequestMapping("/layout")
    public String getAnketaListPartialPage(ModelMap modelMap) {
        return "temp/tempLayout";
    }
}
