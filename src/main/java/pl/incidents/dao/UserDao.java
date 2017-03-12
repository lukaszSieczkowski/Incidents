package pl.incidents.dao;

import java.util.ArrayList;
import java.util.List;

import pl.incidents.model.User;

public interface UserDao {

	public void saveUser(User user);

	public List<User> getUsers();

	public User getUser(long id);
	
	public void updateUser(User user);

	
}
