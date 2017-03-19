package pl.incidents.dao;

import java.util.List;

import pl.incidents.model.User;

public interface UserDao {

	/**
	 * Saves user 
	 * 
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * Reads all users 
	 * 
	 * @return List of users
	 */
	public List<User> getUsers();

	/**
	 * Reads specific user
	 * 
	 * @param id
	 *            User id
	 * @return user
	 */
	public User getUser(long id);

	/**
	 * Updates user
	 * 
	 * @param user
	 */
	public void updateUser(User user);

}
