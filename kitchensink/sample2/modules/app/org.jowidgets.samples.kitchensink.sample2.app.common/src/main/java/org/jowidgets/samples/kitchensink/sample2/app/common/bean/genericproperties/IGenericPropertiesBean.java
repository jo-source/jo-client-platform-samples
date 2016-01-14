package org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties;

import java.util.List;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IPropertyMap;

public interface IGenericPropertiesBean extends IPropertyMap, IBean {
	List<? extends IGenericPropertyValue> getGenericProperties();
}
