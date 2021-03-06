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

package org.jowidgets.samples.fatclient.sample1.common.repository;

import java.util.Arrays;

import org.jowidgets.cap.service.repository.api.ICrudSupportBeanRepository;
import org.jowidgets.cap.service.repository.tools.HashMapCrudRepository;
import org.jowidgets.samples.fatclient.sample1.common.bean.ByteValue;
import org.jowidgets.samples.fatclient.sample1.common.bean.Gender;
import org.jowidgets.samples.fatclient.sample1.common.bean.Person;
import org.jowidgets.samples.fatclient.sample1.common.bean.Role;
import org.jowidgets.unit.tools.units.ByteUnitSet;

public final class PersonRepository {

	public static final ICrudSupportBeanRepository<Person> INSTANCE = createInstance();

	private PersonRepository() {}

	private static ICrudSupportBeanRepository<Person> createInstance() {
		final HashMapCrudRepository<Person> result = new HashMapCrudRepository<Person>(Person.class);

		Person person = new Person();
		person.setName("Ben");
		person.setLastname("Zien");
		person.setGender(Gender.MALE);
		person.setQuota(new ByteValue(100, ByteUnitSet.GB));
		person.setRoles(Arrays.asList(Role.ADMIN, Role.USER, Role.GUEST));
		result.add(person);

		person = new Person();
		person.setName("Ben");
		person.setLastname("Ebelt");
		person.setGender(Gender.MALE);
		person.setQuota(new ByteValue(50, ByteUnitSet.GB));
		person.setRoles(Arrays.asList(Role.MANAGER));
		result.add(person);

		person = new Person();
		person.setName("Klara");
		person.setLastname("Fall");
		person.setGender(Gender.FEMALE);
		person.setQuota(new ByteValue(1, ByteUnitSet.TB));
		result.add(person);

		//		for (int i = 0; i < 5000; i++) {
		//			person = new Person();
		//			person.setName("Name " + i);
		//			person.setLastname("Lastname " + i);
		//			person.setGender(Gender.FEMALE);
		//			person.setQuota(new ByteValue(1, ByteUnit.TB));
		//			result.add(person);
		//		}

		return result;
	};

}
