package pl.incidents.controllers;

import java.time.LocalDateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import pl.incidents.components.IncidentList;
import pl.incidents.dao.IncidentDao;
import pl.incidents.dao.IncidentDaoImplementation;
import pl.incidents.model.Incident;
import pl.incidents.model.User;
import pl.incidents.model.enums.Area;
import pl.incidents.model.enums.Cathegory;
import pl.incidents.model.enums.CathegoryOfPersonel;
import pl.incidents.model.enums.EventType;
import pl.incidents.model.enums.IncidentStatus;
import pl.incidents.utils.CreateDate;
import pl.incidents.utils.MapCreator;

@Controller
@SessionAttributes("user")
public class FilterController {

	/**
	 * Component with list of available incidents for logged user
	 */
	private IncidentList incidentList;

	/**
	 * Constructor for SortControler with injected incidentList.
	 * 
	 * @param incidentsList
	 *            component with list of incidents
	 */
	@Autowired
	public FilterController(IncidentList incidentList) {
		this.incidentList = incidentList;
	}

	/**
	 * Method Method shows in returned view list of incidents filtered according
	 * to described parameters.
	 * 
	 * @param dateStart
	 *            Starting date of filtered incidents.
	 * @param dateEnd
	 *            Ending date of filtered incidents
	 * @param area
	 *            Area.
	 * @param typeOfObservation
	 *            Type of Observation.
	 * @param cathegoryOfPersonel
	 *            Cathegory of Personel.
	 * @param model
	 *            Holder for sorted incident list attribute.
	 * @param user
	 *            Logged user.
	 * @return showIncidentsView
	 */
	@RequestMapping("/filterIncidents")
	public String filterIncidents(@RequestParam String dateStart, @RequestParam String dateEnd,
			@RequestParam String area, @RequestParam String typeOfObservation, @RequestParam String cathegoryOfPersonel,
			Model model, @ModelAttribute User user) {
		CreateDate createDate = new CreateDate();
		IncidentDao incidentDao = new IncidentDaoImplementation();
		List<Incident> incidents = incidentDao.getIncidents(user);
		incidentList.setIncidents(incidents);

		List<Incident> limitedIncident = incidentList.getIncidents();
		if (!dateStart.equals("")) {
			LocalDateTime startDate = createDate.createDateFromString(dateStart, 0, 0);

			limitedIncident = incidentList.getIncidents().stream().filter(a -> (a.getIncidentDate().isAfter(startDate)))
					.collect(Collectors.toList());
		}
		if (!dateEnd.equals("")) {
			LocalDateTime endDate = createDate.createDateFromString(dateEnd, 0, 0);
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

	/**
	 * Method shows in returned view map of incidents filtered according to
	 * described parameters.Map will be used by Google Charts in returned view.
	 * 
	 * @param dateStart
	 *            Starting date of filtered incidents.
	 * @param dateEnd
	 *            Ending date of filtered incidents.
	 * @param cathegory
	 *            Category of incident (Area, Event Type, Category of
	 *            Personnel).
	 * @param chartForm
	 *            Google chart form.
	 * @param model
	 *            Holder for sorted incident map attribute.
	 * @return showStatistics view
	 */
	@RequestMapping("/filterForChart")
	public String filterIncidentsForCharts(@RequestParam String dateStart, @RequestParam String dateEnd,
			@RequestParam String cathegory, @RequestParam String chartForm, Model model) {

		CreateDate createDate = new CreateDate();
		MapCreator mapCreator = new MapCreator();
		List<Incident> incidents = incidentList.getIncidents();
		List<Incident> approvedIncidents = incidents.stream()
				.filter(a -> a.getIncidentStatus() != IncidentStatus.NOT_APPROVED).collect(Collectors.toList());

		Map<String, Long> map = new HashMap<>();

		if (!dateStart.equals("")) {
			LocalDateTime startDate = createDate.createDateFromString(dateStart, 0, 0);
			approvedIncidents = incidents.stream().filter(a -> a.getIncidentDate().isAfter(startDate))
					.collect(Collectors.toList());
		} else {
			Incident first = Collections.min(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
			dateStart = createDate.createDateToString(first.getIncidentDate());
		}
		if (!dateEnd.equals("")) {
			LocalDateTime endDate = createDate.createDateFromString(dateEnd, 0, 0);
			approvedIncidents = incidents.stream().filter(a -> a.getIncidentDate().isBefore(endDate))
					.collect(Collectors.toList());
		} else {
			Incident last = Collections.max(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
			dateEnd = createDate.createDateToString(last.getIncidentDate());
		}
		if (cathegory.equals(Cathegory.AREA.toString())
				&& (chartForm.equals("SLICED_PIE") || chartForm.equals("3D_PIE") || chartForm.equals("DONUT"))) {

			map = mapCreator.createMapForArea(approvedIncidents);
		} else if (cathegory.equals(Cathegory.EVENT_TYPE.toString())) {

			map = mapCreator.createMapForEvent(approvedIncidents);
		}

		else if (cathegory.equals(Cathegory.CATHEGORY_OF_PERSONEL.toString())) {

			map = mapCreator.createMapForPersonel(approvedIncidents);
		}

		model.addAttribute("startDate", dateStart);
		model.addAttribute("endDate", dateEnd);
		model.addAttribute("map", map);
		model.addAttribute("chartForm", chartForm);

		return "showStatistics";
	}
}
