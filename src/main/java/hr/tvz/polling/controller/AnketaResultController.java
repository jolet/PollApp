package hr.tvz.polling.controller;

import hr.tvz.polling.model.Anketa;
import hr.tvz.polling.service.AnketaResultsServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/anketaResults")
public class AnketaResultController {
	
	@Autowired
	private AnketaResultsServiceImpl anketaResultsServiceImpl;
	
	@RequestMapping("/results.json")
	public @ResponseBody List<Anketa> getAnketeResults(){

//		if(anketaResultsServiceImpl.getAnketaResults().size() != 0) {
//			System.out.println(anketaResultsServiceImpl.getAnketaResults().get(0).toString());
//		}
		return anketaResultsServiceImpl.getAnketaResults();
	}
	@RequestMapping("/layout")
	public String getAnketaResultsPartialPage(ModelMap modelMap) {
		return "anketaResults/anketaResultsLayout";
	}

}
