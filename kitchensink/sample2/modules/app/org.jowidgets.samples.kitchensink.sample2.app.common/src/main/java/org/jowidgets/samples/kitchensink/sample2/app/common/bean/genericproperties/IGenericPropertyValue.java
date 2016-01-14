package org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jowidgets.cap.common.api.bean.IBean;

public interface IGenericPropertyValue extends IBean {
	String PROPERTY_NAME_PROPERTY = "propertyName";
	String VALUE_PROPERTY = "value";

	@NotNull
	@Size(min = 1, max = 100)
	String getPropertyName();

	void setPropertyName(String propertyName);

	@NotNull
	@Size(min = 1, max = 2000)
	String getValue();

	void setValue(final String value);

}
