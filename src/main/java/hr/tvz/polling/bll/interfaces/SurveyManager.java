package hr.tvz.polling.bll.interfaces;

import hr.tvz.polling.model.Survey;

import java.util.List;


public interface SurveyManager extends BaseManager<Survey>{

	List<Survey> findAllActive(Boolean active);
//	void delete(Long id);

	List<Survey> findAllActiveValuesStripped();

	List<Survey> findAllActiveValuesStripped(String curentUsername);

	void sendToHistory(String surveyId);
}
