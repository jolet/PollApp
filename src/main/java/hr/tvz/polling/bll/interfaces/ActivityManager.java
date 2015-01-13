package hr.tvz.polling.bll.interfaces;

import hr.tvz.polling.model.Activity;

public interface ActivityManager extends BaseManager<Activity> {

	/**
	 * Check if user already voted on this survey.
	 * @param surveyId
	 * @param userId
	 * @return true if already voted
	 */
	boolean checkAlreadyVoted(Long surveyId, Long userId);

}
