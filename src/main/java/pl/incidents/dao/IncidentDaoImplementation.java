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

	public void saveIncident(Incident incident) {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager entityManager = emFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(incident);
		tx.commit();
		entityManager.close();
	}

	public List<Incident> getIncidents(User user) {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager entityManager = emFactory.createEntityManager();

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

	public Incident getIncident(long id) {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager em = emFactory.createEntityManager();
		Incident incident = em.find(Incident.class, id);
		em.close();
		
		return incident;
	}
}
