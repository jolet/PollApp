package hr.tvz.polling.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//@Entity
//@Table(name="POL_ANKETA")
public class Anketa {

//	@Id
//	@Column(name="ANK_ID")
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SuppressWarnings("unused")
	private Long id;
	
//	@Column(name="ANK_QUESTION")
	String question1; //, option1;
	
//	@Column(name="ANK_ACTIVE")
	Boolean active = false; //da li je anketa dostupna za rješavanje
	
//	@Column(name="ANK_MAX_VOTES")
	int initialMaxAllowedVoters = -1; // maksimalni broj dozvoljenih glasovanja
	
	
	Map<String, Integer> optionResults;
	
	List<String> options; //Lista mogućih odgovora na anketu
	List<Integer> answersResults; //Lista koja prati koliko puta je odabrana koja opcija

	int tempMaxAllowedVoters = -1; //counter za glasovanje
	
	public Anketa() {
	}
	public Anketa(Long id, String question, Boolean active) {
		this.id = id;
		this.question1 = question;
		this.active = active;
	}
	
	public Anketa(String question, List<String> options) {
		question1 = question;
		this.options = options; 
	}
	/**
	 * @return the question1
	 */
	public String getQuestion1() {
		return question1;
	}

	/**
	 * @param question1 the question1 to set
	 */
	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	/**
	 * @return the option1
	 */
	public List<String> getOption1() {
		return options;
	}

	/**
	 * @param option1 the option1 to set
	 */
	public void setOption1(List<String> option1) {
		this.options = option1;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.active = isActive;
	}
	
	@Override
		public String toString() {
			String toOptions = "";
			if(options == null) {
				return "empty poll";
			}
			for(String opt : options) {
				toOptions+=opt+" ";
			}
			return "Question: " + question1 + "\noptions: [" + toOptions + " ]";
		}

	/**
	 * @return the answersResults
	 */
	public List<Integer> getAnswersResults() {
		return answersResults;
	}

	/**
	 * @param answersResults the answersResults to set
	 */
	public void setAnswersResults(List<Integer> answersResults) {
		this.answersResults = answersResults;
	}
	
	public void updateResults(String optionResult) {
		if(answersResults == null) {
			Integer[] emptyArray = new Integer[options.size()];
			Arrays.fill(emptyArray, 0);
			answersResults = new ArrayList<Integer>(Arrays.asList(emptyArray));
			
		}
		int posToUpdate = options.indexOf(optionResult);
		
//		System.out.println("Value before: " + answersResults.get(posToUpdate));
		answersResults.set(posToUpdate, answersResults.get(posToUpdate)+1);
//		System.out.println("After: " + answersResults.get(posToUpdate));
	}

	/**
	 * @return the maxAllowedVoters
	 */
	public int getInitialMaxAllowedVoters() {
		return initialMaxAllowedVoters;
	}

	/**
	 * @param maxAllowedVoters the maxAllowedVoters to set
	 */
	public void setInitialMaxAllowedVoters(int maxAllowedVoters) {
		this.initialMaxAllowedVoters = maxAllowedVoters;
		this.tempMaxAllowedVoters = maxAllowedVoters;
	}
	
	/**
	 * @return the initialMaxAllowedVoters
	 */
	public int getTempMaxAllowedVoters() {
		return tempMaxAllowedVoters;
	}
	
	public void addVote() {
		--tempMaxAllowedVoters;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + initialMaxAllowedVoters;
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result
				+ ((question1 == null) ? 0 : question1.hashCode());
		result = prime * result + tempMaxAllowedVoters;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Anketa)) {
			return false;
		}
		Anketa other = (Anketa) obj;

		if (initialMaxAllowedVoters != other.initialMaxAllowedVoters) {
			return false;
		}
		if (options == null) {
			if (other.options != null) {
				return false;
			}
		} else if (!options.equals(other.options)) {
			return false;
		}
		if (question1 == null) {
			if (other.question1 != null) {
				return false;
			}
		} else if (!question1.equals(other.question1)) {
			return false;
		}
		if (tempMaxAllowedVoters != other.tempMaxAllowedVoters) {
			return false;
		}
		return true;
	}
	
}
