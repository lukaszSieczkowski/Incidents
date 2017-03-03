package pl.incidents.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;
import pl.incidents.model.enums.UserType;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 6299960691116250859L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", nullable = false)
	private long id;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "user_type", nullable = false)
	private UserType userType;
	@Column(name = "bad_login_number", nullable = false)
	private int badLoginNumber;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "surname", nullable = false)
	private String surname;

	
	public User(){
		
	}
	public User(long id, String email, String password, UserType userType, int badLoginNumber, String name,
			String surname) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.userType = userType;
		this.badLoginNumber = badLoginNumber;
		this.name = name;
		this.surname = surname;
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

	public int getBadLoginNumber() {
		return badLoginNumber;
	}

	public void setBadLoginNumber(int badLoginNumber) {
		this.badLoginNumber = badLoginNumber;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
