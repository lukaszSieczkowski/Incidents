package pl.incidents.utils;


import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import pl.incidents.model.Incident;

public class MapCreator {

	public Map<String, Long> createMapForArea(List<Incident> incidents) {

		Map<String, Long> map = incidents.stream()
				.collect(Collectors.groupingBy(a -> a.getArea().getValue(), Collectors.counting()));
		
		return map;
	}

	public Map<String, Long> createMapForEvent(List<Incident> incidents) {

		Map<String, Long> map = incidents.stream()
				.collect(Collectors.groupingBy(a -> a.getTypeOfObservation().getValue(), Collectors.counting()));

		return map;
	}

	public Map<String, Long> createMapForPersonel(List<Incident> incidents) {

		Map<String, Long> map = incidents.stream()
				.collect(Collectors.groupingBy(a -> a.getCathegoryOfPersonel().getValue(), Collectors.counting()));

		return map;
	}
	
	public Map<Integer, Map<String, Long>> createMapForAreaByYear(List<Incident> incidents) {

		Map<Integer, Map<String, Long>> map = incidents.stream()
				.collect(Collectors.groupingBy(a -> a.getIncidentDate().getYear(),
						Collectors.groupingBy(a->a.getArea().getValue(),Collectors.counting())));

		return map;
	}
}
