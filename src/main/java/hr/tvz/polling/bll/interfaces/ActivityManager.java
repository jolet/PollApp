package hr.tvz.polling.bll.interfaces;

import hr.tvz.polling.model.Activity;

public interface ActivityManager extends BaseManager<Activity> {

	boolean checkAlreadyVoted(Long optionId, Long userId);

}
