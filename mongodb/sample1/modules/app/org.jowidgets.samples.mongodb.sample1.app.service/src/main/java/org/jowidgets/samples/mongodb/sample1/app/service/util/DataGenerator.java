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

	private static final int MAX_DATA = 30000000;

	private static final String DB_NAME = "sample1Db";

	private static final String EMAIL_COLLECTION = "EMAIL";

	private static final String SUBJECT_PROPERTY = "SUBJECT";
	private static final String FROM_ADDRESS_PROPERTY = "FROM_ADDRESS";
	private static final String TO_ADDRESS_PROPERTY = "TO_ADDRESS";

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
		index.append(SUBJECT_PROPERTY, 1).append(FROM_ADDRESS_PROPERTY, 1).append(TO_ADDRESS_PROPERTY, 1);
		db.getCollection(EMAIL_COLLECTION).createIndex(index);
		System.out.println("AFTER CREATE INDEX");
	}

	private void generateData() {

		final DBCollection emails = db.getCollection(EMAIL_COLLECTION);

		for (int i = 0; i < MAX_DATA; i++) {
			final BasicDBObject email = new BasicDBObject();
			email.append(SUBJECT_PROPERTY, "Testmail" + i);
			email.append(FROM_ADDRESS_PROPERTY, "herr.grossmann" + (MAX_DATA - i) + "@gmx.de");
			email.append(TO_ADDRESS_PROPERTY, "one.million" + i + "@dollar.com");
			emails.insert(email);
			if (i % 1000 == 0) {
				System.out.println("Inserted: " + i);
			}
		}

	}

	private void findData() {
		System.out.println("BEFORE FIND");

		final DBCollection emails = db.getCollection(EMAIL_COLLECTION);

		final DBCursor cursor = emails.find();

		System.out.println("BEFORE SKIP");

		cursor.sort(new BasicDBObject(SUBJECT_PROPERTY, -1));

		final long timeBefore = System.currentTimeMillis();

		cursor.skip((MAX_DATA / 3) - 20);
		cursor.limit(2000);

		try {
			while (cursor.hasNext()) {
				final DBObject email = cursor.next();
				final Object subject = email.get(SUBJECT_PROPERTY);
				final Object from = email.get(FROM_ADDRESS_PROPERTY);
				final Object to = email.get(TO_ADDRESS_PROPERTY);
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
