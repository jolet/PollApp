package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.bll.interfaces.OptionManager;
import hr.tvz.polling.bll.interfaces.SecurityRealm;
import hr.tvz.polling.bll.interfaces.UserManager;
import hr.tvz.polling.controller.util.HttpResponsePayloadWrapper;
import hr.tvz.polling.dal.ActivityRepository;
import hr.tvz.polling.model.Activity;
import hr.tvz.polling.model.Option;

import java.util.ArrayList;
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
	
	@Autowired
	UserManager userManager;
	
	@Autowired
	SecurityRealm secRealm;
	
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
		//XXX: turn comparison, for prod mode >
		return repository.checkAlreadyVoted(surveyId, userId) > 0 ? true : false;
	}

	@Override
	public Integer getUserPoints() {
		Long userId = userManager.findIdByEmail(secRealm.getCurentUsername());
		return repository.getUserPoints(userId);
	}

	@Override
	public List<HttpResponsePayloadWrapper> findAllByUserId(String userId) {
		//TODO: XXX: long conversion check
		Long id = Long.valueOf(userId);
		List<Activity> activityList = repository.findAllByUserId(id);
		List<HttpResponsePayloadWrapper> activityListWrapper = new ArrayList<HttpResponsePayloadWrapper>();
		for(Activity act : activityList){
			activityListWrapper.add(new HttpResponsePayloadWrapper(act.getOption().getSurvey().getQuestion(), act));
		}
		return activityListWrapper;
	}

	@Override
	public String findWhoVoted(String optionId) {
		
		Long id = Long.valueOf(optionId);
		
		List<String> voters = repository.findWhoVoted(id);
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		for(String vote : voters){
			sb.append(vote).append(", ");
		}
		if(sb.indexOf(",")!= -1 ){
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("\"");
		return sb.toString();
	}
	
}
