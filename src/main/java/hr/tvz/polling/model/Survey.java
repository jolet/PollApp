package hr.tvz.polling.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "POL_SURVEY")
public class Survey implements Serializable{

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "survey")
	private List<Option> options;

	@ManyToOne
	@JoinColumn(name = "CGR_ID")
	private ClassGroup classGroup;
	
	@Id
	@Column(name = "SRV_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "SRV_ACTIVE")
	private Boolean active;
	
	@Column(name = "SRV_HAS_POINTS")
	private Boolean hasPoints;
	
	@Column(name = "SRV_MAX_VOTES")
	private Long maxVotes;
	
	@Column(name = "SRV_QUESTION")
	private String question;
	
	@Column(name = "SRV_VALID_FROM")
	private Date validFrom;
	
	@Column(name = "SRV_VALID_TO")
	private Date validTo;

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getHasPoints() {
		return hasPoints;
	}

	public void setHasPoints(Boolean hasPoints) {
		this.hasPoints = hasPoints;
	}

	public Long getMaxVotes() {
		return maxVotes;
	}

	public void setMaxVotes(Long maxVotes) {
		this.maxVotes = maxVotes;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	
	
	
	
}
