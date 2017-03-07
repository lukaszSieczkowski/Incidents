package pl.incidents.dao;

import java.util.List;

import pl.incidents.model.Incident;
import pl.incidents.model.User;

public interface UserDao {

	public void saveUser(User user);

	public List<User> getUsers();


	

	
}
