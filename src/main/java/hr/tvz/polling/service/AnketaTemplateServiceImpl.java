package hr.tvz.polling.service;

import hr.tvz.polling.model.Anketa;
import hr.tvz.polling.service.interfaces.AnketaTemplateService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("anketaTemplateService")
public class AnketaTemplateServiceImpl implements AnketaTemplateService {

	private String question = "";
	private List<String> listaOpcija = new ArrayList<>();
	private static List<Anketa> mockAnkete = new ArrayList<>();
	int maxAllowedVoters = 0;
	
	@Override
	public List<String> getOptionsList() {
		return listaOpcija;
	}

	@Override
	public void addOption(String option) {
		if(!listaOpcija.contains(option))
		listaOpcija.add(option);
	}

	@Override
	public void addQuestion(String question) {
		this.question = question; 
		
	}
	
	@Override
	public String getQuestion() {
		return question;
		
	}
	@Override
	public void removeOption(String option) {
		listaOpcija.remove(option);
	}
	@Override
	public void removeAllOptions() {
		listaOpcija.clear();
	}

	/**
	 * @return the mockAnkete
	 */
	public static List<Anketa> getMockAnkete() {
		return mockAnkete;
	}

	/**
	 * @param mockAnkete the mockAnkete to set
	 */
	@SuppressWarnings("static-access")
	public void setMockAnkete(List<Anketa> mockAnkete) {
		this.mockAnkete = mockAnkete;
		//TODO: static access
	}
	
	public void addMockAnketa(Anketa mockAnketa) {
		for(Anketa ank : mockAnkete) {
			if(ank.getQuestion1().equals(mockAnketa.getQuestion1())) {
				ank.setOption1(mockAnketa.getOption1());
				return;
			}
		}
		AnketaTemplateServiceImpl.mockAnkete.add(mockAnketa);
	}
	
	public Anketa createMockAnketa(String question, List<String> options, int maxVotes){
		Anketa tempAnketa = new Anketa();
		tempAnketa.setQuestion1(question);
		tempAnketa.setOption1(options);
		tempAnketa.setInitialMaxAllowedVoters(maxVotes);
		return tempAnketa;
	}

	public void deleteQuestion() {
		question = "";
		
	}

	public void setMaxAllowedVoters(int maxVotes) {
		this.maxAllowedVoters = maxVotes;
		
	}

	/**
	 * @return the maxAllowedVoters
	 */
	public int getMaxAllowedVoters() {
		return maxAllowedVoters;
	}


}
