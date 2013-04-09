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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

//CHECKSTYLE:OFF
public final class DataGenerator {

	private static final int MAX_DATA = 20000000;

	private static final String DB_NAME = "sample1Db";

	private static final String PERSON_COLLECTION = "PERSON";

	private static final String NAME_PROPERTY = "NAME";
	private static final String LAST_NAME_PROPERTY = "LAST_NAME";
	private static final String COMMENT_PROPERTY = "COMMENT";

	private final DB db;

	private DataGenerator() throws Exception {
		final MongoClient client = new MongoClient();
		this.db = client.getDB(DB_NAME);
	}

	private void dropData() {
		System.out.println("BEFORE DROP");
		db.dropDatabase();
		System.out.println("AFTER DROP");
	}

	private void createIndex() {
		System.out.println("BEFORE CREATE INDEX");
		final BasicDBObject index = new BasicDBObject();
		index.append("_id", 1);
		index.append(NAME_PROPERTY, 1);
		index.append(LAST_NAME_PROPERTY, 1);
		index.append(COMMENT_PROPERTY, 1);
		db.getCollection(PERSON_COLLECTION).createIndex(index);
		System.out.println("AFTER CREATE INDEX");
	}

	private void generateData() {

		final DBCollection persons = db.getCollection(PERSON_COLLECTION);

		for (int i = 0; i < MAX_DATA; i++) {
			final BasicDBObject person = new BasicDBObject();
			person.append(NAME_PROPERTY, "Name" + i);
			person.append(LAST_NAME_PROPERTY, "LastName" + (MAX_DATA - i));
			person.append(COMMENT_PROPERTY, "Comment" + i);
			persons.insert(person);
			if (i % 1000 == 0) {
				System.out.println("Inserted: " + i);
			}
		}

	}

	private void findData() {
		System.out.println("BEFORE FIND");

		final DBCollection persons = db.getCollection(PERSON_COLLECTION);

		final DBCursor cursor = persons.find(new BasicDBObject());

		System.out.println("BEFORE SKIP");

		cursor.sort(new BasicDBObject(NAME_PROPERTY, -1));

		final long timeBefore = System.currentTimeMillis();

		cursor.skip((MAX_DATA) - 2000);
		cursor.limit(2000);

		try {
			while (cursor.hasNext()) {
				final DBObject person = cursor.next();
				final Object subject = person.get(NAME_PROPERTY);
				final Object from = person.get(LAST_NAME_PROPERTY);
				final Object to = person.get(COMMENT_PROPERTY);
				System.out.println(from + " / " + to + "/" + subject + " / ");
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
		final DataGenerator dataGenerator = new DataGenerator();
		dataGenerator.dropData();
		dataGenerator.createIndex();
		dataGenerator.generateData();
		dataGenerator.findData();
	}
}
