package org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties;

public enum GenericPropertyType {
	STRING("String"),
	STRING_LIST("String list");

	private String message;

	private GenericPropertyType(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
