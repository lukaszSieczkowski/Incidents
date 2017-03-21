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
public class SortController implements IController {

	/**
	 * Component with list of available incidents for logged user
	 */
	private IncidentList incidentsList;
	/**
	 * Component with list of available users for logged user
	 */
	private UserList usersList;

	/**
	 * Constructor for SortControler with injected incidentList and UsersList
	 * 
	 * @param incidentsList
	 *            component with list of incidents
	 * @param usersList
	 *            component with list of users
	 */
	@Autowired
	public SortController(IncidentList incidentsList, UserList usersList) {
		this.incidentsList = incidentsList;
		this.usersList = usersList;
	}

	/**
	 * Method shows in returned view all incidents available for logged user
	 * sorted by incident date.
	 * 
	 * @param model
	 *            Holder for sorted incident list attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showIncidents view.
	 */
	@RequestMapping("/sortByIncidentDate")
	public String sortByIncudentDate(Model model, String param) {

		List<Incident> incidents = incidentsList.getIncidents();
		sortByDate(model, param, incidents);

		return SHOW_INCIDENTS;
	}

	/**
	 * Method shows in returned view all incidents available for logged user
	 * sorted by area.
	 * 
	 * @param model
	 *            Holder for sorted incident list attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showIncidents view.
	 */

	@RequestMapping("/sortByArea")
	public String sortByArea(Model model, String param) {

		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getArea().compareTo(b.getArea())))
				.collect(Collectors.toList());

		if (ASC.equals(param)) {
			Collections.reverse(incidents);
		}

		incidentsList.setIncidents(incidents);
		model.addAttribute(	INCIDENTS, incidentsList.getIncidents());

		return SHOW_STATISTICS;
	}

	/**
	 * Method shows in returned view all incidents available for logged user
	 * sorted by event.
	 * 
	 * @param model
	 *            Holder for sorted incidentList attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showIncidents view.
	 */
	@RequestMapping("/sortByEvent")
	public String sortByEvent(Model model, String param) {

		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getTypeOfObservation().compareTo(b.getTypeOfObservation())))
				.collect(Collectors.toList());

		if (ASC.equals(param)) {
			Collections.reverse(incidents);
		}

		incidentsList.setIncidents(incidents);
		model.addAttribute(INCIDENTS, incidentsList.getIncidents());

		return SHOW_INCIDENTS;
	}

	/**
	 * Method shows in returned view all incidents available for logged user
	 * sorted by a personel.
	 * 
	 * @param model
	 *            Holder for sorted incidentList attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showIncidents view.
	 */
	@RequestMapping("/sortByPersonel")
	public String sortByPersonel(Model model, String param) {

		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream()
				.sorted((a, b) -> (a.getCathegoryOfPersonel().compareTo(b.getCathegoryOfPersonel())))
				.collect(Collectors.toList());

		if (ASC.equals(param)) {
			Collections.reverse(incidents);
		}

		incidentsList.setIncidents(incidents);
		model.addAttribute(INCIDENTS, incidentsList.getIncidents());

		return SHOW_INCIDENTS;
	}

	/**
	 * Method shows in returned view all incidents available for logged user
	 * sorted by a information: is supervisor informed.
	 * 
	 * @param model
	 *            Holder for sorted incidents list attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showIncidents view.
	 */
	@RequestMapping("/sortBySupervisorInf")
	public String sortBySupervisorInfl(Model model, String param) {

		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream()
				.sorted((a, b) -> (a.getSupervisorInformed().compareTo(b.getSupervisorInformed())))
				.collect(Collectors.toList());

		if (ASC.equals(param)) {
			Collections.reverse(incidents);
		}

		incidentsList.setIncidents(incidents);
		model.addAttribute(INCIDENTS, incidentsList.getIncidents());

		return SHOW_INCIDENTS;
	}

	/**
	 * Method shows in returned view all incidents available for logged user
	 * sorted by reporting date.
	 * 
	 * @param model
	 *            Holder for sorted incidentList attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showIncidents view.
	 */

	@RequestMapping("/sortByReportingDate")
	public String sortByReportingtDate(Model model, String param) {

		List<Incident> incidents = incidentsList.getIncidents();
		sortByDate(model, param, incidents);

		return SHOW_INCIDENTS;
	}

	/**
	 * sorting incidents by date
	 * @param model Holder for sorted incidentList attribute.
	 * @param param  Specifies direction of sorting: asc - ascending or desc descending.
	 * @param incidents list of incidents which supposed to sorted
	 */
	private void sortByDate(Model model, String param, List<Incident> incidents) {
		incidents = incidents.stream().sorted((a, b) -> (a.getIncidentDate().compareTo(b.getIncidentDate())))
				.collect(Collectors.toList());

		if (ASC.equals(param)) {
			Collections.reverse(incidents);
		}

		incidentsList.setIncidents(incidents);
		model.addAttribute(INCIDENTS, incidentsList.getIncidents());
	}

	/**
	 * Method shows in returned view all incidents available for logged user
	 * sorted by incident owner(person whose reported incident ).
	 * 
	 * @param model
	 *            Holder for sorted incidentList attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showIncidents view.
	 */

	@RequestMapping("/sortByReporter")
	public String sortByReporter(Model model, String param) {

		List<Incident> incidents = incidentsList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getUser().getSurname().compareTo(b.getUser().getSurname())))
				.collect(Collectors.toList());

		if (ASC.equals(param)) {
			Collections.reverse(incidents);
		}

		incidentsList.setIncidents(incidents);
		model.addAttribute(INCIDENTS, incidentsList.getIncidents());

		return SHOW_INCIDENTS;
	}

	/**
	 * Method shows in returned view all users available for logged user sorted
	 * by user surname.
	 * 
	 * @param model
	 *            Holder for sorted users list attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showUsers view.
	 */

	@RequestMapping("/sortByUserSurname")
	public String sortByUserSurnameDesc(Model model, String param) {

		List<User> users = usersList.getUsers();
		users = users.stream().sorted((a, b) -> (a.getSurname().compareTo(b.getSurname())))
				.collect(Collectors.toList());

		if (ASC.equals(param)) {
			Collections.reverse(users);
		}

		usersList.setUsers(users);
		model.addAttribute(USERS, usersList.getUsers());

		return SHOW_USERS;
	}

	/**
	 * Method shows in returned view all users available for logged user sorted
	 * by user type.
	 * 
	 * @param model
	 *            Holder for sorted users list attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showUsers view.
	 */

	@RequestMapping("/sortByUserType")
	public String sortByUserType(Model model, String param) {

		List<User> users = usersList.getUsers();
		users = users.stream().sorted((a, b) -> (a.getUserType().compareTo(b.getUserType())))
				.collect(Collectors.toList());

		if (ASC.equals(param)) {
			Collections.reverse(users);
		}

		usersList.setUsers(users);
		model.addAttribute(USERS, usersList.getUsers());

		return SHOW_USERS;
	}

	/**
	 * Method shows in returned view all users available for logged user sorted
	 * by is user active or inactive.
	 * 
	 * @param model
	 *            Holder for sorted users list attribute.
	 * @param param
	 *            Specifies direction of sorting: asc - ascending or desc =
	 *            descending.
	 * @return showUsers view.
	 */

	@RequestMapping("/sortByUserActive")
	public String sortByUserActive(Model model, String param) {

		List<User> users = usersList.getUsers();
		users = users.stream().sorted((a, b) -> (a.getUserActive().compareTo(b.getUserActive())))
				.collect(Collectors.toList());

		if (ASC.equals(param)) {
			Collections.reverse(users);
		}

		usersList.setUsers(users);
		model.addAttribute(USERS, usersList.getUsers());

		return SHOW_USERS;
	}
}
