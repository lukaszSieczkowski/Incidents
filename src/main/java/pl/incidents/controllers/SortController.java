package pl.incidents.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.incidents.components.IncidentList;
import pl.incidents.components.UserList;
import pl.incidents.model.Incident;
import pl.incidents.model.User;

@Controller
public class SortController {

	private IncidentList incidentsList;
	private UserList usersList;

	@Autowired
	public SortController(IncidentList incidentsList, UserList usersList) {
		this.incidentsList = incidentsList;
		this.usersList = usersList;
	}

	@RequestMapping("/sortByIncidentDate")
	public String sortByIncudentDate(Model model, String param) {
		
		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getIncidentDate().compareTo(b.getIncidentDate())))
				.collect(Collectors.toList());
	
		if (param.equals("asc")) {
			Collections.reverse(incidents);
		}
		
		incidentsList.setIncidents(incidents);
		model.addAttribute("incidents", incidentsList.getIncidents());
		
		return "showIncidents";
	}

	@RequestMapping("/sortByArea")
	public String sortByArea(Model model, String param) {
		
		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getArea().compareTo(b.getArea())))
				.collect(Collectors.toList());
		
		if (param.equals("asc")) {
			Collections.reverse(incidents);
		}
		
		incidentsList.setIncidents(incidents);
		model.addAttribute("incidents", incidentsList.getIncidents());
		
		return "showIncidents";
	}

	@RequestMapping("/sortByEvent")
	public String sortByEvent(Model model, String param) {
		
		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getTypeOfObservation().compareTo(b.getTypeOfObservation())))
				.collect(Collectors.toList());
		
		if (param.equals("asc")) {
			Collections.reverse(incidents);
		}
		
		incidentsList.setIncidents(incidents);
		model.addAttribute("incidents", incidentsList.getIncidents());
		
		return "showIncidents";
	}

	@RequestMapping("/sortByPersonel")
	public String sortByPersonel(Model model, String param) {
		
		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream()
				.sorted((a, b) -> (a.getCathegoryOfPersonel().compareTo(b.getCathegoryOfPersonel())))
				.collect(Collectors.toList());
		
		if (param.equals("asc")) {
			Collections.reverse(incidents);
		}
		
		incidentsList.setIncidents(incidents);
		model.addAttribute("incidents", incidentsList.getIncidents());
		
		return "showIncidents";
	}

	@RequestMapping("/sortBySupervisorInf")
	public String sortBySupervisorInfl(Model model, String param) {
		
		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream()
				.sorted((a, b) -> (a.getSupervisorInformed().compareTo(b.getSupervisorInformed())))
				.collect(Collectors.toList());
		
		if (param.equals("asc")) {
			Collections.reverse(incidents);
		}

		incidentsList.setIncidents(incidents);
		model.addAttribute("incidents", incidentsList.getIncidents());
		
		return "showIncidents";
	}

	@RequestMapping("/sortByReportingDate")
	public String sortByReportingtDate(Model model, String param) {
		
		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getIncidentDate().compareTo(b.getIncidentDate())))
				.collect(Collectors.toList());
		
		if (param.equals("asc")) {
			Collections.reverse(incidents);
		}

		incidentsList.setIncidents(incidents);
		model.addAttribute("incidents", incidentsList.getIncidents());
		
		return "showIncidents";
	}

	@RequestMapping("/sortByReporter")
	public String sortByReporter(Model model, String param) {
		
		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getUser().getSurname().compareTo(b.getUser().getSurname())))
				.collect(Collectors.toList());

		if (param.equals("asc")) {
			Collections.reverse(incidents);
		}
		
		incidentsList.setIncidents(incidents);
		model.addAttribute("incidents", incidentsList.getIncidents());
		
		return "showIncidents";
	}

	@RequestMapping("/sortByUserSurname")
	public String sortByUserSurnameDesc(Model model, String param) {
		
		List<User> users = usersList.getUsers();
		users = users.stream().sorted((a, b) -> (a.getSurname().compareTo(b.getSurname())))
				.collect(Collectors.toList());
		
		if (param.equals("asc")) {
			Collections.reverse(users);
		}
		
		usersList.setUsers(users);
		model.addAttribute("users", usersList.getUsers());
		
		return "showUsers";
	}

	@RequestMapping("/sortByUserType")
	public String sortByUserType(Model model, String param) {
		
		List<User> users = usersList.getUsers();
		users = users.stream().sorted((a, b) -> (a.getUserType().compareTo(b.getUserType())))
				.collect(Collectors.toList());
		
		if (param.equals("asc")) {
			Collections.reverse(users);
		}
		
		usersList.setUsers(users);
		model.addAttribute("users", usersList.getUsers());
		
		return "showUsers";
	}

	@RequestMapping("/sortByUserActive")
	public String sortByUserActive(Model model, String param) {
		
		List<User> users = usersList.getUsers();
		users = users.stream().sorted((a, b) -> (a.getUserActive().compareTo(b.getUserActive())))
				.collect(Collectors.toList());
		
		if (param.equals("asc")) {
			Collections.reverse(users);
		}
		
		usersList.setUsers(users);
		model.addAttribute("users", usersList.getUsers());
		
		return "showUsers";
	}
}
