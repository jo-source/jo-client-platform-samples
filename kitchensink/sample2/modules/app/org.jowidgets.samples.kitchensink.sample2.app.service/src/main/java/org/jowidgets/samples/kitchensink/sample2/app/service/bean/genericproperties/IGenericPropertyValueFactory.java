package org.jowidgets.samples.kitchensink.sample2.app.service.bean.genericproperties;

import java.io.Serializable;
import java.util.List;

import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericPropertyValue;

public interface IGenericPropertyValueFactory extends Serializable {
	IGenericPropertyValue create();

	List<? extends IGenericPropertyValue> getGenericProperties();
}
