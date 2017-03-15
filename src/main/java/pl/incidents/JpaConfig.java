package pl.incidents;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

@Configuration
public class JpaConfig {

	/**
	 * Registers Bean whose is a connector JPA and Spring
	 * 
	 * @return LocalEntityBeanFactory
	 */
	@Bean
	public LocalEntityManagerFactoryBean createEMF() {
		LocalEntityManagerFactoryBean emf = new LocalEntityManagerFactoryBean();
		emf.setPersistenceUnitName("myPersistanceUnit");
		return emf;
	}

}
