package hr.tvz.polling.bll.interfaces;

import java.util.List;

import hr.tvz.polling.controller.util.HttpResponsePayloadWrapper;
import hr.tvz.polling.model.Activity;

public interface ActivityManager extends BaseManager<Activity> {

	/**
	 * Check if user already voted on this survey.
	 * @param surveyId
	 * @param userId
	 * @return true if already voted
	 */
	boolean checkAlreadyVoted(Long surveyId, Long userId);

	Integer getUserPoints();

	List<HttpResponsePayloadWrapper> findAllByUserId(String userId);

	String findWhoVoted(String optionId);

}
