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

	void setEntity(GenericPropertyEntity entity);

	@NotNull
	@Size(min = 1, max = 200)
	String getPropertyName();

	void setPropertyName(String propertyName);

	@NotNull
	@Size(min = 1, max = 100)
	String getLabel();

	void setLabel(String label);

	@Size(min = 1, max = 2000)
	String getDescription();

	void setDescription(String description);

	@NotNull
	GenericPropertyType getType();

	void setType(GenericPropertyType type);

	Integer getSortIndex();

	void setSortIndex(Integer sortIndex);
}
