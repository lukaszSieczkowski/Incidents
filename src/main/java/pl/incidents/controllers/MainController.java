package pl.incidents.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

		LocalDateTime reportingDate = LocalDateTime.now();

		CreateDate createDate = new CreateDate();
		LocalDateTime incidentDate = createDate.createDateFromString(date, hour, minute);

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
	public String showIncidents(@ModelAttribute User user, Model model) {
		IncidentDao incidentDao = new IncidentDaoImplementation();

		List<Incident> incidents = incidentDao.getIncidents(user);
		System.out.println("incidenrs + " + incidents.get(0).getDetails());
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

		Incident first = Collections.min(incidents, Comparator.comparing(c -> c.getIncidentDate()));
		Incident last = Collections.max(incidents, Comparator.comparing(c -> c.getIncidentDate()));

		MapCreator mapCreator = new MapCreator();
		Map<String, Long> map = mapCreator.createMapForArea(incidents);

		CreateDate createDate = new CreateDate();

		String startDate = createDate.createDateToString(first.getIncidentDate());
		String endDate = createDate.createDateToString(last.getIncidentDate());

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("chartForm", chartForm);
		model.addAttribute("map", map);
		
		return "showStatistics";
	}

}
