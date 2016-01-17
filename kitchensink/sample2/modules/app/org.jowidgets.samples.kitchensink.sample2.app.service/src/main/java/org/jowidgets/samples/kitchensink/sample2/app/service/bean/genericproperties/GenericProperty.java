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
