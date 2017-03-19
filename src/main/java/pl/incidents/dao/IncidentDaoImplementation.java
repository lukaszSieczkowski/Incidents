package pl.incidents.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import pl.incidents.model.Incident;
import pl.incidents.model.User;
import pl.incidents.model.enums.UserType;

public class IncidentDaoImplementation implements IncidentDao {

	EntityManagerFactory emFactory;
	EntityManager entityManager;
	EntityTransaction tx;

	public IncidentDaoImplementation() {

	}

	/**
	 * Save incident in data base
	 * 
	 * @param incident
	 *            Incident with will be stored in data base.
	 */

	public void saveIncident(Incident incident) {
		emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		entityManager = emFactory.createEntityManager();
		tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(incident);
		tx.commit();
		entityManager.close();
	}

	/**
	 * Reads all available incidents from data base for user type.
	 * 
	 * @param user
	 * @return List of incidents
	 */

	public List<Incident> getIncidents(User user) {
		emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		entityManager = emFactory.createEntityManager();
		TypedQuery<Incident> query = null;
		List<Object[]> list = null;
		List<Incident> resultList = new ArrayList<>();

		if (user.getUserType().equals(UserType.USER)) {
			list = entityManager
					.createQuery(
							"SELECT d, m  FROM User d, Incident m WHERE d = m.user AND m.user='" + user.getId() + "'")
					.getResultList();
		} else {
			list = entityManager.createQuery("SELECT d, m  FROM User d, Incident m WHERE d = m.user").getResultList();
		}

		for (Object[] p : list) {
			User userFromDB = (User) p[0];
			Incident incidentFromDB = (Incident) p[1];
			incidentFromDB.setUser(userFromDB);
			resultList.add(incidentFromDB);
		}

		return resultList;
	}

	/**
	 * Reads incidents from data base reported by specific user
	 * 
	 * @param id
	 *            User id
	 * @return Incident list
	 */

	public List<Incident> getIncidentsByUserId(long id) {
		emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		entityManager = emFactory.createEntityManager();
		TypedQuery<Incident> query = null;
		List<Object[]> list = null;
		List<Incident> resultList = new ArrayList<>();

		list = entityManager
				.createQuery("SELECT d, m  FROM User d, Incident m WHERE d = m.user AND m.user='" + id + "'")
				.getResultList();

		for (Object[] p : list) {
			User userFromDB = (User) p[0];
			Incident incidentFromDB = (Incident) p[1];
			incidentFromDB.setUser(userFromDB);
			resultList.add(incidentFromDB);
		}

		return resultList;
	}

	/**
	 * Reads specific incident from data base
	 * 
	 * @param id
	 *            Incident id.
	 * @return Incident
	 */

	public Incident getIncident(long id) {
		emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		entityManager = emFactory.createEntityManager();
		Incident incident = entityManager.find(Incident.class, id);
		entityManager.close();

		return incident;
	}

	/**
	 * Updates incident
	 * 
	 * @param incident
	 */

	public void updateIncident(Incident incident) {
		emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		entityManager = emFactory.createEntityManager();
		tx = entityManager.getTransaction();
		tx.begin();
		entityManager.merge(incident);
		tx.commit();
		entityManager.close();
	}

}
