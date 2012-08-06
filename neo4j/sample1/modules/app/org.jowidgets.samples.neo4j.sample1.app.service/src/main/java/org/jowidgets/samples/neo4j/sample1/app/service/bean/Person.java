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

package org.jowidgets.samples.neo4j.sample1.app.service.bean;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jowidgets.cap.service.neo4j.tools.NodeBean;
import org.jowidgets.samples.neo4j.sample1.app.common.bean.IPerson;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class Person extends NodeBean implements IPerson {

	public Person(final Node node) {
		super(node);
	}

	@Override
	public String getLoginName() {
		return getProperty(LOGIN_NAME_PROPERTY);
	}

	@Override
	public void setLoginName(final String loginName) {
		setProperty(LOGIN_NAME_PROPERTY, loginName);
	}

	@Override
	public String getName() {
		return getProperty(NAME_PROPERTY);
	}

	@Override
	public void setName(final String name) {
		setProperty(NAME_PROPERTY, name);
	}

	@Override
	public Boolean getActive() {
		return getProperty(ACTIVE_PROPERTY);
	}

	@Override
	public void setActive(final Boolean active) {
		setProperty(ACTIVE_PROPERTY, active);
	}

	public Set<Role> getRoles() {
		final Set<Role> result = new HashSet<Role>();
		for (final Relationship relation : getNode().getRelationships(Direction.OUTGOING, RelationTypes.PERSON_ROLE_LINKS)) {
			result.add(new Role(relation.getEndNode()));
		}
		return result;
	}

	@Override
	public List<String> getRoleNames() {
		final List<String> result = new LinkedList<String>();
		for (final Relationship relation : getNode().getRelationships(Direction.OUTGOING, RelationTypes.PERSON_ROLE_LINKS)) {
			result.add(new Role(relation.getEndNode()).getName());
		}
		return result;
	}

}
