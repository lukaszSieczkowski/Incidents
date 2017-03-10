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
import pl.incidents.components.UserList;
import pl.incidents.dao.IncidentDao;
import pl.incidents.dao.IncidentDaoImplementation;
import pl.incidents.model.Incident;
import pl.incidents.model.User;
import pl.incidents.model.enums.Area;
import pl.incidents.model.enums.Cathegory;
import pl.incidents.model.enums.CathegoryOfPersonel;
import pl.incidents.model.enums.EventType;
import pl.incidents.utils.CreateDate;
import pl.incidents.utils.MapCreator;

@Controller
@SessionAttributes("user")
public class FilterController {

	private IncidentList incidentList;
	private UserList usersList;

	@Autowired
	public FilterController(IncidentList incidentList, UserList usersList) {
		this.incidentList = incidentList;
		this.usersList = usersList;
	}

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

	@RequestMapping("/filterForChart")
	public String filterIncidentsForCharts(@RequestParam String dateStart, @RequestParam String dateEnd,
			@RequestParam String cathegory, @RequestParam String chartForm, Model model) {

		CreateDate createDate = new CreateDate();
		MapCreator mapCreator = new MapCreator();
		List<Incident> incidents = incidentList.getIncidents();

		Map<String, Long> map = new HashMap<>();

		if (!dateStart.equals("")) {
			LocalDateTime startDate = createDate.createDateFromString(dateStart, 0, 0);
			incidents = incidents.stream().filter(a -> a.getIncidentDate().isAfter(startDate))
					.collect(Collectors.toList());
		} else {
			Incident first = Collections.min(incidents, Comparator.comparing(c -> c.getIncidentDate()));
			dateStart = createDate.createDateToString(first.getIncidentDate());
		}
		if (!dateEnd.equals("")) {
			LocalDateTime endDate = createDate.createDateFromString(dateEnd, 0, 0);
			incidents = incidents.stream().filter(a -> a.getIncidentDate().isBefore(endDate))
					.collect(Collectors.toList());
		} else {
			Incident last = Collections.max(incidents, Comparator.comparing(c -> c.getIncidentDate()));
			dateEnd = createDate.createDateToString(last.getIncidentDate());
		}
		if (cathegory.equals(Cathegory.AREA.toString())
				&& (chartForm.equals("SLICED_PIE") || chartForm.equals("3D_PIE") || chartForm.equals("DONUT"))) {
			System.out.println("AREA OK");

			map = mapCreator.createMapForArea(incidents);
		} else if (cathegory.equals(Cathegory.EVENT_TYPE.toString())) {

			map = mapCreator.createMapForEvent(incidents);
		}

		else if (cathegory.equals(Cathegory.CATHEGORY_OF_PERSONEL.toString())) {

			map = mapCreator.createMapForPersonel(incidents);
		}

		model.addAttribute("startDate", dateStart);
		model.addAttribute("endDate", dateEnd);
		model.addAttribute("map", map);
		model.addAttribute("chartForm", chartForm);
		
		return "showStatistics";
	}
}