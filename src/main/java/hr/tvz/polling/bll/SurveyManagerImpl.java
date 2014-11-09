package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.SurveyManager;
import hr.tvz.polling.dal.SurveyRepository;
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
		repository.saveAndFlush(survey);
	}

}
