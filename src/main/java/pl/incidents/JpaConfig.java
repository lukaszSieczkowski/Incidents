package pl.incidents;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

@Configuration
public class JpaConfig {
	@Bean
	public LocalEntityManagerFactoryBean createEMF() {
		LocalEntityManagerFactoryBean emf = new LocalEntityManagerFactoryBean();
		emf.setPersistenceUnitName("myPersistanceUnit");
		return emf;
	}
}
