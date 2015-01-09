package hr.tvz.polling.service.interfaces;

import hr.tvz.polling.model.Anketa;

import java.util.List;


public interface AnketaStudentiService {

	public List<Anketa> getAktivneAnkete();
	public List<Integer> updateResults(String option);
	
}
