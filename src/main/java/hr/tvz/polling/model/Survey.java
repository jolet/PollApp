package hr.tvz.polling.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "POL_SURVEY")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Survey implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonManagedReference
	@OneToMany(mappedBy = "survey", fetch=FetchType.EAGER)
//	@OneToMany(mappedBy = "survey", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
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
	private Boolean scored;
	
	@Column(name = "SRV_MAX_VOTES")
	private Long maxVotes;
	
	@Column(name = "SRV_QUESTION")
	private String question;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SRV_VALID_FROM")
	private Date validFrom;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SRV_VALID_TO")
	private Date validTo;
	
	@Column(name = "SRV_DESCRIPTION")
	private String description;

	public List<Option> getOptions() {
		return options;
	}

	public Survey() {
		// TODO Auto-generated constructor stub
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

	public Boolean getScored() {
		return scored;
	}

	public void setScored(Boolean scored) {
		this.scored = scored;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
