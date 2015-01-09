package hr.tvz.polling.service.interfaces;

import hr.tvz.polling.model.Anketa;

import java.util.List;

public interface AnketaActivationService {


	public List<Anketa> getAnketeList();
	public void switchStatus(String anketaNaziv);
	public void deleteAnketa(String nazivAnkete);
}
