package pl.incidents.controllers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import pl.incidents.components.IncidentList;

import pl.incidents.dao.IncidentDao;
import pl.incidents.dao.IncidentDaoImplementation;
import pl.incidents.dao.UserDao;
import pl.incidents.dao.UserDaoImplementation;
import pl.incidents.model.Incident;
import pl.incidents.model.User;
import pl.incidents.model.enums.Area;
import pl.incidents.model.enums.CathegoryOfPersonel;
import pl.incidents.model.enums.EventType;
import pl.incidents.model.enums.SupervisorInformed;
import pl.incidents.model.enums.UserType;
import pl.incidents.utils.CreateDate;

@Controller
@SessionAttributes("user")
public class MainController {
	private IncidentList incidentList;

	@Autowired
	public MainController(IncidentList incidentList) {
		this.incidentList = incidentList;
	}

	@RequestMapping("/")
	public String showMain() {
		return "index";
	}

	@PostMapping("/login")
	public String showUser(@RequestParam String email, @RequestParam String password, Model model) {

		UserDao userDao = new UserDaoImplementation();
		IncidentDao incidentDao = new IncidentDaoImplementation();
		ArrayList<User> userList = (ArrayList<User>) userDao.getUsers();

		Optional<User> optionalUser = userList.stream().filter(a -> a.getEmail().equals(email)).findAny();

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.getPassword().equals(password)) {
				model.addAttribute("user", user);
				LocalDateTime date = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String formatedDate = date.format(formatter);
				model.addAttribute("date", formatedDate);
				List<Incident> incidents = incidentDao.getIncidents();
				incidentList.setIncidents(incidents);
				model.addAttribute("incidents", incidentList.getIncidents());
				
					return "showIncidents";
				}
				} else {
					String alert = "Incorrect Password !!!";
					model.addAttribute("alert", alert);
					return "index";
				}
		return  "index";

	//} else {
		//	String alert = "User does't exist !!!";
		//	model.addAttribute("alert", alert);
		//	return "index";
		//}
	}

	@RequestMapping("/logout")
	public String logout(WebRequest request, SessionStatus status) {
		status.setComplete();
		request.removeAttribute("user", WebRequest.SCOPE_SESSION);
		request.removeAttribute("date", WebRequest.SCOPE_SESSION);
		request.removeAttribute("incidents", WebRequest.SCOPE_SESSION);
		return "index";
	}

	@PostMapping("/saveIncident")
	public String saveIncident(@RequestParam String date, @RequestParam int hour, @RequestParam int minute,
			@RequestParam String area, @RequestParam String location, @RequestParam String typeOfObservation,
			@RequestParam String cathegoryOfPersonel, @RequestParam String details, @RequestParam String action,
			@RequestParam String supervisorInformed, Model model, @ModelAttribute User user) {

		IncidentDao incidentDao = new IncidentDaoImplementation();

		LocalDateTime reportingDate = LocalDateTime.now();

		CreateDate createDate = new CreateDate();
		LocalDateTime incidentDate = createDate.createDateFromRequest(date, hour, minute);

		Incident incident = new Incident(incidentDate, reportingDate, Area.valueOf(area), location,
				EventType.valueOf(typeOfObservation), CathegoryOfPersonel.valueOf(cathegoryOfPersonel), details, action,
				SupervisorInformed.valueOf(supervisorInformed), user);

		incidentDao.saveIncident(incident);
		model.addAttribute("incident", incident);

		return "showIncident";
	}

	@RequestMapping("/reportIncident")
	public String reportIncident() {
		return "reportIncident";

	}

	@GetMapping("/showIncident")
	public String showIncident(@RequestParam long param, Model model) {
		IncidentDao incidentDao = new IncidentDaoImplementation();
		Incident incident = incidentDao.getIncident(param);

		model.addAttribute("incident", incident);
		return "showIncident";

	}

	@RequestMapping("/showIncidents")
	public String showIncidents(Model model) {
		IncidentDao incidentDao = new IncidentDaoImplementation();
		List<Incident> incidents = incidentDao.getIncidents();
		incidentList.setIncidents(incidents);

		model.addAttribute("incidents", incidentList.getIncidents());
		return "showIncidents";

	}

	@RequestMapping("/filter")
	public String showStatistics(@RequestParam String dateStart, @RequestParam String dateEnd,
			@RequestParam String area, @RequestParam String typeOfObservation, @RequestParam String cathegoryOfPersonel,
			Model model) {

		CreateDate createDate = new CreateDate();
		IncidentDao incidentDao = new IncidentDaoImplementation();
		List<Incident> incidents = incidentDao.getIncidents();
		incidentList.setIncidents(incidents);

		List<Incident> limitedIncident = incidentList.getIncidents();
		if (!dateStart.equals("")) {
			LocalDateTime startDate = createDate.createDateFromRequest(dateStart, 0, 0);
			System.out.println("LOCAL DATE" + startDate);
			limitedIncident = incidentList.getIncidents().stream().filter(a -> (a.getIncidentDate().isAfter(startDate)))
					.collect(Collectors.toList());
		}
		if (!dateEnd.equals("")) {
			LocalDateTime endDate = createDate.createDateFromRequest(dateEnd, 0, 0);
			limitedIncident = limitedIncident.stream().filter(a -> (a.getIncidentDate().isBefore(endDate)))
					.collect(Collectors.toList());
		}
		if (!area.equals("")) {
			limitedIncident = limitedIncident.stream().filter(a -> a.getArea().equals(Area.valueOf(area)))
					.collect(Collectors.toList());
		}
		if (!typeOfObservation.equals("")) {
			limitedIncident = limitedIncident.stream()
					.filter(a -> a.getTypeOfObservation().equals(EventType.valueOf(typeOfObservation)))
					.collect(Collectors.toList());
		}
		if (!cathegoryOfPersonel.equals("")) {
			limitedIncident = limitedIncident.stream()
					.filter(a -> a.getCathegoryOfPersonel().equals(CathegoryOfPersonel.valueOf(cathegoryOfPersonel)))
					.collect(Collectors.toList());
		}

		incidentList.setIncidents(limitedIncident);
		model.addAttribute("incidents", incidentList.getIncidents());
		return "showIncidents";

	}
}
