package hr.tvz.polling.controller;



import hr.tvz.polling.service.AnketaTemplateServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/anketaTemplate")
public class AnketaTemplateController {

	@Autowired
	public AnketaTemplateServiceImpl anketaTemplateServiceImpl;
	
	/**
	 * Anketa template
	 */
	@RequestMapping("/anketeList.json")
	public @ResponseBody List<String> getAnketaOptionsList(){
		return anketaTemplateServiceImpl.getOptionsList();
	}
	
    @RequestMapping(value = "/addOption/{option}", method = RequestMethod.POST)
    public @ResponseBody void addAnketaOption(@PathVariable("option") String option) {
        anketaTemplateServiceImpl.addOption(option);
    }
    @RequestMapping(value = "/saveAnketa")//, method = RequestMethod.POST)
    public @ResponseBody void saveAnketa() {	
    	String question = anketaTemplateServiceImpl.getQuestion();
    	List<String> options = anketaTemplateServiceImpl.getOptionsList();
    	int maxVotes = anketaTemplateServiceImpl.getMaxAllowedVoters();
    	anketaTemplateServiceImpl.addMockAnketa(anketaTemplateServiceImpl.createMockAnketa(question, options, maxVotes));
    }
    
    @RequestMapping(value = "/addQuestion/{question}", method = RequestMethod.POST)
    public @ResponseBody void addAnketaQuestion(@PathVariable String question) {
        anketaTemplateServiceImpl.addQuestion(question);
    }
    
    @RequestMapping(value = "/question")//, method = RequestMethod.POST)
    public @ResponseBody String getTemplateQuestion() {
        return anketaTemplateServiceImpl.getQuestion();
    }
    
    
    @RequestMapping(value = "/removeOption/{option}", method = RequestMethod.DELETE)
    public @ResponseBody void removeAnketaOption(@PathVariable String option) {
        anketaTemplateServiceImpl.removeOption(option);
    }
	
    @RequestMapping(value = "/removeAllOptions", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllOptions() {
        anketaTemplateServiceImpl.removeAllOptions();
    }
    
    @RequestMapping(value = "/maxAllowed/{maxVotes}", method = RequestMethod.POST)
    public @ResponseBody void setMaxAllowedVotes(@PathVariable int maxVotes) {
        anketaTemplateServiceImpl.setMaxAllowedVoters(maxVotes);
    }
    
    @RequestMapping("/layout")
    public String getAnketaListPartialPage(ModelMap modelMap) {
        return "anketaTemplate/anketaTemplateLayout";
    }
    
}
