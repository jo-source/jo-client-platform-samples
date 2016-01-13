package org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jowidgets.cap.common.api.bean.IBean;

public interface IGenericProperty extends IBean {

	String ENTITY_PROPERTY = "entity";
	String PROPERTY_NAME_PROPERTY = "propertyName";
	String LABEL_PROPERTY = "label";
	String DESCRIPTION_PROPERTY = "description";
	String TYPE_PROPERTY = "type";
	String SORT_INDEX_PROPERTY = "sortIndex";

	List<String> ALL_PROPERTIES = Arrays.asList(
			ENTITY_PROPERTY,
			PROPERTY_NAME_PROPERTY,
			LABEL_PROPERTY,
			DESCRIPTION_PROPERTY,
			TYPE_PROPERTY,
			SORT_INDEX_PROPERTY,
			IBean.ID_PROPERTY,
			IBean.VERSION_PROPERTY);

	@NotNull
	GenericPropertyEntity getEntity();

	void setEntity(final GenericPropertyEntity entity);

	@NotNull
	@Size(min = 1, max = 200)
	String getPropertyName();

	void setPropertyName(final String propertyName);

	@NotNull
	@Size(min = 1, max = 100)
	String getLabel();

	void setLabel(final String label);

	@Size(min = 1, max = 2000)
	String getDescription();

	void setDescription(final String description);

	@NotNull
	GenericPropertyType getType();

	void setType(GenericPropertyType type);

	Integer getSortIndex();

	void setSortIndex(Integer sortIndex);
}
