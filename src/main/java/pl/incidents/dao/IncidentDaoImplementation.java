package pl.incidents.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import pl.incidents.model.Incident;

public class IncidentDaoImplementation implements IncidentDao {

	@Override
	public void saveIncident(Incident incident) {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager entityManager = emFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(incident);
		tx.commit();
		entityManager.close();
	}

	@Override
	public List<Incident> getIncidents() {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager entityManager = emFactory.createEntityManager();
		TypedQuery<Incident> query = entityManager.createQuery("SELECT c FROM Incident c", Incident.class);
		List<Incident> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public Incident getIncident(long id) {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager em = emFactory.createEntityManager();
		Incident incident = em.find(Incident.class, id);
		em.close();
		return incident;
	}
}
