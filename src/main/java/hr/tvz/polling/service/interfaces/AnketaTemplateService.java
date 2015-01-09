package hr.tvz.polling.service.interfaces;

import java.util.List;

public interface AnketaTemplateService {
	
		public void addQuestion(String question);
		public String getQuestion();
		public void deleteQuestion();
		public void addOption(String option);
		public List<String> getOptionsList();
		public void removeOption(String option);
		public void removeAllOptions();
}
