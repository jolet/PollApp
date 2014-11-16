package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.ClassGroupManager;
import hr.tvz.polling.bll.interfaces.OptionManager;
import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.dal.SurveyRepository;
import hr.tvz.polling.model.Option;
import hr.tvz.polling.model.Survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SurveyManagerImpl implements SurveyManager{

	@Autowired
	SurveyRepository repository;
	
	@Autowired
	ClassGroupManager classGroupManager;
	
	@Autowired
	OptionManager optionManager;
	
	@Override
	public List<Survey> findAll() {
		return repository.findAll();
	}

	@Override
	public Survey findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void saveAndFlush(Survey survey) {
		
		classGroupManager.saveAndFlush(survey.getClassGroup());
//		for(Option opt : survey.getOptions()) {
//		optionManager.saveAndFlush(opt);
//		}
		
		List<Option> op = survey.getOptions();
		survey.setOptions(null);
		repository.saveAndFlush(survey);
		
		for(Option opt : op) {
			opt.setSurvey(survey);
			optionManager.saveAndFlush(opt);
		}
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
