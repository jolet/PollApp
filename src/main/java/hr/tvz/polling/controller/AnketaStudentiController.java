 package hr.tvz.polling.controller;

import hr.tvz.polling.model.Anketa;
import hr.tvz.polling.service.AnketaStudentiServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/anketaStudenti")
public class AnketaStudentiController {

	@Autowired
	private AnketaStudentiServiceImpl anketaStudentiServiceImpl;
	
	@RequestMapping("/aktivneAnkete.json")
	public @ResponseBody List<Anketa> getActiveAnketaList() {

		return anketaStudentiServiceImpl.getAktivneAnkete();
	}
	@RequestMapping(value = "/sendAnswer/{answer}", method = RequestMethod.POST)
	public @ResponseBody void getAnswer(@PathVariable String answer) {
		anketaStudentiServiceImpl.updateResults(answer);
		
	}
	@RequestMapping("/layout")
	public String getPartialPage(ModelMap modelMap) {
		return "anketaStudenti/anketaStudentiLayout";
	}
	
}
