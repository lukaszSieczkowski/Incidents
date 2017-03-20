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

	public static final String EMPTY_STRING = "";
	public static final String INCIDENTS = "incidents";
	public static final String SHOW_INCIDENTS = "showIncidents";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String MAP = "map";
	public static final String CHART_FORM = "chartForm";
	public static final String SHOW_STATISTICS = "showStatistics";
	public static final String INCORECT_END_DATE_FORMAT = "Incorect End Date format (dd-mm-yyyy)";
	public static final String ALERT = "alert";
	public static final String SLICED_PIE = "SLICED_PIE";
	public static final String D_PIE = "3D_PIE";
	public static final String DONUT = "DONUT";
	public static final int HOUR_DEFAULT = 0;
	public static final int MIN_DEFAULT = 0;
	/**
	 * Component with list of available incidents for logged user
	 */
	private IncidentList incidentList;

	/**
	 * Constructor for SortControler with injected incidentList.
	 * 
	 * @param incidentList
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
		if (!EMPTY_STRING.equals(dateStart)) {

			try {
				LocalDateTime startDate = createDate.createDateFromString(dateStart, HOUR_DEFAULT, MIN_DEFAULT);
				limitedIncident = incidentList.getIncidents().stream()
						.filter(a -> (a.getIncidentDate().isAfter(startDate))).collect(Collectors.toList());
			} catch (StringIndexOutOfBoundsException | NumberFormatException e) {
				String alert;
				alert = INCORECT_END_DATE_FORMAT;
				model.addAttribute(ALERT, alert);

			}

		}
		if (!EMPTY_STRING.equals(dateEnd)) {
			try {
				LocalDateTime endDate = createDate.createDateFromString(dateEnd, HOUR_DEFAULT, MIN_DEFAULT);
				limitedIncident = limitedIncident.stream().filter(a -> (a.getIncidentDate().isBefore(endDate)))
						.collect(Collectors.toList());
			} catch (StringIndexOutOfBoundsException | NumberFormatException e) {
				String alert;
				alert = INCORECT_END_DATE_FORMAT;
				model.addAttribute(ALERT, alert);

			}
		}

		if (!EMPTY_STRING.equals(area)) {
			limitedIncident = limitedIncident.stream().filter(a -> a.getArea().equals(Area.valueOf(area)))
					.collect(Collectors.toList());
		}
		if (!EMPTY_STRING.equals(typeOfObservation)) {
			limitedIncident = limitedIncident.stream()
					.filter(a -> a.getTypeOfObservation().equals(EventType.valueOf(typeOfObservation)))
					.collect(Collectors.toList());
		}
		if (!EMPTY_STRING.equals(cathegoryOfPersonel)) {
			limitedIncident = limitedIncident.stream()
					.filter(a -> a.getCathegoryOfPersonel().equals(CathegoryOfPersonel.valueOf(cathegoryOfPersonel)))
					.collect(Collectors.toList());
		}

		incidentList.setIncidents(limitedIncident);
		model.addAttribute(INCIDENTS, incidentList.getIncidents());
		return SHOW_INCIDENTS;
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

		if (!EMPTY_STRING.equals(dateStart)) {
			try {
				LocalDateTime startDate = createDate.createDateFromString(dateStart, HOUR_DEFAULT, MIN_DEFAULT);
				approvedIncidents = incidents.stream().filter(a -> a.getIncidentDate().isAfter(startDate))
						.collect(Collectors.toList());
			} catch (StringIndexOutOfBoundsException | NumberFormatException e) {
				String alert;
				alert = INCORECT_END_DATE_FORMAT;
				System.out.println(alert);
				model.addAttribute(ALERT, alert);
			}
		} else {
			Incident first = Collections.min(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
			dateStart = createDate.createDateToString(first.getIncidentDate());
		}
		if (!EMPTY_STRING.equals(dateEnd)) {
			try {
				LocalDateTime endDate = createDate.createDateFromString(dateEnd, HOUR_DEFAULT, MIN_DEFAULT);
				approvedIncidents = incidents.stream().filter(a -> a.getIncidentDate().isBefore(endDate))
						.collect(Collectors.toList());
			} catch (StringIndexOutOfBoundsException | NumberFormatException e) {
				String alert;
				alert = INCORECT_END_DATE_FORMAT;
				model.addAttribute(ALERT, alert);
			}
		} else {
			Incident last = Collections.max(approvedIncidents, Comparator.comparing(c -> c.getIncidentDate()));
			dateEnd = createDate.createDateToString(last.getIncidentDate());
		}
		if (Cathegory.AREA.toString().equals(cathegory)
				&& (chartForm.equals(SLICED_PIE) || chartForm.equals(D_PIE) || chartForm.equals(DONUT))) {

			map = mapCreator.createMapForArea(approvedIncidents);
		} else if (cathegory.equals(Cathegory.EVENT_TYPE.toString())) {

			map = mapCreator.createMapForEvent(approvedIncidents);
		}

		else if (Cathegory.CATHEGORY_OF_PERSONEL.toString().equals(cathegory)) {

			map = mapCreator.createMapForPersonel(approvedIncidents);
		}

		model.addAttribute(START_DATE, dateStart);
		model.addAttribute(END_DATE, dateEnd);
		model.addAttribute(MAP, map);
		model.addAttribute(CHART_FORM, chartForm);

		return SHOW_STATISTICS;
	}
}
