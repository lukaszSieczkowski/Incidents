package pl.incidents.utils;

import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import pl.incidents.model.Incident;

public class MapCreator {

	/**
	 * Creates data map from available incidents. Key - area, Value - number of
	 * incidents
	 * 
	 * @param incidents
	 *            List of available incidents
	 * @return Map of incidents
	 */
	public Map<String, Long> createMapForArea(List<Incident> incidents) {

		Map<String, Long> map = incidents.stream()
				.collect(Collectors.groupingBy(a -> a.getArea().getValue(), Collectors.counting()));

		return map;
	}

	/**
	 * Creates data map from available incidents. Key - event, Value - number of
	 * incidents
	 * 
	 * @param incidents
	 *            List of available incidents
	 * @return Map of incidents
	 */
	public Map<String, Long> createMapForEvent(List<Incident> incidents) {

		Map<String, Long> map = incidents.stream()
				.collect(Collectors.groupingBy(a -> a.getTypeOfObservation().getValue(), Collectors.counting()));

		return map;
	}

	/**
	 * Creates data map from available incidents. Key - category of personnel,
	 * Value - number of incidents
	 * 
	 * @param incidents
	 *            List of available incidents
	 * @return Map of incidents
	 */
	public Map<String, Long> createMapForPersonel(List<Incident> incidents) {

		Map<String, Long> map = incidents.stream()
				.collect(Collectors.groupingBy(a -> a.getCathegoryOfPersonel().getValue(), Collectors.counting()));

		return map;
	}

}
