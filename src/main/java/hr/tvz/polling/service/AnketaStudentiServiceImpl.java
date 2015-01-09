package hr.tvz.polling.service;

import hr.tvz.polling.model.Anketa;
import hr.tvz.polling.service.interfaces.AnketaStudentiService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("anketaStudentiService")
public class AnketaStudentiServiceImpl implements AnketaStudentiService{

	private static List<Anketa> aktivneAnkete = new ArrayList<>();
	
	@Override
	public List<Anketa> getAktivneAnkete() {
		
		aktivneAnkete.clear();
		
		//MOCK VALUES HERE
		for(Anketa ank : AnketaTemplateServiceImpl.getMockAnkete()) {
			if(ank.isActive()) {
				aktivneAnkete.add(ank);
			}
		}
		return aktivneAnkete;
	}
	
	@Override
	public List<Integer> updateResults(String option) {
		for(Anketa ank : aktivneAnkete) {
			if(ank.getOption1().contains(option))
			ank.updateResults(option);
			ank.addVote();
			break;

		}
	
		return null;
	}

	public static List<Anketa> getMockAktivneAnkete(){
		return aktivneAnkete;
		
	}




}
