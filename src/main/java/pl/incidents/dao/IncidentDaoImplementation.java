package pl.incidents.dao;

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

		if (user.getUserType().equals(UserType.USER)) {
			query = entityManager.createQuery("SELECT c FROM Incident c WHERE c.user='" + user.getId() + "'",
					Incident.class);
		} else {
			query = entityManager.createQuery("SELECT c FROM Incident c", Incident.class);
		}
		List<Incident> resultList = query.getResultList();
	
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
