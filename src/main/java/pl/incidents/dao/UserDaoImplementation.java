package pl.incidents.dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.persistence.TypedQuery;

import pl.incidents.model.User;
import pl.incidents.model.enums.UserType;

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
	


	public User getUser(long id) {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager em = emFactory.createEntityManager();
		User user = em.find(User.class, id);
		em.close();
		
		return user;
	}

	public void updateUser(User user) {
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		EntityManager em = emFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(user);
		tx.commit();
		em.close();
		
	}
}
