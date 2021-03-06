package pl.incidents.dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.persistence.TypedQuery;

import pl.incidents.model.User;

public class UserDaoImplementation implements UserDao {

	EntityManagerFactory emFactory;
	EntityManager entityManager;
	EntityTransaction tx;

	public UserDaoImplementation() {
		
	}

	/**
	 * Save user in data base
	 * 
	 * @param user
	 */
	@Override
	public void saveUser(User user) {
		emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		entityManager = emFactory.createEntityManager();
		tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(user);
		tx.commit();
		entityManager.close();
	}

	/**
	 * Read all users from data base
	 * 
	 * @return List of users
	 */
	@Override
	public ArrayList<User> getUsers() {
		emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		entityManager = emFactory.createEntityManager();
		TypedQuery<User> query = entityManager.createQuery("SELECT c FROM User c", User.class);
		ArrayList<User> resultList = (ArrayList<User>) query.getResultList();
		return resultList;
	}

	/**
	 * Read specific user from data base
	 * 
	 * @param id
	 *            User id
	 * @return user
	 */
	public User getUser(long id) {
		emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		entityManager = emFactory.createEntityManager();
		User user = entityManager.find(User.class, id);
		entityManager.close();
		return user;
	}

	/**
	 * Update user
	 * 
	 * @param user
	 */
	public void updateUser(User user) {
		emFactory = Persistence.createEntityManagerFactory("myPersistanceUnit");
		entityManager = emFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.merge(user);
		tx.commit();
		entityManager.close();
	}
}
