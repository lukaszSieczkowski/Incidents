package pl.incidents.dao;

import java.util.List;

import pl.incidents.model.Incident;

public interface IncidentDao {
	void saveIncident(Incident incident);

	public List<Incident> getIncidents();

	public Incident getIncident(long id);
}
