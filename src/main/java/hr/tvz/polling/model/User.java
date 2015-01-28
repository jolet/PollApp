package hr.tvz.polling.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "POL_USER")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonIgnore
//	@JsonManagedReference
//	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "user")
	private List<Activity> activities;
	
	@ManyToOne
	@JoinColumn(name = "ROL_ID")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name = "CGR_ID")
	private ClassGroup classGroup;
		
	@Id
	@Column(name = "USR_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "USR_EMAIL")
	private String email;
	@Column(name = "USR_FIRSTNAME")
	private String firstName;
	@Column(name = "USR_LASTNAME")
	private String lastName;
	@Column(name = "USR_PASSWORD")
	private String password;
	@Column(name = "USR_ACTIVE")
	private Boolean active;
	@Column(name = "USR_RESETTOKEN")
	private String resetToken;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public Boolean isActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
}
