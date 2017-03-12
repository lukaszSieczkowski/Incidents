package pl.incidents.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRegistry;

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
import pl.incidents.components.UserList;
import pl.incidents.dao.IncidentDao;
import pl.incidents.dao.IncidentDaoImplementation;
import pl.incidents.dao.UserDao;
import pl.incidents.dao.UserDaoImplementation;
import pl.incidents.model.Incident;
import pl.incidents.model.User;
import pl.incidents.model.enums.Area;
import pl.incidents.model.enums.CathegoryOfPersonel;
import pl.incidents.model.enums.EventType;
import pl.incidents.model.enums.IncidentStatus;
import pl.incidents.model.enums.SupervisorInformed;
import pl.incidents.model.enums.UserActive;
import pl.incidents.model.enums.UserType;
import pl.incidents.utils.CreateDate;
import pl.incidents.utils.Mail;
import pl.incidents.utils.MapCreator;
import pl.incidents.utils.PasswordGenerator;

@Controller
@SessionAttributes("user")
public class MainController {

	private IncidentList incidentList;
	private UserList usersList;

	@Autowired
	public MainController(IncidentList incidentList, UserList usersList) {
		this.incidentList = incidentList;
		this.usersList = usersList;
	}

	@RequestMapping("/")
	public String showMain() {
		return "index";
	}

