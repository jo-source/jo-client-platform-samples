/*
 * Copyright (c) 2011, grossmann
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
package org.jowidgets.samples.kitchensink.sample2.app.common.bean;

import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jowidgets.cap.common.api.annotation.BeanValidator;
import org.jowidgets.cap.common.api.annotation.PropertyValidator;
import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.security.common.api.annotation.CreateAuthorization;
import org.jowidgets.cap.security.common.api.annotation.DeleteAuthorization;
import org.jowidgets.cap.security.common.api.annotation.ReadAuthorization;
import org.jowidgets.cap.security.common.api.annotation.UpdateAuthorization;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericPropertiesBean;
import org.jowidgets.samples.kitchensink.sample2.app.common.security.AuthKeys;
import org.jowidgets.samples.kitchensink.sample2.app.common.validation.PersonNameLastNameValidator;
import org.jowidgets.samples.kitchensink.sample2.app.common.validation.PersonNameUppercaseValidator;
import org.jowidgets.samples.kitchensink.sample2.app.common.validation.PersonNameWordCountValidator;

@BeanValidator({PersonNameWordCountValidator.class, PersonNameLastNameValidator.class})
@CreateAuthorization(AuthKeys.CREATE_PERSON)
@ReadAuthorization(AuthKeys.READ_PERSON)
@UpdateAuthorization(AuthKeys.UPDATE_PERSON)
@DeleteAuthorization(AuthKeys.DELETE_PERSON)
public interface IPerson extends IBean, IGenericPropertiesBean {

	String NAME_PROPERTY = "name";
	String LAST_NAME_PROPERTY = "lastname";
	String LOGIN_NAME_PROPERTY = "loginName";
	String GENDER_PROPERTY = "gender";
	String ACTIVE_PROPERTY = "active";
	String COUNTRY_ID_PROPERTY = "countryId";
	String ROLE_IDS_PROPERTY = "roleIds";

	List<String> ALL_PROPERTIES = new LinkedList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add(LOGIN_NAME_PROPERTY);
			add(NAME_PROPERTY);
			add(LAST_NAME_PROPERTY);
			add(COUNTRY_ID_PROPERTY);
			add(GENDER_PROPERTY);
			add(ACTIVE_PROPERTY);
			add(ROLE_IDS_PROPERTY);
			add(IBean.ID_PROPERTY);
			add(IBean.VERSION_PROPERTY);
		}
	};

	@NotNull
	@Size(min = 2, max = 50)
	@PropertyValidator(PersonNameUppercaseValidator.class)
	String getName();

	void setName(String name);

	@NotNull
	@Size(min = 2, max = 50)
	String getLastname();

	void setLastname(String name);

	@NotNull
	@Size(min = 2, max = 20)
	String getLoginName();

	void setLoginName(String loginName);

	@NotNull
	@Size(min = 1, max = 1)
	String getGender();

	void setGender(String gender);

	@NotNull
	Boolean getActive();

	void setActive(Boolean active);

	Long getCountryId();

	void setCountryId(Long id);

	List<Long> getRoleIds();

	void setRoleIds(List<Long> roleIds);

}
