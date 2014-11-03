package hr.tvz.polling.controller;

import hr.tvz.polling.model.Anketa;
import hr.tvz.polling.service.AnketaActivationServiceImpl;

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
@RequestMapping("/anketaActivation")
public class AnketaActivationController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AnketaActivationController.class);

	@Autowired
	AnketaActivationServiceImpl anketaActivationServiceImpl;

	@RequestMapping("/anketeList.json")
	public @ResponseBody List<Anketa> getAnketaActivationList() {
		return anketaActivationServiceImpl.getAnketeList();
	}

	@RequestMapping(value = "/statusSwitch/{nazivAnkete}", method = RequestMethod.POST)
	public @ResponseBody void addAnketaQuestion(@PathVariable String nazivAnkete) {
		anketaActivationServiceImpl.switchStatus(nazivAnkete);

	}

	@RequestMapping(value = "/deleteAnketa/{nazivAnkete}", method = RequestMethod.POST)
	public @ResponseBody void deleteAnketa(@PathVariable String nazivAnkete) {
		anketaActivationServiceImpl.deleteAnketa(nazivAnkete);
	}

	@RequestMapping(value = "/exampleAnkete", method = RequestMethod.POST)
	public @ResponseBody void getMockAnkete() {
		anketaActivationServiceImpl.getExampleAnkete();
	}

	@RequestMapping("/layout")
	public String getAnketaActivationPartialPage(ModelMap modelMap) {
		return "anketaActivation/anketaActivationLayout";
	}

}
