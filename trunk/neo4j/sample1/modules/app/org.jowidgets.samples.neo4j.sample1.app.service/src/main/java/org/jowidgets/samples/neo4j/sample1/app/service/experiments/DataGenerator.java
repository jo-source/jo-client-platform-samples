/*
 * Copyright (c) 2012, grossmann
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

package org.jowidgets.samples.neo4j.sample1.app.service.experiments;

import java.util.Date;
import java.util.UUID;

import org.jowidgets.cap.service.neo4j.api.GraphDBConfig;
import org.jowidgets.cap.service.neo4j.tools.EmbeddedGraphDbConfig;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

public final class DataGenerator {

	private DataGenerator() {}

	public static void main(final String[] args) {
		GraphDBConfig.initialize(new EmbeddedGraphDbConfig(GraphDbPath.PATH));

		new DataGenerator().createDataInTransaction();
	}

	private void createDataInTransaction() {
		final GraphDatabaseService graphDb = GraphDBConfig.getGraphDbService();
		final Transaction tx = graphDb.beginTx();
		try {
			createData();
			tx.success();
		}
		finally {
			tx.finish();
		}
	}

	private void createData() {
		final GraphDatabaseService graphDb = GraphDBConfig.getGraphDbService();

		final Node referenceNode = graphDb.getReferenceNode();
		final Node personsReferenceNode = graphDb.createNode();
		referenceNode.createRelationshipTo(personsReferenceNode, EntityRelationTypes.PERSONS);

		final Node person1 = graphDb.createNode();
		person1.setProperty(Person.ID, UUID.randomUUID().toString());
		person1.setProperty(Person.LOGIN_NAME_PROPERTY, "MG");
		person1.setProperty(Person.NAME_PROPERTY, "Michael Grossmann");
		person1.setProperty(Person.CREATION_DATE_PROPERTY, new Date().getTime());
		person1.setProperty(Person.ACTIVE_PROPERTY, Boolean.TRUE);
		personsReferenceNode.createRelationshipTo(person1, EntityRelationTypes.PERSON);

		final Node person2 = graphDb.createNode();
		person2.setProperty(Person.ID, UUID.randomUUID().toString());
		person2.setProperty(Person.LOGIN_NAME_PROPERTY, "FM");
		person2.setProperty(Person.NAME_PROPERTY, "Frank Maier");
		person2.setProperty(Person.CREATION_DATE_PROPERTY, new Date().getTime());
		person2.setProperty(Person.ACTIVE_PROPERTY, Boolean.TRUE);
		personsReferenceNode.createRelationshipTo(person2, EntityRelationTypes.PERSON);

		person1.createRelationshipTo(person2, RelationTypes.KNOWS);
	}

}
