package org.jowidgets.samples.kitchensink.sample2.app.service.bean.genericproperties;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.GenericPropertyEntity;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.GenericPropertyType;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericProperty;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Bean;

@Entity
@Table
public class GenericProperty extends Bean implements IGenericProperty {

	@Basic
	@Column(length = 30, nullable = false)
	@Enumerated(EnumType.STRING)
	private GenericPropertyEntity entity;

	@Basic
	@Column(length = 100, nullable = false)
	private String propertyName;

	@Basic
	@Column(length = 100, nullable = false)
	private String label;

	@Basic
	@Column(length = 2000, nullable = true)
	private String description;

	@Basic
	@Column(length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private GenericPropertyType type;

	@Basic
	@Column(nullable = true)
	private Integer sortIndex;

	@Override
	public GenericPropertyEntity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(final GenericPropertyEntity entity) {
		this.entity = entity;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public GenericPropertyType getType() {
		return type;
	}

	@Override
	public void setType(final GenericPropertyType type) {
		this.type = type;
	}

	@Override
	public Integer getSortIndex() {
		return sortIndex;
	}

	@Override
	public void setSortIndex(final Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

}
