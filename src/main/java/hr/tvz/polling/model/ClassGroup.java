package hr.tvz.polling.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POL_CLASS_GROUP")
public class ClassGroup implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CGR_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CGR_NAME")
	private String name;
	
	@Column(name = "CGR_ACADEMIC_YEAR")
	private String academicYear;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	
}
