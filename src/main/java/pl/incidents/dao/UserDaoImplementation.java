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

public class UserDaoImplementation implements UserDao {

	@Override
	public void saveUser(User user) {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager entityManager = emFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(user);
		tx.commit();
		entityManager.close();
	}

	@Override
	public ArrayList<User> getUsers() {
	
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager entityManager = emFactory.createEntityManager();
	
		TypedQuery<User> query = entityManager.createQuery("SELECT c FROM User c", User.class);
		ArrayList<User> resultList = (ArrayList<User>) query.getResultList();
		
		return resultList;
	
	}

	
	
}
