package pl.incidents.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.incidents.components.IncidentList;
import pl.incidents.model.Incident;

@Controller
public class SortController {

	private IncidentList incidentList;

	@Autowired
	public SortController(IncidentList incidentList) {
		this.incidentList = incidentList;
	}

	@RequestMapping("/sortByIncidentDateDesc")
	public String sortByIncudentDateDesc(Model model) {
		List<Incident> incidents = incidentList.getIncidents();

		incidents = incidents.stream().sorted((a, b) -> (a.getIncidentDate().compareTo(b.getIncidentDate())))
				.collect(Collectors.toList());
		incidentList.setIncidents(incidents);
		model.addAttribute("incidents", incidentList.getIncidents());
		return "uShowIncidents";
	}

	@RequestMapping("/sortByIncidentDateAsc")
	public String sortByIncudentDateAsc(Model model) {
		List<Incident> incidents = incidentList.getIncidents();

		incidents = incidents.stream().sorted((a, b) -> (a.getIncidentDate().compareTo(b.getIncidentDate())))
				.collect(Collectors.toList());
		Collections.reverse(incidents);

		incidentList.setIncidents(incidents);
		model.addAttribute("incidents", incidentList.getIncidents());
		return "uShowIncidents";
	}

	@RequestMapping("/sortByAreaDesc")
	public String sortByAreaDesc(Model model) {
		List<Incident> incidents = incidentList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getArea().compareTo(b.getArea())))
				.collect(Collectors.toList());
		incidentList.setIncidents(incidents);
		model.addAttribute("incidents", incidentList.getIncidents());
		return "uShowIncidents";
	}

	@RequestMapping("/sortByAreaAsc")
	public String sortByAreaAsc(Model model) {
		List<Incident> incidents = incidentList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getArea().compareTo(b.getArea())))
				.collect(Collectors.toList());
		Collections.reverse(incidents);
		incidentList.setIncidents(incidents);
		model.addAttribute("incidents", incidentList.getIncidents());
		return "uShowIncidents";
	}

	@RequestMapping("/sortByEventDesc")
	public String sortByEventDesc(Model model) {
		List<Incident> incidents = incidentList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getTypeOfObservation().compareTo(b.getTypeOfObservation())))
				.collect(Collectors.toList());
		incidentList.setIncidents(incidents);
		model.addAttribute("incidents", incidentList.getIncidents());
		return "uShowIncidents";
	}

	@RequestMapping("/sortByEventAsc")
	public String sortByEventAsc(Model model) {
		List<Incident> incidents = incidentList.getIncidents();
		incidents = incidents.stream().sorted((a, b) -> (a.getTypeOfObservation().compareTo(b.getTypeOfObservation())))
				.collect(Collectors.toList());
		Collections.reverse(incidents);
		incidentList.setIncidents(incidents);
		model.addAttribute("incidents", incidentList.getIncidents());
		return "uShowIncidents";
	}

	@RequestMapping("/sortByPersonelDesc")
	public String sortByPersonelDesc(Model model) {
		List<Incident> incidents = incidentList.getIncidents();
		incidents = incidents.stream()
				.sorted((a, b) -> (a.getCathegoryOfPersonel().compareTo(b.getCathegoryOfPersonel())))
				.collect(Collectors.toList());
		incidentList.setIncidents(incidents);
		model.addAttribute("incidents", incidentList.getIncidents());
		return "uShowIncidents";
	}

	@RequestMapping("/sortByPersonelAsc")
	public String sortByPersoneltAsc(Model model) {
		List<Incident> incidents = incidentList.getIncidents();
		incidents = incidents.stream()
				.sorted((a, b) -> (a.getCathegoryOfPersonel().compareTo(b.getCathegoryOfPersonel())))
				.collect(Collectors.toList());
		Collections.reverse(incidents);
		incidentList.setIncidents(incidents);
		model.addAttribute("incidents", incidentList.getIncidents());
		return "uShowIncidents";
	}
	/*
	 * @RequestMapping("/sortByReporterlDesc") public String
	 * sortByReporterDesc(Model model){ List<Incident> incidents =
	 * incidentList.getIncidents(); incidents=
	 * incidents.stream().sorted((a,b)->(a.getCathegoryOfPersonel().compareTo(b.
	 * getCathegoryOfPersonel()))).collect(Collectors.toList());
	 * incidentList.setIncidents(incidents); model.addAttribute("incidents",
	 * incidentList.getIncidents()); return "uShowIncidents"; }
	 * 
	 * @RequestMapping("/sortByReporterlAsc") public String
	 * sortByReportertAsc(Model model){ List<Incident> incidents =
	 * incidentList.getIncidents(); incidents=
	 * incidents.stream().sorted((a,b)->(a.getCathegoryOfPersonel().compareTo(b.
	 * getCathegoryOfPersonel()))).collect(Collectors.toList());
	 * Collections.reverse(incidents); incidentList.setIncidents(incidents);
	 * model.addAttribute("incidents", incidentList.getIncidents()); return
	 * "uShowIncidents"; }
	 */
}
