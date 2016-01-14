package org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties;

public enum GenericPropertyEntity {
	PERSON("Person");

	private String message;

	private GenericPropertyEntity(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
