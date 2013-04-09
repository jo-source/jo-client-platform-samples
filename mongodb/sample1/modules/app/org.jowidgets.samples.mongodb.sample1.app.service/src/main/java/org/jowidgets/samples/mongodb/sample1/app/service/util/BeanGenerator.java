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

package org.jowidgets.samples.mongodb.sample1.app.service.util;

import java.util.Date;

import org.jowidgets.samples.mongodb.sample1.app.common.bean.IGenericBean;
import org.jowidgets.samples.mongodb.sample1.app.common.bean.IPerson;
import org.jowidgets.samples.mongodb.sample1.app.common.bean.IRole;
import org.jowidgets.samples.mongodb.sample1.app.common.dto.Gender;
import org.jowidgets.samples.mongodb.sample1.app.service.mapper.MappingConstants;
import org.jowidgets.samples.mongodb.sample1.app.service.persistence.Person;
import org.jowidgets.samples.mongodb.sample1.app.service.persistence.Role;
import org.jowidgets.samples.mongodb.sample1.mongodb.api.BeanTypeIdMapperProvider;
import org.jowidgets.samples.mongodb.sample1.mongodb.api.MongoDBProvider;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

//CHECKSTYLE:OFF
public final class BeanGenerator {

	private static final int MAX_DATA = 200;

	private final DB db;

	private BeanGenerator() throws Exception {
		this.db = MongoDBProvider.get();
	}

	private void dropData() {
		System.out.println("BEFORE DROP");
		db.dropDatabase();
		System.out.println("AFTER DROP");
	}

	private void createIndex() {
		System.out.println("BEFORE CREATE INDEX");
		final DBCollection collection = db.getCollection(MappingConstants.COLLECTION_NAME);
		collection.ensureIndex(new BasicDBObject(IGenericBean.BEAN_TYPE_ID_TYPE_PROPERTY, 1));

		collection.ensureIndex(new BasicDBObject(IPerson.NAME_PROPERTY, 1));
		collection.ensureIndex(new BasicDBObject(IPerson.DATE_OF_BIRTH_PROPERTY, 1));
		collection.ensureIndex(new BasicDBObject(IPerson.GENDER_PROPERTY, 1));

		collection.ensureIndex(new BasicDBObject(IRole.NAME_PROPERTY, 1));

		System.out.println("AFTER CREATE INDEX");
	}

	private void generateData() {

		final DBCollection documents = db.getCollection(MappingConstants.COLLECTION_NAME);

		for (int i = 0; i < MAX_DATA; i++) {
			final Person person = new Person();

			person.setName("Name" + i);
			person.setDateOfBirth(new Date());
			person.setGender(Gender.MALE);

			documents.insert(person);
			if (i % 1000 == 0) {
				System.out.println("Inserted: " + i);
			}
		}

		for (int i = 0; i < 10; i++) {
			final Role role = new Role();
			role.setName("Role" + i);
			documents.insert(role);
		}

	}

	private void findData() {
		System.out.println("BEFORE FIND");

		final DBCollection persons = BeanTypeIdMapperProvider.getCollection(IPerson.BEAN_TYPE_ID);
		persons.setObjectClass(Person.class);

		final DBCursor cursor = persons.find();

		System.out.println("BEFORE SKIP");

		cursor.sort(new BasicDBObject(IPerson.NAME_PROPERTY, -1));

		final long timeBefore = System.currentTimeMillis();

		//cursor.skip((MAX_DATA / 3) - 20);
		cursor.limit(2000);

		try {
			while (cursor.hasNext()) {
				final Person person = (Person) cursor.next();
				System.out.println(person);
			}
		}
		finally {
			cursor.close();
		}

		final long timeAfter = System.currentTimeMillis();

		final long timeInSeconds = (timeAfter - timeBefore) / 1000;

		System.out.println("Query TIME: " + timeInSeconds);
	}

	public static void main(final String[] args) throws Exception {
		final BeanGenerator dataGenerator = new BeanGenerator();
		dataGenerator.dropData();
		dataGenerator.createIndex();
		dataGenerator.generateData();
		dataGenerator.findData();
	}
}
