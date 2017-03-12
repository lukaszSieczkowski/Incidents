package pl.incidents.dao;

import java.util.List;

import pl.incidents.model.Incident;
import pl.incidents.model.User;

public interface IncidentDao {

	void saveIncident(Incident incident);

	public List<Incident> getIncidents(User user);

	public Incident getIncident(long id);
	
	public List<Incident> getIncidentsByUserId(long id);
	public void updateIncident(Incident incident);

}
