package hr.tvz.polling.service;

import hr.tvz.polling.model.Anketa;
import hr.tvz.polling.service.interfaces.AnketaResultsService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service("anketaResultsService")
public class AnketaResultsServiceImpl implements AnketaResultsService {

	// MOCK VALUES HERE
	private List<Anketa> anketeResultsList;

	@Override
	public List<Anketa> getAnketaResults() {

//		anketeResultsList = AnketaStudentiServiceImpl.getMockAktivneAnkete();
		anketeResultsList = AnketaActivationServiceImpl.getMockAktivneAnkete();

		return anketeResultsList;
	}

}
