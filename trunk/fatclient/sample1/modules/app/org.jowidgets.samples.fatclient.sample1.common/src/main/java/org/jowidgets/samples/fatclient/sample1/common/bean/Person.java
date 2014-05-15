/*
 * Copyright (c) 2014, grossmann
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

package org.jowidgets.samples.fatclient.sample1.common.bean;

import java.util.Date;
import java.util.List;

public final class Person {

	public static final String NAME_PROPERTY = "name";
	public static final String DAY_OF_BIRTH_PROPERTY = "dayOfBirth";
	public static final String GENDER_PROPERTY = "gender";
	public static final String QUOTA_PROPERTY = "quota";
	public static final String ROLES_PROPERTY = "roles";

	private String name;
	private Date dayOfBirth;
	private Gender gender;
	private ByteValue quota;
	private List<Role> roles;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Date getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(final Date dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	public ByteValue getQuota() {
		return quota;
	}

	public void setQuota(final ByteValue quota) {
		this.quota = quota;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(final List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Person [name="
			+ name
			+ ", dayOfBirth="
			+ dayOfBirth
			+ ", gender="
			+ gender
			+ ", quota="
			+ quota
			+ ", roles="
			+ roles
			+ "]";
	}

}
