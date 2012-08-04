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
import java.util.Map;

import org.jowidgets.cap.service.neo4j.api.GraphDBConfig;
import org.jowidgets.cap.service.neo4j.tools.EmbeddedGraphDbConfig;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public final class DataReader {

	private DataReader() {}

	public static void main(final String[] args) {
		GraphDBConfig.initialize(new EmbeddedGraphDbConfig(GraphDbPath.PATH));
		new DataReader().printPersons();
	}

	private void printPersons() {
		final GraphDatabaseService graphDb = GraphDBConfig.getGraphDbService();
		final Node referenceNode = graphDb.getReferenceNode();
		for (final Relationship personsRel : referenceNode.getRelationships(EntityRelationTypes.PERSONS, Direction.OUTGOING)) {
			final Node personsNode = personsRel.getEndNode();
			for (final Relationship personRel : personsNode.getRelationships(EntityRelationTypes.PERSON, Direction.OUTGOING)) {
				printPerson(personRel.getEndNode());
			}
		}

		//CHECKSTYLE:OFF
		System.out.println("with cypher");
		//CHECKSTYLE:OFF

		final ExecutionEngine engine = new ExecutionEngine(GraphDBConfig.getGraphDbService());
		final String query = "start n=node(0) MATCH n-[:"
			+ EntityRelationTypes.PERSONS
			+ "]->hyperEdge-[:"
			+ EntityRelationTypes.PERSON
			+ "]->person return person";
		final ExecutionResult result = engine.execute(query);

		for (final Map<String, Object> map : result) {
			final Object object = map.get("person");
			printPerson((Node) object);
		}

	}

	private void printPerson(final Node node) {
		//CHECKSTYLE:OFF
		System.out.println("ID: " + node.getProperty(Person.ID));
		System.out.println("Login: " + node.getProperty(Person.LOGIN_NAME_PROPERTY));
		System.out.println("Name: " + node.getProperty(Person.NAME_PROPERTY));
		System.out.println("CREATION DATE: " + new Date((Long) node.getProperty(Person.CREATION_DATE_PROPERTY)));
		System.out.println("ACTIVE_PROPERTY: " + node.getProperty(Person.ACTIVE_PROPERTY));
		//CHECKSTYLE:ON
	}
}
