package hr.tvz.polling.bll;

import java.util.List;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.dal.ActivityRepository;
import hr.tvz.polling.model.Activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityManagerImpl implements ActivityManager{

	@Autowired
	ActivityRepository repository;
	
	@Override
	public List<Activity> findAll() {
		return repository.findAll();
	}

	@Override
	public Activity findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void saveAndFlush(Activity entity) {
		repository.saveAndFlush(entity);
	}

	@Override
	public boolean checkAlreadyVoted(Long surveyId, Long userId) {
//		if(repository.checkAlreadyVoted(surveyId, userId) == 0){
//			return true;
//		}
		return false;
	}
	
}
