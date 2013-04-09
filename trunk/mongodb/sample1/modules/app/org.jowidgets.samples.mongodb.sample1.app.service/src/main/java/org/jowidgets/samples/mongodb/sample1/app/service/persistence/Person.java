/*
 * Copyright (c) 2013, grossmann
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

package org.jowidgets.samples.mongodb.sample1.app.service.persistence;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jowidgets.samples.mongodb.sample1.app.common.bean.IPerson;
import org.jowidgets.samples.mongodb.sample1.app.common.dto.Gender;
import org.jowidgets.util.EmptyCheck;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class Person extends GenericBean implements IPerson {

	public static final String PHONE_NUMBERS_PROPERTY = "phoneNumbers";

	private static final long serialVersionUID = -872265010584978183L;

	public Person() {
		super(IPerson.BEAN_TYPE_ID);
	}

	@Override
	public String getName() {
		return getString(NAME_PROPERTY);
	}

	@Override
	public void setName(final String name) {
		put(NAME_PROPERTY, name);
	}

	@Override
	public Date getDateOfBirth() {
		return getDate(DATE_OF_BIRTH_PROPERTY);
	}

	@Override
	public void setDateOfBirth(final Date dateOfBirth) {
		put(DATE_OF_BIRTH_PROPERTY, dateOfBirth);
	}

	@Override
	public Gender getGender() {
		return getEnum(Gender.class, GENDER_PROPERTY);
	}

	@Override
	public void setGender(final Gender gender) {
		putEnum(GENDER_PROPERTY, gender);
	}

	public void setPhoneNumbers(final Collection<? extends PhoneNumber> numbers) {
		final BasicDBList list = new BasicDBList();
		if (!EmptyCheck.isEmpty(numbers)) {
			list.addAll(numbers);
		}
		put(PHONE_NUMBERS_PROPERTY, list);
	}

	public List<PhoneNumber> getPhoneNumbers() {
		@SuppressWarnings("unchecked")
		final Iterable<DBObject> phoneNumbers = (Iterable<DBObject>) get(PHONE_NUMBERS_PROPERTY);
		if (phoneNumbers != null) {
			final List<PhoneNumber> result = new LinkedList<PhoneNumber>();
			for (final DBObject phoneNumber : phoneNumbers) {
				result.add(new PhoneNumber(phoneNumber));
			}
			return Collections.unmodifiableList(result);
		}
		else {
			return Collections.emptyList();
		}
	}

	@Override
	public String toString() {
		return "Person [name=" + getName() + ", dateOfBirth=" + getDateOfBirth() + ", gender=" + getGender() + "]";
	}

}
