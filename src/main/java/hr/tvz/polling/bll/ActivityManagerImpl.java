package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.bll.interfaces.OptionManager;
import hr.tvz.polling.dal.ActivityRepository;
import hr.tvz.polling.model.Activity;
import hr.tvz.polling.model.Option;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityManagerImpl implements ActivityManager{

	@Autowired
	ActivityRepository repository;
	
	@Autowired
	OptionManager optionManager;
	
	@Override
	public List<Activity> findAll() {
		return repository.findAll();
	}

	@Override
	public Activity findOne(Long id) {
		return repository.findOne(id);
	}

	//XXX: synchronized?
	@Override
	public synchronized void saveAndFlush(Activity activity) {
		
		Option toIncrement = optionManager.findOne(activity.getOption().getId());
		toIncrement.setCount(toIncrement.getCount()+1L);
		toIncrement = optionManager.saveAndFlushAndReturn(toIncrement);
		
		activity.setOption(toIncrement);
		repository.saveAndFlush(activity);
		
	}

	@Override
	public boolean checkAlreadyVoted(Long surveyId, Long userId) {
		//XXX: turn comparison for prod mode >
		return repository.checkAlreadyVoted(surveyId, userId) > 0 ? true : false;
	}
	
}
