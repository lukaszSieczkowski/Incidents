package pl.incidents;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pl.incidents.model.Incident;
import pl.incidents.model.User;

@Configuration
@ComponentScan(value = "pl.incidents.model")
public class BeanConfig {

	/**
	 * Registers User bean in Spring Container
	 * 
	 * @return user
	 */
	@Bean
	public User getSUser() {
		return new User();
	}

	/**
	 * Registers Incident bean in Spring Container
	 * 
	 * @return
	 */
	@Bean
	public Incident getIncident() {
		return new Incident();
	}
}
