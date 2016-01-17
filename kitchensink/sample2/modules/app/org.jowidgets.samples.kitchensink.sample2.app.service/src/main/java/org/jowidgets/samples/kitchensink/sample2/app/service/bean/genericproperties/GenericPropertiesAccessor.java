/*
 * Copyright (c) 2016, bentele
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jowidgets.samples.kitchensink.sample2.app.service.bean.genericproperties;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.GenericPropertyEntity;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericPropertyValue;
import org.jowidgets.samples.kitchensink.sample2.app.service.loader.GenericPropertyLoader;

public class GenericPropertiesAccessor implements Serializable {

	private static final long serialVersionUID = 1L;

	private final IGenericPropertyValueFactory genericPropertyValueFactory;
	private final GenericPropertyEntity entity;

	public GenericPropertiesAccessor(
		final IGenericPropertyValueFactory genericPropertyValueFactory,
		final GenericPropertyEntity entity) {
		this.genericPropertyValueFactory = genericPropertyValueFactory;
		this.entity = entity;
	}

	@SuppressWarnings("unchecked")
	public void setValue(final String propertyName, final Object value) {
		final GenericProperty genericProperty = GenericPropertyLoader.getGenericProperty(entity, propertyName);
		if (genericProperty == null) {
			// case: a property not mapped as generic property, and with no setter
			return;
		}

		final Collection<String> values;
		if (value == null) {
			values = Arrays.asList();
		}
		else {
			switch (genericProperty.getType()) {
				case STRING:
					if (String.class.isAssignableFrom(value.getClass())) {
						if (value != null && !value.equals("")) {
							values = Arrays.asList((String) value);
						}
						else {
							values = Arrays.asList();
						}
					}
					else {
						throw new IllegalArgumentException("Property "
							+ genericProperty.getLabel()
							+ " defined as string but value isn't of type string.");
					}
					break;
				case STRING_LIST:
					if (Collection.class.isAssignableFrom(value.getClass())) {
						values = (Collection<String>) value;
					}
					else {
						throw new IllegalArgumentException("Property "
							+ genericProperty.getLabel()
							+ " defined as list of strings but value isn't of type Collection.");
					}
					break;
				default:
					throw new RuntimeException("Not implemented");
			}
		}

		final List<IGenericPropertyValue> currentValues = getCurrentValues(propertyName);
		if (values.size() == 1 && currentValues.size() == 1) {
			// special case: modification of non-collection property
			final IGenericPropertyValue genericPropertyValue = currentValues.get(0);
			genericPropertyValue.setValue(values.iterator().next());
		}
		else {
			final List<IGenericPropertyValue> genericPropsToRemove = new LinkedList<IGenericPropertyValue>();
			for (final IGenericPropertyValue genericPropertyValue : currentValues) {
				if (!values.contains(genericPropertyValue.getValue())) {
					genericPropsToRemove.add(genericPropertyValue);
				}
			}
			genericPropertyValueFactory.getGenericProperties().removeAll(genericPropsToRemove);

			final Set<String> currentGenericPropValues = new HashSet<String>();
			for (final IGenericPropertyValue genericPropertyValue : genericPropertyValueFactory.getGenericProperties()) {
				if (genericPropertyValue.getPropertyName().equals(propertyName)) {
					currentGenericPropValues.add(genericPropertyValue.getValue());
				}
			}

			for (final Object genericPropValue : values) {
				if (!currentGenericPropValues.contains(genericPropValue)) {
					final IGenericPropertyValue genericPropertyValue = genericPropertyValueFactory.create();
					genericPropertyValue.setPropertyName(propertyName);
					genericPropertyValue.setValue(genericPropValue.toString());
				}
			}
		}
	}

	private List<IGenericPropertyValue> getCurrentValues(final String propertyName) {
		final List<IGenericPropertyValue> result = new LinkedList<IGenericPropertyValue>();
		if (genericPropertyValueFactory.getGenericProperties() != null) {
			for (final IGenericPropertyValue genericProperty : genericPropertyValueFactory.getGenericProperties()) {
				if (propertyName.equals(genericProperty.getPropertyName())) {
					result.add(genericProperty);
				}
			}
		}
		return result;
	}

	public Object getValue(final String propertyName) {
		final GenericProperty genericProperty = GenericPropertyLoader.getGenericProperty(entity, propertyName);
		if (genericProperty == null) {
			throw new IllegalArgumentException("Unknown property name: " + propertyName);
		}

		final List<String> values = new LinkedList<String>();
		for (final IGenericPropertyValue genericPropertyValue : genericPropertyValueFactory.getGenericProperties()) {
			if (propertyName.equals(genericPropertyValue.getPropertyName())) {
				values.add(genericPropertyValue.getValue());
			}
		}
		Collections.sort(values);

		switch (genericProperty.getType()) {
			case STRING:
				if (values.size() == 1) {
					return values.get(0);
				}
				else {
					return null;
				}
			case STRING_LIST:
				return values;
			default:
				throw new RuntimeException("not implemented");
		}
	}
}
