package hr.tvz.polling.service;

import hr.tvz.polling.model.Anketa;
import hr.tvz.polling.service.interfaces.AnketaActivationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("anketaActivationService")
public class AnketaActivationServiceImpl implements AnketaActivationService{
	
	private static List<Anketa> anketeActivationList;// = new ArrayList<Anketa>();
	
	@Override
	public List<Anketa> getAnketeList() {
		
		//MOCK VALUES HERE
		anketeActivationList = AnketaTemplateServiceImpl.getMockAnkete();
		return anketeActivationList;
	}

	public void switchStatus(String anketaNaziv) {
		for(Anketa ank : anketeActivationList) {
			if(ank.getQuestion1().equals(anketaNaziv)) {
				ank.setActive(!ank.isActive());
				break;
			}
		}
	}

	public void deleteAnketa(String nazivAnkete) {
		for(Anketa ank : anketeActivationList) {
			if(ank.getQuestion1().equals(nazivAnkete)) {
				anketeActivationList.remove(ank);
				break;
			}
		}
		
	}
	
	public List<Anketa> getMockAnkete(){
		return anketeActivationList;
	}
	
	public static List<Anketa> getMockAktivneAnkete(){
		
		List<Anketa> mockAktivneAnkete = new ArrayList<>();
		if(anketeActivationList == null) {
			anketeActivationList = new ArrayList<>();
		}
		for(Anketa ank : anketeActivationList) {
			if(ank.isActive()) {
				mockAktivneAnkete.add(ank);
			}
		}
		return mockAktivneAnkete;
	}

	public void getExampleAnkete() {
//		if(anketeActivationList.isEmpty()) {
			String[] opts1 = new String[]{"Javatar", "Otac Jave", "Sportski park", "Igrac BourneMoutha"};
			String[] opts2 = new String[]{"Da", "Ne", "Neznam"};
			String[] opts3 = new String[]{"javaLangThrowable", "Majka svih Exceptiona", "javaUtilException", "javaxUtilityCheckedException"};
//			String[] opts3 = new String[]{"le", "na", "on", "1on"};
			List<String> optionsMock1 = Arrays.asList(opts1);
			List<String> optionsMock2 = Arrays.asList(opts2);
			List<String> optionsMock3 = Arrays.asList(opts3);
			Anketa ank1 = new Anketa("Tko je James Gosling", optionsMock1);
			Anketa ank2 = new Anketa("Zelite li pisati blic sljedeci puta", optionsMock2);
			Anketa ank3 = new Anketa("Blic - Klasa Exception je (Restricted number of votes example)", optionsMock3);
			ank3.setInitialMaxAllowedVoters(4);
			
			
//			for(Anketa ank : anketeActivationList) {
//				if(ank.getQuestion1().equals())
//			}
			if(!anketeActivationList.contains(ank1)) 
				anketeActivationList.add(ank1);
			if(!anketeActivationList.contains(ank2))
				anketeActivationList.add(ank2);
			if(!anketeActivationList.contains(ank3))
				anketeActivationList.add(ank3);

//		}
		
	}
	
	

}
