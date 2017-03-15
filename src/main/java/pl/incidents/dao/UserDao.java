package pl.incidents.dao;


import java.util.List;

import pl.incidents.model.User;

public interface UserDao {
	
	/**
	 * Save user in data base
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * Read all users from data base
	 * @return List of users
	 */
	public List<User> getUsers();

	/**
	 * Read specific user from data base
	 * @param id User id
	 * @return user
	 */
	public User getUser(long id);
	
	/**
	 * Update user
	 * 
	 * @param user
	 */
	public void updateUser(User user);

}
