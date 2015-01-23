package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.bll.interfaces.ClassGroupManager;
import hr.tvz.polling.bll.interfaces.OptionManager;
import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.bll.interfaces.UserManager;
import hr.tvz.polling.dal.SurveyRepository;
import hr.tvz.polling.model.ClassGroup;
import hr.tvz.polling.model.Option;
import hr.tvz.polling.model.Survey;
import hr.tvz.polling.model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SurveyManagerImpl  implements SurveyManager{

	@Autowired
	SurveyRepository repository;
	
	@Autowired
	ClassGroupManager classGroupManager;
	
	@Autowired
	OptionManager optionManager;
	
	@Autowired
	UserManager userManager;
	
	@Autowired
	ActivityManager activityManager;
	
	@Override
	public List<Survey> findAll() {
		return repository.findAll();
	}

	@Override
	public Survey findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional (readOnly = false)
	public void saveAndFlush(Survey survey) {
		
		//TODO: remove duplicates
		
//		classGroupManager.saveAndFlush(survey.getClassGroup());
		ClassGroup cgr = classGroupManager.saveAndFlushAndReturnCGR(survey.getClassGroup());
//		for(Option opt : survey.getOptions()) {
//		optionManager.saveAndFlush(opt);
//		}
		
		List<Option> op = survey.getOptions();
		survey.setOptions(null);
		survey.setClassGroup(cgr);
//		survey.setClassGroup(repo.find(xxx));
		repository.saveAndFlush(survey);
		
		for(Option opt : op) {
			opt.setSurvey(survey);
			optionManager.saveAndFlush(opt);
		}
	}

	@Override
	public List<Survey> findAllActive(Boolean active) {
		return repository.findAllByActive(active);
	}

	/**
	 * Strips values from active surveys which end user could potentially exploit, 
	 * i.e. removes option state (true/false), counter, and survey hint
	 */
	@Override
	public List<Survey> findAllActiveValuesStripped() {
		
		return prepareSurveys(repository.findAllByActive(true));
	}
	
	/**
	 * Strips values from active surveys which end user could potentially exploit, 
	 * i.e. removes option state (true/false), counter, and survey hint
	 * @param surveyList
	 * @return surveyList stripped from unnecessary risky data
	 */
	private List<Survey> prepareSurveys(List<Survey> surveyList) {
		for(Survey s : surveyList){
			for (Option o : s.getOptions()){
				o.setState(null);
				o.setCount(null);
			}
//			s.setHint(null);
		}
		
		return surveyList;
	}

	@Override
	public List<Survey> findAllActiveValuesStripped(String curentUsername) {
		User user = userManager.findByEmail(curentUsername);
		
//		return repository.findAllActiveForUser(user.getId(), user.getClassGroup().getId());
		List<Survey> surveys =  repository.findAllByActiveAndClassId(true, user.getClassGroup().getId());
		List<Survey> toRemove = new ArrayList<>();
		//check user already voted, don't load this survey then
		for(Survey s : surveys){
			if(activityManager.checkAlreadyVoted(s.getId(), user.getId())){
				toRemove.add(s);
			}
		}
		
		for(Survey remove : toRemove){
			surveys.remove(remove);
		}
		return prepareSurveys(surveys);
	}

//	@Override
//	public void delete(Long id) {
//		Survey srv =repository.findOne(id); 
//		if(srv ==null) {
//			System.out.println("Survey with id " + id + " doesn't exist");
//			return;
//		}
//		for(Option opt : srv.getOptions()) {
//			optionManager.delete(opt.getId());
//		}
//		
//		repository.delete(id);
//	}

}
