package org.jowidgets.samples.kitchensink.sample2.app.service.descriptor;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IBeanPropertyBluePrint;
import org.jowidgets.cap.common.api.sort.Sort;
import org.jowidgets.cap.common.tools.bean.BeanDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.GenericPropertyType;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericProperty;
import org.jowidgets.samples.kitchensink.sample2.app.common.lookup.LookUpIds;

public class GenericPropertyDtoDescriptorBuilder extends BeanDtoDescriptorBuilder {

	public GenericPropertyDtoDescriptorBuilder() {
		this("Generic Property", "Generic Properties");
	}

	public GenericPropertyDtoDescriptorBuilder(final String labelSigular, final String labelPlural) {
		super(IGenericProperty.class);

		setLabelSingular(labelSigular);
		setLabelPlural(labelPlural);

		setDefaultSorting(Sort.create(IGenericProperty.ENTITY_PROPERTY), Sort.create(IGenericProperty.SORT_INDEX_PROPERTY));

		IBeanPropertyBluePrint propertyBp;

		propertyBp = addProperty(IBean.ID_PROPERTY);
		propertyBp.setLabel("Id");
		propertyBp.setDescription("The generic properties technical identifier");

		propertyBp = addProperty(IGenericProperty.ENTITY_PROPERTY);
		propertyBp.setLabel("Entity");
		propertyBp.setMandatory(true);
		propertyBp.setLookUpValueRange(LookUpIds.GENERIC_PROPERTY_ENTITY);

		propertyBp = addProperty(IGenericProperty.PROPERTY_NAME_PROPERTY);
		propertyBp.setLabel("Property name");

		propertyBp = addProperty(IGenericProperty.LABEL_PROPERTY);
		propertyBp.setLabel("Label");

		propertyBp = addProperty(IGenericProperty.DESCRIPTION_PROPERTY);
		propertyBp.setLabel("Description");

		propertyBp = addProperty(IGenericProperty.TYPE_PROPERTY);
		propertyBp.setLabel("Type");
		propertyBp.setLookUpValueRange(LookUpIds.GENERIC_PROPERTY_TYPE);
		propertyBp.setDefaultValue(GenericPropertyType.STRING);

		propertyBp = addProperty(IGenericProperty.SORT_INDEX_PROPERTY);
		propertyBp.setLabel("Sort index");

		propertyBp = addProperty(IBean.VERSION_PROPERTY);
		propertyBp.setLabel("Version");
		propertyBp.setDescription("The version of the dataset");
	}
}
