package pl.incidents.model.enums;

public enum Area {

	OFFICE("Office"), WORKSHOP("Workshop"), PARK_LOOT("Park Loot"), PROJECT_SITE("Project Site");

	private String value;

	private Area(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
