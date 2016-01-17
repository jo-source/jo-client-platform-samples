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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.jowidgets.cap.service.jpa.api.ddl.UpperIndex;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericPropertyValue;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Bean;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Person;

@Entity
@Table
public class PersonGenericPropertyValue extends Bean implements IGenericPropertyValue {

	@Index(name = "IGPV_PROPERTYNAME")
	@Column(nullable = false)
	private String propertyName;

	@Basic
	@UpperIndex(name = "IGPV_VALUE_INDEX")
	@Column(nullable = false)
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name = "IGPV_Person_ID_INDEX")
	@JoinColumn(name = "PERSON_ID", nullable = false)
	@ForeignKey(name = "FK_PGPV_PERSON_ID")
	private Person person;

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(final String propValue) {
		this.value = propValue;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(final Person person) {
		this.person = person;
	}

}
