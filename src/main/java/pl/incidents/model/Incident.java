package pl.incidents.model;

import java.io.Serializable;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import pl.incidents.model.enums.Area;
import pl.incidents.model.enums.CathegoryOfPersonel;
import pl.incidents.model.enums.EventType;
import pl.incidents.model.enums.SupervisorInformed;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Entity
@Table(name = "incidents")
public class Incident implements Serializable {
	private static final long serialVersionUID = -2374742569817038350L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "incident_id", nullable = false)
	private long id;
	@Column(name = "incident_date", nullable = false)
	private LocalDateTime incidentDate;
	@Column(name = "reporting_date", nullable = false)
	private LocalDateTime reportingDate;
	@Enumerated
	@Column(name = "area", nullable = false)
	private Area area;
	@Column(name = "location", nullable = false, length = 500)
	private String location;
	@Enumerated
	@Column(name = "type_of_observation", nullable = false)
	private EventType typeOfObservation;
	@Enumerated
	@Column(name = "cathegory_of_personel", nullable = false)
	private CathegoryOfPersonel cathegoryOfPersonel;
	@Column(name = "details", nullable = false, length = 3000)
	private String details;
	@Column(name = "action", nullable = false, length = 3000)
	private String action;
	@Enumerated
	@Column(name = "supervisor_informed", nullable = false)
	private SupervisorInformed supervisorInformed;
	@ManyToOne
    @JoinColumn(name = "id_user")
	private User user;

	
	public Incident() {

	}

	public Incident(LocalDateTime incidentDate, LocalDateTime reportingDate, Area area, String location,
			EventType typeOfObservation, CathegoryOfPersonel cathegoryOfPersonel, String details, String action,
			SupervisorInformed supervisorInformed, User user) {
		
		this.incidentDate = incidentDate;
		this.reportingDate = reportingDate;
		this.area = area;
		this.location = location;
		this.typeOfObservation = typeOfObservation;
		this.cathegoryOfPersonel = cathegoryOfPersonel;
		this.details = details;
		this.action = action;
		this.supervisorInformed = supervisorInformed;
		this.user = user;
	}

	


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDateTime incidentDate) {
		this.incidentDate = incidentDate;
	}

	public LocalDateTime getReportingDate() {
		return reportingDate;
	}

	public void setReportingDate(LocalDateTime reportingDate) {
		this.reportingDate = reportingDate;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public EventType getTypeOfObservation() {
		return typeOfObservation;
	}

	public void setTypeOfObservation(EventType typeOfObservation) {
		this.typeOfObservation = typeOfObservation;
	}

	public CathegoryOfPersonel getCathegoryOfPersonel() {
		return cathegoryOfPersonel;
	}

	public void setCathegoryOfPersonel(CathegoryOfPersonel cathegoryOfPersonel) {
		this.cathegoryOfPersonel = cathegoryOfPersonel;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public SupervisorInformed getSupervisorInformed() {
		return supervisorInformed;
	}

	public void setSupervisorInformed(SupervisorInformed supervisorInformed) {
		this.supervisorInformed = supervisorInformed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Incident [id=" + id + ", incidentDate=" + incidentDate + ", reportingDate=" + reportingDate + ", area="
				+ area + ", location=" + location + ", typeOfObservation=" + typeOfObservation
				+ ", cathegoryOfPersonel=" + cathegoryOfPersonel + ", details=" + details + ", action=" + action
				+ ", supervisorInformed=" + supervisorInformed + ", user=" + user + "]";
	}

	
	


}
