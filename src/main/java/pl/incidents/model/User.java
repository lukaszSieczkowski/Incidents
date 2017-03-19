package pl.incidents.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import pl.incidents.model.enums.UserActive;
import pl.incidents.model.enums.UserType;

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 6299960691116250859L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", nullable = false)
	private long id;
	@Email(message = "It is not an email adress")
	@NotBlank(message = "This field may not be empty")
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Enumerated
	@NotNull (message ="Select at least one value")
	@Column(name = "user_type", nullable = false)
	private UserType userType;
	@NotBlank(message = "This field may not be empty")
	@Column(name = "name", nullable = false)
	private String name;
	@NotBlank(message = "This field may not be empty")
	@Column(name = "surname", nullable = false)
	private String surname;
	@Column(name = "userActive", nullable = false)
	private UserActive userActive;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Incident> incidents;

	public User() {

	}

	public User(String email, String password, UserType userType, String name, String surname, UserActive userActive) {
		this.email = email;
		this.password = password;
		this.userType = userType;
		this.name = name;
		this.surname = surname;
		this.userActive = userActive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public UserActive getUserActive() {
		return userActive;
	}

	public void setUserActive(UserActive userActive) {
		this.userActive = userActive;
	}

	public List<Incident> getIncidents() {
		return incidents;
	}

	public void setIncidents(List<Incident> incidents) {
		this.incidents = incidents;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", userType=" + userType + ", name="
				+ name + ", surname=" + surname + ", userActive=" + userActive + "]";
	}

}
