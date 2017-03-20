package pl.incidents.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
import pl.incidents.model.enums.*;
import pl.incidents.utils.CreateDate;
import pl.incidents.utils.Mail;
import pl.incidents.utils.MapCreator;
import pl.incidents.utils.PasswordGenerator;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static pl.incidents.controllers.IController.*;

@Controller
@SessionAttributes("user")

public class MainController {

	/**
	 * Component with list of available incidents for logged user
	 */
	private IncidentList incidentList;

	/**
	 * Component with list of available users for logged user
	 */
	private UserList usersList;

	/**
	 * Constructor for SortControler with injected incidentList and UsersList
	 * 
	 * @param incidentList
	 *            component with list of incidents
	 * @param usersList
	 *            component with list of users
	 */
	@Autowired
	public MainController(IncidentList incidentList, UserList usersList) {
		this.incidentList = incidentList;
		this.usersList = usersList;
	}

	/**
	 * 
	 * Method shows main view when application start.
	 * 
	 * @return index view
	 */
	@RequestMapping("/")
	public String showMain() {
		return INDEX;
	}

	/**
	 * Logs user.
	 * 
	 * @param username
	 *            Email of logged user.
	 * @param password
	 *            Password of logged user.
	 * @param model
	 *            Holder for attributes.
	 * @return showIncidents view
	 */
	@PostMapping("/login")
	public String showUser(@RequestParam String username, @RequestParam String password, Model model) {

		/////// Prepares data for presentation////////////

		// Presentation.createData();

		/////////////////////////////

		UserDao userDao = new UserDaoImplementation();
		IncidentDao incidentDao = new IncidentDaoImplementation();

		ArrayList<User> userList = (ArrayList<User>) userDao.getUsers();

		Optional<User> optionalUser = userList.stream().filter(a -> a.getEmail().equals(username)).findAny();

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

			String formMd5Password = DigestUtils.md5Hex(password).toString();

			if (user.getPassword().equals(formMd5Password)) {

				model.addAttribute(USER, user);

				System.out.println(user);

				LocalDateTime date = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
				String formatedDate = date.format(formatter);
				model.addAttribute(DATE, formatedDate);

				List<Incident> incidents = incidentDao.getIncidents(user);
				incidents = incidents.stream()
						.sorted((a, b) -> (a.getIncidentStatus().compareTo(b.getIncidentStatus())))
						.collect(Collectors.toList());
				incidentList.setIncidents(incidents);
				model.addAttribute(INCIDENTS, incidentList.getIncidents());

				return SHOW_INCIDENTS;

			} else {
				String alert = INCORRECT_PASSWORD;
				model.addAttribute(ALERT, alert);
				return INDEX;
			}
		} else {
			String alert = USER_DOES_T_EXIST;
			model.addAttribute(ALERT, alert);
			return INDEX;
		}
	}

	/**
	 * Clean all attributes
	 * 
	 * @param request
	 *            Giving access to general request metadata.
	 * @param status
	 *            Clean up a session,
	 * @return index view.
	 */

	@RequestMapping("/logout")
	public String logout(WebRequest request, SessionStatus status) {
		status.setComplete();
		request.removeAttribute(USER, WebRequest.SCOPE_SESSION);
		request.removeAttribute(DATE, WebRequest.SCOPE_SESSION);
		request.removeAttribute(INCIDENTS, WebRequest.SCOPE_SESSION);
		request.removeAttribute(USERS, WebRequest.SCOPE_SESSION);
		return INDEX;
	}

	/**
	 * Saves reported incident
	 * 
	 * @param date
	 *            Incident date,
	 * @param hour
	 *            Incident time(hours),
	 * @param minute
	 *            Incident time(minutes),
	 * @param area
	 *            Incident Area.
	 * @param location
	 *            Incident Location.
	 * @param typeOfObservation
	 *            Type of Incident.
	 * @param cathegoryOfPersonel
	 *            Category of personnel involved in incident.
	 * @param details
	 *            Incident details
	 * @param action
	 *            Actions taken.
	 * @param supervisorInformed
	 *            Informed Information is Supervisor informed or not.
	 * @param model
	 *            Holder for attributes
	 * @param user
	 *            User whose reported incident
	 * @param incident
	 *            New incident object
	 * @param result
	 *            Holder for errors
	 * @return reportIncident view
	 */

	@PostMapping("/saveIncident")
	public String saveIncident(@RequestParam String date, @RequestParam int hour, @RequestParam int minute,
			@RequestParam String area, @RequestParam String location, @RequestParam String typeOfObservation,
			@RequestParam String cathegoryOfPersonel, @RequestParam String details, @RequestParam String action,
			@RequestParam String supervisorInformed, Model model, @ModelAttribute User user,
			@Valid @ModelAttribute("newIncident") Incident incident, BindingResult result) {

		if (!result.hasErrors()) {

			IncidentDao incidentDao = new IncidentDaoImplementation();
			UserDao userDao = new UserDaoImplementation();
			LocalDateTime reportingDate = LocalDateTime.now();
			LocalDateTime incidentDate;
			CreateDate createDate = new CreateDate();
			try {
				incidentDate = createDate.createDateFromString(date, hour, minute);
			} catch (StringIndexOutOfBoundsException | NumberFormatException e) {
				String alert;
				alert = INCORECT_END_DATE_FORMAT;
				model.addAttribute(ALERT, alert);
				return REPORT_INCIDENT;
			}

			incident = new Incident(incidentDate, reportingDate, Area.valueOf(area), location,
					EventType.valueOf(typeOfObservation), CathegoryOfPersonel.valueOf(cathegoryOfPersonel), details,
					action, SupervisorInformed.valueOf(supervisorInformed), IncidentStatus.OPEN, user);
			incidentDao.saveIncident(incident);

			List<User> usersList = userDao.getUsers();
			List<User> adminList = usersList.stream().filter(a -> a.getUserType() == UserType.ADMIN)
					.collect(Collectors.toList());

			Mail mail = new Mail();
			String mailContent = mail.prepareContentNewIncidentReported(user, incident);
			String subject = mail.prepareSubjectNewIncident();
			for (int i = 0; i < adminList.size(); i++) {
				mail.sendMail(adminList.get(i).getEmail(), mailContent, subject);
			}

			model.addAttribute(INCIDENT, incident);
			return SHOW_INCIDENTS;
		} else {
			return REPORT_INCIDENT;
		}

	}

	/**
	 * Shows view where can be reported incident
	 * 
	 * @return reportIncident view.
	 */
	@RequestMapping("/reportIncident")
	public String reportIncident(Model model) {

		return REPORT_INCIDENT;
	}

	/**
	 * Shows selected incident in returned view
	 * 
	 * @param param
	 *            Id of Incident.
	 * @param model
	 *            Holder for attributes.
	 * @return showIncident view.
	 */
	@GetMapping("/showIncident")
	public String showIncident(@RequestParam long param, Model model) {

		IncidentDao incidentDao = new IncidentDaoImplementation();
		Incident incident = incidentDao.getIncident(param);

		model.addAttribute(INCIDENT, incident);

		return SHOW_INCIDENTS;
	}

	/**
	 * Shows list of incidents available for logged user in returned view.
	 * 
	 * @param user
	 *            Logged user.
	 * @param model
	 *            Holder for attributes.
	 * @return showIncidents view.
	 */
	@RequestMapping("/showIncidents")
	public String showIncidents(@ModelAttribute User user, Model model) {

		IncidentDao incidentDao = new IncidentDaoImplementation();
		List<Incident> incidents = incidentDao.getIncidents(user);
		incidents = incidents.stream().sorted((a, b) -> (a.getIncidentStatus().compareTo(b.getIncidentStatus())))
				.collect(Collectors.toList());

		incidentList.setIncidents(incidents);

		model.addAttribute(INCIDENTS, incidentList.getIncidents());

		return SHOW_INCIDENTS;
	}

	/**
	 * Returns addUser view
	 * 
	 * @return addUser view
	 */

	@RequestMapping("/addUser")
	public String addUser() {
		return ADD_USER;
	}

	/**
	 * Shows list of users in returned view
	 * 
	 * @param model
	 *            Holder for attributes.
	 * @return showUsers view.
	 */

	@RequestMapping("/getUsers")
	public String showUsers(Model model) {

		UserDao userDao = new UserDaoImplementation();
		List<User> users = userDao.getUsers();
		users = (List<User>) users.stream().sorted((a, b) -> (a.getUserActive().compareTo(b.getUserActive())))
				.collect(Collectors.toList());
		usersList.setUsers(users);

		model.addAttribute(USERS, usersList.getUsers());

		return SHOW_USERS;
	}

	/**
	 * Saves new user in System
	 * 
	 * @param name
	 *            New user name.
	 * @param surname
	 *            New user surname.
	 * @param email
	 *            New user email.
	 * @param userType
	 *            New user type.
	 * @param newUser
	 *            New user object
	 * @param result
	 *            Holder for errors
	 * @param model
	 *            Holder for attributes.
	 * @return addUser view
	 */
	@RequestMapping("/saveUser")
	public String saveUser(@RequestParam String name, @RequestParam String surname, @RequestParam String email,
			@RequestParam String userType, @Valid @ModelAttribute("newUser") User newUser, BindingResult result,
			Model model) {

		if (!result.hasErrors()) {
			String alert;
			UserDao userDao = new UserDaoImplementation();
			List<User> usersList = userDao.getUsers();
			Optional<User> optionalUser = usersList.stream().filter(a -> a.getEmail().equals(email)).findAny();

			if (optionalUser.isPresent()) {
				alert = THE_USER_WITH_GIVEN_EMAIL_ALREADY_EXIST;
				model.addAttribute(ALERT, alert);
			} else {
				String password;
				String md5Password;

				PasswordGenerator passwordGenerator = new PasswordGenerator();
				password = passwordGenerator.generatePasword();

				md5Password = DigestUtils.md5Hex(password).toString();

				newUser = new User(email, md5Password, UserType.valueOf(userType), name, surname,
						UserActive.valueOf(ACTIVE));

				userDao.saveUser(newUser);

				alert = THE_USER_HAS_BEEN_SAVED_SUCCESSFULY;
				model.addAttribute(ALERT, alert);

				Mail mail = new Mail();
				String mailContent = mail.prepareContentNewUser(newUser, password);
				String mailSubject = mail.prepareSubjectNewUser();
				mail.sendMail(newUser.getEmail(), mailContent, mailSubject);
			}
		}

		return ADD_USER;

	}

	/**
	 * Shows user details in returned view
	 * 
	 * @param model
	 *            Holder for attributes.
	 * @param param
	 *            User id.
	 * @return showUser view
	 */

	@RequestMapping("/showUserDetails")
	public String showUserDetails(Model model, long param) {

		UserDao userDao = new UserDaoImplementation();
		User userWithDetails = userDao.getUser(param);

		model.addAttribute(USER_WITH_DETAILS, userWithDetails);

		return SHOW_USER;
	}

	/**
	 * Changes user type to Admin or User
	 * 
	 * @param model
	 *            Holder for attributes.
	 * @param param
	 *            User id.
	 * @return showUser view.
	 */

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
		model.addAttribute(USER_WITH_DETAILS, userWithDetails);
		model.addAttribute(ALERT, alert);

		return SHOW_USER;
	}

	/**
	 * Changes user activity to Active or Inactive
	 * 
	 * @param model
	 *            Holder for attributes.
	 * @param param
	 *            User id.
	 * @return showUser view.
	 */
	@RequestMapping("/changeUserActivity")
	public String changeUserActivity(Model model, long param) {

		UserDao userDao = new UserDaoImplementation();
		User userWithDetails = userDao.getUser(param);
		String userActivityBefore = userWithDetails.getUserActive().toString();

		if (UserActive.ACTIVE.equals(userWithDetails.getUserActive())) {

			userWithDetails.setUserActive(UserActive.INACTIVE);
			PasswordGenerator pass = new PasswordGenerator();
			String password = pass.generatePasword();

			String md5Password = DigestUtils.md5Hex(password).toString();
			userWithDetails.setPassword(md5Password);

			Mail mail = new Mail();
			String mailContent = mail.prepareContentUserLocked();
			String subject = mail.prepareSubjectUserLocked();
			mail.sendMail(userWithDetails.getEmail(), mailContent, subject);

		} else {
			userWithDetails.setUserActive(UserActive.ACTIVE);
			PasswordGenerator pass = new PasswordGenerator();
			String password = pass.generatePasword();

			String md5Password = DigestUtils.md5Hex(password).toString();
			userWithDetails.setPassword(md5Password);

			Mail mail = new Mail();
			String mailContent = mail.prepareContentNewUser(userWithDetails, password);
			String subject = mail.prepareSubjectNewUser();
			mail.sendMail(userWithDetails.getEmail(), mailContent, subject);
		}
		String userActivityAfter = userWithDetails.getUserActive().toString();

		userDao.updateUser(userWithDetails);

		String alert = "User type has been changed from " + userActivityBefore + " to " + userActivityAfter
				+ "  successfuly !!!";
		model.addAttribute(USER_WITH_DETAILS, userWithDetails);
		model.addAttribute(ALERT, alert);

		return SHOW_USER;
	}

	/**
	 * Resets user password
	 * 
	 * @param model
	 *            Holder for attributes.
	 * @param param
	 *            User id.
	 * @return showUser view.
	 */
	@RequestMapping("/resetUserPassword")
	public String resetPassword(Model model, long param) {

		UserDao userDao = new UserDaoImplementation();
		User userWithDetails = userDao.getUser(param);

		String password;
		String alert;

		PasswordGenerator passwordGenerator = new PasswordGenerator();
		password = passwordGenerator.generatePasword();

		String md5Password = DigestUtils.md5Hex(password).toString();
		userWithDetails.setPassword(md5Password);

		Mail mail = new Mail();
		String mailContent = mail.prepareContentNewPassword(userWithDetails, md5Password);
		String mailSubject = mail.prepareSubjectNewPassword();
		mail.sendMail(userWithDetails.getEmail(), mailContent, mailSubject);

		String md5password = DigestUtils.md5Hex(password).toString();
		userWithDetails.setPassword(md5password);

		userDao.updateUser(userWithDetails);

		alert = "Tha password for " + userWithDetails.getName() + " " + userWithDetails.getSurname()
				+ " has been changed  successfuly !!!";
		model.addAttribute(USER_WITH_DETAILS, userWithDetails);
		model.addAttribute(ALERT, alert);

		return SHOW_USER;
	}

	/**
	 * Shows statistics available for logged user
	 * 
	 * @param chartForm
	 *            Type of chart
	 * @param model
	 *            Holder for attributes.
	 * @return showStatistics
	 */
	@RequestMapping("/showStatistics")
	public String showStatistics(@RequestParam String chartForm, Model model) {

		List<Incident> incidents = incidentList.getIncidents();
		List<Incident> approvedIncidents = incidents.stream()
				.filter(a -> a.getIncidentStatus() != IncidentStatus.NOT_APPROVED).collect(Collectors.toList());
		Incident first;
		Incident last;
		try {
			first = Collections.min(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
			last = Collections.max(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
		} catch (NoSuchElementException e) {
			String alert = "Incident list is empty !!!";
			model.addAttribute(ALERT, alert);
			return SHOW_INCIDENTS;
		}

		MapCreator mapCreator = new MapCreator();
		Map<String, Long> map = mapCreator.createMapForArea(approvedIncidents);

		CreateDate createDate = new CreateDate();

		String startDate = createDate.createDateToString(first.getIncidentDate());
		String endDate = createDate.createDateToString(last.getIncidentDate());

		model.addAttribute(START_DATE, startDate);
		model.addAttribute(END_DATE, endDate);
		model.addAttribute(CHART_FORM, chartForm);
		model.addAttribute(MAP, map);

		return "showStatistics";
	}

	/**
	 * Shows administrator statistics for specific user in returned view
	 * 
	 * @param chartForm
	 *            Type of chart.
	 * @param param
	 *            User id.
	 * @param model
	 *            Holder for attributes.
	 * @return showStatstics view.
	 */
	@RequestMapping("/showIncidentsForUser")
	public String showStatisticForUser(@RequestParam String chartForm, @RequestParam long param, Model model) {

		IncidentDao incidentDao = new IncidentDaoImplementation();
		List<Incident> incidents = incidentDao.getIncidentsByUserId(param);
		List<Incident> approvedIncidents = incidents.stream()
				.filter(a -> a.getIncidentStatus() != IncidentStatus.NOT_APPROVED).collect(Collectors.toList());
		incidentList.setIncidents(approvedIncidents);
		Incident first;
		Incident last;
		try {

			//todo rid of duplicate
			first = Collections.min(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
			last = Collections.max(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
		} catch (NoSuchElementException e) {
			String alert = "Incident list is empty !!!";
			model.addAttribute(ALERT, alert);
			return SHOW_USERS;
		}
		MapCreator mapCreator = new MapCreator();
		Map<String, Long> map = mapCreator.createMapForArea(approvedIncidents);

		CreateDate createDate = new CreateDate();

		String startDate = createDate.createDateToString(first.getIncidentDate());
		String endDate = createDate.createDateToString(last.getIncidentDate());

		model.addAttribute(START_DATE, startDate);
		model.addAttribute(END_DATE, endDate);
		model.addAttribute(CHART_FORM, chartForm);
		model.addAttribute(MAP, map);

		return "showStatistics";
	}

	/**
	 * Sets incident as Open or Closed
	 * 
	 * @param param
	 *            Incident id.
	 * @param model
	 *            Holder for attributes.
	 * @return showIncident view.
	 */

	@RequestMapping("/closeIncident")
	public String closeIncident(@RequestParam long param, Model model) {
		IncidentDao incidentDao = new IncidentDaoImplementation();
		Incident incident = incidentDao.getIncident(param);
		if (incident.getIncidentStatus().equals(IncidentStatus.OPEN)) {
			incident.setIncidentStatus(IncidentStatus.CLOSED);
		} else if (incident.getIncidentStatus().equals(IncidentStatus.CLOSED)) {
			incident.setIncidentStatus(IncidentStatus.OPEN);
		}

		incidentDao.updateIncident(incident);

		model.addAttribute(INCIDENT, incident);

		return SHOW_INCIDENT;
	}

	/**
	 * Sets incident as Approved or Not Approved
	 * 
	 * @param param
	 *            Incident id.
	 * @param model
	 *            Holder for attributes.
	 * @return showIncident view.
	 */

	@RequestMapping("/aproveIncident")
	public String aproveIncident(@RequestParam long param, Model model) {
		IncidentDao incidentDao = new IncidentDaoImplementation();
		Incident incident = incidentDao.getIncident(param);
		if (incident.getIncidentStatus().equals(IncidentStatus.OPEN)
				|| incident.getIncidentStatus().equals(IncidentStatus.CLOSED)) {
			incident.setIncidentStatus(IncidentStatus.NOT_APPROVED);
		} else if (incident.getIncidentStatus().equals(IncidentStatus.NOT_APPROVED)) {
			incident.setIncidentStatus(IncidentStatus.OPEN);
		}

		incidentDao.updateIncident(incident);

		model.addAttribute(INCIDENT, incident);

		return SHOW_INCIDENT;
	}

	/**
	 * Returns specific incident in editView.
	 * 
	 * @param param
	 *            Incident id.
	 * @param model
	 *            Holder for attributes.
	 * @return editIncident view.
	 */

	@RequestMapping("/showEditIncident")
	public String showEditincident(@RequestParam long param, Model model) {
		IncidentDao incidentDao = new IncidentDaoImplementation();
		Incident incident = incidentDao.getIncident(param);
		model.addAttribute(INCIDENT, incident);
		return EDIT_INCIDENT;
	}

	/**
	 * Edit incident
	 * 
	 * @param area
	 *            Incident Area.
	 * @param location
	 *            Incident Location.
	 * @param typeOfObservation
	 *            Type of Incident.
	 * @param cathegoryOfPersonel
	 *            Category of personnel involved in incident.
	 * @param details
	 *            Incident details
	 * @param action
	 *            Actions taken.
	 * @param supervisorInformed
	 *            Informed Information is Supervisor informed or not.
	 * @param model
	 *            Holder for attributes
	 * @param param
	 *            Incident id
	 * @param model
	 *            Holder for incidents
	 * @param incident
	 *            Incident object
	 * @param result
	 *            Holder for errors
	 * @return showIncident or editIncident view
	 */

	@RequestMapping("/editIncident")
	public String editIncident(@RequestParam String area, @RequestParam String location,
			@RequestParam String typeOfObservation, @RequestParam String cathegoryOfPersonel,
			@RequestParam String details, @RequestParam String action, @RequestParam String supervisorInformed,
			@RequestParam long param, Model model, @Valid @ModelAttribute("editedIncident") Incident incident,
			BindingResult result) {

		IncidentDao incidentDao = new IncidentDaoImplementation();
		Incident newIncident = incidentDao.getIncident(param);

		if (result.hasErrors()) {

			model.addAttribute(INCIDENT, newIncident);

			return EDIT_INCIDENT;
		} else {
			incidentDao = new IncidentDaoImplementation();
			newIncident.setArea(Area.valueOf(area));
			newIncident.setLocation(location);
			newIncident.setTypeOfObservation(EventType.valueOf(typeOfObservation));
			newIncident.setCathegoryOfPersonel(CathegoryOfPersonel.valueOf(cathegoryOfPersonel));
			newIncident.setDetails(details);
			newIncident.setAction(action);
			newIncident.setSupervisorInformed(SupervisorInformed.valueOf(supervisorInformed));

			incidentDao.updateIncident(newIncident);

			model.addAttribute(INCIDENT, newIncident);

			return SHOW_INCIDENT;
		}

	}

	/**
	 * Shows view where password may be changed.
	 * 
	 * @return editPassword view.
	 */
	@RequestMapping("/editPassword")
	public String editPassword() {
		return EDIT_PASSWORD;
	}

	/**
	 * Changes user password
	 * 
	 * @param oldPassword
	 *            Old password.
	 * @param newPassword1
	 *            New password.
	 * @param newPassword2
	 *            Repeated new password.
	 * @param model
	 *            Holder for attributes.
	 * @param user
	 *            Logged user.
	 * @return editPassword view.
	 */
	@RequestMapping("/changePassword")
	public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword1,
			@RequestParam String newPassword2, Model model, @ModelAttribute User user) {

		String oldPasswordMd5 = DigestUtils.md5Hex(oldPassword).toString();

		String alert = null;

		if (oldPassword.length() < 9 || newPassword1.length() < 9 || newPassword2.length() < 9) {
			alert = PASSWORD_HAVE_TO_CONTAINS_AT_LEAST_8_SIGNS;
		} else if (user.getPassword().equals(oldPasswordMd5) && newPassword1.equals(newPassword2)
				&& (!newPassword1.equals(""))) {

			String newPasswordMd5 = DigestUtils.md5Hex(newPassword1).toString();
			user.setPassword(newPasswordMd5);

			UserDao userdao = new UserDaoImplementation();
			userdao.updateUser(user);

			Mail mail = new Mail();
			String mailContent = mail.prepareContentNewPassword(user, newPassword1);
			String subject = mail.prepareSubjectNewPassword();
			mail.sendMail(user.getEmail(), mailContent, subject);
			alert = PASSWORD_WAS_CHANGED_SUCCESSFUL;

		} else if (!user.getPassword().equals(oldPasswordMd5)) {
			alert = INCORECT_OLD_PASSWORD;
		} else if (!newPassword1.equals(newPassword2)) {
			alert = PASSWORDS_AREN_T_EQULAS;
		}
		model.addAttribute(ALERT, alert);
		return EDIT_PASSWORD;
	}

}