	@PostMapping("/login")
	public String showUser(@RequestParam String username, @RequestParam String password, Model model) {

		UserDao userDao = new UserDaoImplementation();
		IncidentDao incidentDao = new IncidentDaoImplementation();
		ArrayList<User> userList = (ArrayList<User>) userDao.getUsers();

		Optional<User> optionalUser = userList.stream().filter(a -> a.getEmail().equals(username)).findAny();

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.getPassword().equals(password)) {
				model.addAttribute("user", user);

				LocalDateTime date = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String formatedDate = date.format(formatter);
				model.addAttribute("date", formatedDate);

				List<Incident> incidents = incidentDao.getIncidents(user);
				incidents = incidents.stream().sorted((a,b)->(a.getIncidentStatus().compareTo(b.getIncidentStatus())))
						.collect(Collectors.toList());
				incidentList.setIncidents(incidents);
				model.addAttribute("incidents", incidentList.getIncidents());

				return "showIncidents";

			} else {
				String alert = "Incorrect Password !!!";
				model.addAttribute("alert", alert);
				return "index";
			}
		} else {
			String alert = "User does't exist!!!";
			model.addAttribute("alert", alert);
			return "index";
		}
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
		UserDao userDao = new UserDaoImplementation();
		LocalDateTime reportingDate = LocalDateTime.now();

		CreateDate createDate = new CreateDate();
		LocalDateTime incidentDate = createDate.createDateFromString(date, hour, minute);

		Incident incident = new Incident(incidentDate, reportingDate, Area.valueOf(area), location,
				EventType.valueOf(typeOfObservation), CathegoryOfPersonel.valueOf(cathegoryOfPersonel), details, action,
				SupervisorInformed.valueOf(supervisorInformed),IncidentStatus.OPEN, user);

		incidentDao.saveIncident(incident);
		
		List<User> usersList = userDao.getUsers();
		List<User> adminList = usersList.stream().filter(a->a.getUserType()==UserType.ADMIN).collect(Collectors.toList());
	
		
		Mail mail = new Mail();
		
		String mailContent = mail.prepareContentNewIncidentReported(user, incident);
		String subject = mail.prepareSubjectNewIncident();
		for(int i=0;i<adminList.size();i++){
			mail.sendMail(adminList.get(i).getEmail(), mailContent, subject);
		}
	
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
	public String showIncidents(@ModelAttribute User user, Model model) {
		IncidentDao incidentDao = new IncidentDaoImplementation();

		List<Incident> incidents = incidentDao.getIncidents(user);
		incidents = incidents.stream().sorted((a,b)->(a.getIncidentStatus().compareTo(b.getIncidentStatus())))
				.collect(Collectors.toList());

		incidentList.setIncidents(incidents);

		model.addAttribute("incidents", incidentList.getIncidents());

		return "showIncidents";
	}

	@RequestMapping("/addUser")
	public String addUser() {
		return "addUser";
	}

	@RequestMapping("/getUsers")
	public String showUsers(Model model) {
		UserDao userDao = new UserDaoImplementation();
		List<User> users = userDao.getUsers();
		users = (List<User>) users.stream().sorted((a,b)->(a.getUserActive().compareTo(b.getUserActive()))).collect(Collectors.toList());
		usersList.setUsers(users);

		model.addAttribute("users", usersList.getUsers());

		return "showUsers";
	}

	@RequestMapping("/saveUser")
	public String saveUser(@RequestParam String name, @RequestParam String surname, @RequestParam String email,
			@RequestParam String userType, Model model) {

		String password;
		String alert;
		PasswordGenerator passwordGenerator = new PasswordGenerator();
		password = passwordGenerator.generatePasword();

		User user = new User(email, password, UserType.valueOf(userType), name, surname, UserActive.valueOf("ACTIVE"));
		UserDao userDao = new UserDaoImplementation();
		userDao.saveUser(user);

		alert = "The user has been saved successfuly !!!";
		model.addAttribute("alert", alert);

		Mail mail = new Mail();
		String mailContent = mail.prepareContentNewUser(user);
		String mailSubject = mail.prepareSubjectNewUser();
		mail.sendMail(user.getEmail(), mailContent, mailSubject);

		return "addUser";
	}

	@RequestMapping("/showUserDetails")
	public String showUserDetails(Model model, long param) {

		UserDao userDao = new UserDaoImplementation();
		User userWithDetails = userDao.getUser(param);

		model.addAttribute("userWithDetails", userWithDetails);

		return "showUser";
	}

	@RequestMapping("/changeUserType")
	public String changeUserType(Model model, long param) {
		UserDao userDao = new UserDaoImplementation();
		User userWithDetails = userDao.getUser(param);
		String userTypeBefore = userWithDetails.getUserType().toString();

		if (userWithDetails.getUserType().equals(UserType.ADMIN)) {
			userWithDetails.setUserType(UserType.USER);
		} else {
			userWithDetails.setUserType(UserType.ADMIN);
		}
		String userTypeAfter = userWithDetails.getUserType().toString();
		userDao.updateUser(userWithDetails);

		String alert = "User type has been changed from " + userTypeBefore + " to " + userTypeAfter
				+ "  successfuly !!!";
		model.addAttribute("userWithDetails", userWithDetails);
		model.addAttribute("alert", alert);

		return "showUser";
	}

	@RequestMapping("/changeUserActivity")
	public String changeUserActivity(Model model, long param) {
		UserDao userDao = new UserDaoImplementation();
		User userWithDetails = userDao.getUser(param);
		String userActivityBefore = userWithDetails.getUserActive().toString();

		if (userWithDetails.getUserActive().equals(UserActive.ACTIVE)) {
			
			userWithDetails.setUserActive(UserActive.INACTIVE);
			PasswordGenerator pass = new PasswordGenerator();
			String password = pass.generatePasword();
			userWithDetails.setPassword(password);
			
			Mail mail = new Mail();
			String mailContent = mail.prepareContentUserLocked();
			String subject = mail.prepareSubjectUserLocked();
			mail.sendMail(userWithDetails.getEmail(), mailContent, subject);
			
		} else {
			userWithDetails.setUserActive(UserActive.ACTIVE);
		}
		String userActivityAfter = userWithDetails.getUserActive().toString();

		userDao.updateUser(userWithDetails);
		
		String alert = "User type has been changed from " + userActivityBefore + " to " + userActivityAfter
				+ "  successfuly !!!";
		model.addAttribute("userWithDetails", userWithDetails);
		model.addAttribute("alert", alert);
		
		

		return "showUser";
	}

	@RequestMapping("/resetUserPassword")
	public String resetPassword(Model model, long param) {
		UserDao userDao = new UserDaoImplementation();
		User userWithDetails = userDao.getUser(param);

		String password;
		String alert;

		PasswordGenerator passwordGenerator = new PasswordGenerator();
		password = passwordGenerator.generatePasword();
		userWithDetails.setPassword(password);
		userDao.updateUser(userWithDetails);

		Mail mail = new Mail();
		String mailContent = mail.prepareContentNewPassword(userWithDetails);
		String mailSubject = mail.prepareSubjectNewPassword();
		mail.sendMail(userWithDetails.getEmail(), mailContent, mailSubject);

		alert = "Tha password for " + userWithDetails.getName() + " " + userWithDetails.getSurname()
				+ " has been changed  successfuly !!!";
		model.addAttribute("userWithDetails", userWithDetails);
		model.addAttribute("alert", alert);

		return "showUser";
	}

	@RequestMapping("/showStatistics")
	public String showStatistics(@RequestParam String chartForm, Model model) {

		List<Incident> incidents = incidentList.getIncidents();
		List<Incident> approvedIncidents = incidents.stream()
				.filter(a->a.getIncidentStatus()!=IncidentStatus.NOT_APPROVED).collect(Collectors.toList());
		
		Incident first = Collections.min(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
		Incident last = Collections.max(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));

		MapCreator mapCreator = new MapCreator();
		Map<String, Long> map = mapCreator.createMapForArea(approvedIncidents);

		CreateDate createDate = new CreateDate();

		String startDate = createDate.createDateToString(first.getIncidentDate());
		String endDate = createDate.createDateToString(last.getIncidentDate());

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("chartForm", chartForm);
		model.addAttribute("map", map);

		return "showStatistics";
	}
	
	@RequestMapping("/showIncidentsForUser")
	public String showStatisticForUser(@RequestParam String chartForm,@RequestParam long param, Model model){
		
		IncidentDao incidentDao = new IncidentDaoImplementation();
		List<Incident> incidents = incidentDao.getIncidentsByUserId(param);
		List<Incident> approvedIncidents = incidents.stream()
				.filter(a->a.getIncidentStatus()!=IncidentStatus.NOT_APPROVED).collect(Collectors.toList());
		incidentList.setIncidents(approvedIncidents);
	
		Incident first = Collections.min(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
		Incident last = Collections.max(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));

		MapCreator mapCreator = new MapCreator();
		Map<String, Long> map = mapCreator.createMapForArea(approvedIncidents);

		CreateDate createDate = new CreateDate();

		String startDate = createDate.createDateToString(first.getIncidentDate());
		String endDate = createDate.createDateToString(last.getIncidentDate());

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("chartForm", chartForm);
		model.addAttribute("map", map);

		return "showStatistics";
	}
	
	@RequestMapping("/closeIncident")
	public String closeIncident(@RequestParam long param,Model model){
		IncidentDao incidentDao = new IncidentDaoImplementation();
		Incident incident = incidentDao.getIncident(param);
		if(incident.getIncidentStatus().equals(IncidentStatus.OPEN)){
			incident.setIncidentStatus(IncidentStatus.CLOSED);
		}
		else if(incident.getIncidentStatus().equals(IncidentStatus.CLOSED)){
			incident.setIncidentStatus(IncidentStatus.OPEN);
		}
		
		
		incidentDao.updateIncident(incident);
		
		model.addAttribute("incident",incident);
		
		return "showIncident";
	}
	
	@RequestMapping("/aproveIncident")
	public String aproveIncident(@RequestParam long param,Model model){
		IncidentDao incidentDao = new IncidentDaoImplementation();
		Incident incident = incidentDao.getIncident(param);
		if(incident.getIncidentStatus().equals(IncidentStatus.OPEN)||incident.getIncidentStatus().equals(IncidentStatus.CLOSED)){
			incident.setIncidentStatus(IncidentStatus.NOT_APPROVED);
		}
		else if(incident.getIncidentStatus().equals(IncidentStatus.NOT_APPROVED)){
			incident.setIncidentStatus(IncidentStatus.OPEN);
		}
		
		
		incidentDao.updateIncident(incident);
		
		model.addAttribute("incident",incident);
		
		return "showIncident";
	}
	
	@RequestMapping ("/showEditIncident")
	public String showEditincident(@RequestParam long param,Model model){
		IncidentDao incidentDao = new IncidentDaoImplementation();
		Incident incident = incidentDao.getIncident(param);
		
		model.addAttribute("incident",incident);
		return "editIncident";
	}
	
	@RequestMapping("/editIncident")
	public String editIncident(@RequestParam String area, @RequestParam String location, @RequestParam String typeOfObservation,
			@RequestParam String cathegoryOfPersonel, @RequestParam String details, @RequestParam String action,
			@RequestParam String supervisorInformed,@RequestParam long param,Model model){
	
			
			IncidentDao incidentDao = new IncidentDaoImplementation();
			Incident incident = incidentDao.getIncident(param);
			incident.setArea(Area.valueOf(area));
			incident.setLocation(location);
			incident.setTypeOfObservation(EventType.valueOf(typeOfObservation));
			incident.setCathegoryOfPersonel(CathegoryOfPersonel.valueOf(cathegoryOfPersonel));
			incident.setDetails(details);
			incident.setAction(action);
			incident.setSupervisorInformed(SupervisorInformed.valueOf(supervisorInformed));
			
			incidentDao.updateIncident(incident);
			
			model.addAttribute("incident", incident);
		return "showIncident";
	}

}
