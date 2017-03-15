package pl.incidents.dao;

import java.util.List;

import pl.incidents.model.Incident;
import pl.incidents.model.User;

public interface IncidentDao {

	/**
	 * Save incident in data base
	 * 
	 * @param incident
	 *            Incident with will be stored in data base.
	 */
	void saveIncident(Incident incident);

	/**
	 * Reads all available incidents from data base for user type.
	 * 
	 * @param user
	 * @return List of incidents
	 */
	public List<Incident> getIncidents(User user);

	/**
	 * Reads specific incident from data base
	 * 
	 * @param id
	 *            User id.
	 * @return Incident
	 */
	public Incident getIncident(long id);

	/**
	 * Reads incidents from data base reported by specific user
	 * 
	 * @param id
	 *            User id
	 * @return Incident list
	 */
	public List<Incident> getIncidentsByUserId(long id);

	/**
	 * Updates incident
	 * 
	 * @param incident
	 */
	public void updateIncident(Incident incident);

}
