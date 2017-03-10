package pl.incidents.model.enums;

public enum EventType {

	UNSAFE_ACT("Unsafe Act"), UNSAFE_CONDITIONS("Unsafe Conditions"), SAFE_BEHAVIOURS("Safe Behaviours");

	private String value;

	private EventType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
