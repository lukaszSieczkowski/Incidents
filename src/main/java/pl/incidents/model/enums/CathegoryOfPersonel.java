package pl.incidents.model.enums;

public enum CathegoryOfPersonel {
	
	OWN("Own"), CONTRACTOR("Contractor"), CLIENT("Client"), THIRD_PARTY("Third Party");
	
	private String value;

	private CathegoryOfPersonel(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
