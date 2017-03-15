package pl.incidents.components;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.incidents.model.Incident;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IncidentList {

	private List<Incident> incidents;
	
	public IncidentList(List<Incident> incidents) {
		this.incidents = incidents;
	}

	public List<Incident> getIncidents() {
		return incidents;
	}

	public void setIncidents(List<Incident> incidents) {
		this.incidents = incidents;
	}

}
