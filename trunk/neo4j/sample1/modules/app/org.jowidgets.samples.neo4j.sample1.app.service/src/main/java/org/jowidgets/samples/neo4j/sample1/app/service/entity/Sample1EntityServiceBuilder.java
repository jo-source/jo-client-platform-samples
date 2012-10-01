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

package org.jowidgets.samples.neo4j.sample1.app.service.entity;

import org.jowidgets.cap.common.api.service.IDeleterService;
import org.jowidgets.cap.common.api.service.IReaderService;
import org.jowidgets.cap.service.api.entity.IBeanEntityBluePrint;
import org.jowidgets.cap.service.api.entity.IBeanEntityLinkBluePrint;
import org.jowidgets.cap.service.neo4j.tools.Neo4JEntityServiceBuilderWrapper;
import org.jowidgets.samples.neo4j.sample1.app.common.bean.IAuthorization;
import org.jowidgets.samples.neo4j.sample1.app.common.bean.IPerson;
import org.jowidgets.samples.neo4j.sample1.app.common.bean.IPersonRoleLink;
import org.jowidgets.samples.neo4j.sample1.app.common.bean.IRole;
import org.jowidgets.samples.neo4j.sample1.app.common.bean.IRoleAuthorizationLink;
import org.jowidgets.samples.neo4j.sample1.app.common.entity.EntityIds;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.Authorization;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.Person;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.PersonRoleLink;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.RelationTypes;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.Role;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.RoleAuthorizationLink;
import org.jowidgets.samples.neo4j.sample1.app.service.descriptor.AuthorizationDtoDescriptorBuilder;
import org.jowidgets.samples.neo4j.sample1.app.service.descriptor.PersonDtoDescriptorBuilder;
import org.jowidgets.samples.neo4j.sample1.app.service.descriptor.RoleDtoDescriptorBuilder;
import org.jowidgets.service.api.IServiceRegistry;
import org.neo4j.graphdb.Direction;

public final class Sample1EntityServiceBuilder extends Neo4JEntityServiceBuilderWrapper {

	public Sample1EntityServiceBuilder(final IServiceRegistry registry) {
		super(registry);

		//IPerson
		IBeanEntityBluePrint entityBp = addEntity().setEntityId(EntityIds.PERSON).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
		addPersonLinkDescriptors(entityBp);

		//IRole
		entityBp = addEntity().setEntityId(EntityIds.ROLE).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		addRoleLinkDescriptors(entityBp);

		//IAuthorization
		entityBp = addEntity().setEntityId(EntityIds.AUTHORIZATION).setBeanType(Authorization.class);
		entityBp.setDtoDescriptor(new AuthorizationDtoDescriptorBuilder());
		addAuthorizationRoleLinkDescriptor(entityBp);

		//Linked persons of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKED_PERSONS_OF_ROLES).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
		entityBp.setReaderService(createPersonsOfRolesReader(true));
		addPersonLinkDescriptors(entityBp);

		//Linkable persons of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_PERSONS_OF_ROLES).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
		entityBp.setReaderService(createPersonsOfRolesReader(false));

		//Linked roles of persons
		entityBp = addEntity().setEntityId(EntityIds.LINKED_ROLES_OF_PERSONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		entityBp.setReaderService(createRolesOfPersonsReader(true));
		entityBp.setDeleterService((IDeleterService) null);
		addRoleLinkDescriptors(entityBp);

		//Linkable roles of persons
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_ROLES_OF_PERSONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		entityBp.setReaderService(createRolesOfPersonsReader(false));
		entityBp.setDeleterService((IDeleterService) null);

		//Linked roles of authorizations
		entityBp = addEntity().setEntityId(EntityIds.LINKED_ROLES_OF_AUTHORIZATIONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		entityBp.setReaderService(createRolesOfAuthorizationsReader(true));
		entityBp.setDeleterService((IDeleterService) null);
		addRoleLinkDescriptors(entityBp);

		//Linkable roles of authorizations
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_ROLES_OF_AUTHORIZATIONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		entityBp.setReaderService(createRolesOfAuthorizationsReader(false));
		entityBp.setDeleterService((IDeleterService) null);

		//Linked authorizations of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKED_AUTHORIZATION_OF_ROLES).setBeanType(Authorization.class);
		entityBp.setDtoDescriptor(new AuthorizationDtoDescriptorBuilder());
		entityBp.setReaderService(createAuthorizationsOfRolesReader(true));
		entityBp.setDeleterService((IDeleterService) null);
		addAuthorizationRoleLinkDescriptor(entityBp);

		//Linkable authorizations of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_AUTHORIZATIONS_OF_ROLES).setBeanType(Authorization.class);
		entityBp.setDtoDescriptor(new AuthorizationDtoDescriptorBuilder());
		entityBp.setReaderService(createAuthorizationsOfRolesReader(false));
		entityBp.setDeleterService((IDeleterService) null);

	}

	private void addPersonLinkDescriptors(final IBeanEntityBluePrint entityBp) {
		addPersonRoleLinkDescriptor(entityBp);
	}

	private void addRoleLinkDescriptors(final IBeanEntityBluePrint entityBp) {
		addRolePersonLinkDescriptor(entityBp);
		addRoleAuthorizationLinkDescriptor(entityBp);
	}

	private void addPersonRoleLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.PERSON_ROLE_LINK);
		bp.setLinkBeanType(PersonRoleLink.class);
		bp.setLinkedEntityId(EntityIds.LINKED_ROLES_OF_PERSONS);
		bp.setLinkableEntityId(EntityIds.LINKABLE_ROLES_OF_PERSONS);
		bp.setSourceProperties(IPersonRoleLink.PERSON_ID_PROPERTY);
		bp.setDestinationProperties(IPersonRoleLink.ROLE_ID_PROPERTY);
	}

	private void addRolePersonLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.PERSON_ROLE_LINK);
		bp.setLinkBeanType(PersonRoleLink.class);
		bp.setLinkedEntityId(EntityIds.LINKED_PERSONS_OF_ROLES);
		bp.setLinkableEntityId(EntityIds.LINKABLE_PERSONS_OF_ROLES);
		bp.setSourceProperties(IPersonRoleLink.ROLE_ID_PROPERTY);
		bp.setDestinationProperties(IPersonRoleLink.PERSON_ID_PROPERTY);
	}

	private void addRoleAuthorizationLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.ROLE_AUTHORIZATION_LINK);
		bp.setLinkBeanType(RoleAuthorizationLink.class);
		bp.setLinkedEntityId(EntityIds.LINKED_AUTHORIZATION_OF_ROLES);
		bp.setLinkableEntityId(EntityIds.LINKABLE_AUTHORIZATIONS_OF_ROLES);
		bp.setSourceProperties(IRoleAuthorizationLink.ROLE_ID_PROPERTY);
		bp.setDestinationProperties(IRoleAuthorizationLink.AUTHORIZATION_ID_PROPERTY);
	}

	private void addAuthorizationRoleLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.ROLE_AUTHORIZATION_LINK);
		bp.setLinkBeanType(RoleAuthorizationLink.class);
		bp.setLinkedEntityId(EntityIds.LINKED_ROLES_OF_AUTHORIZATIONS);
		bp.setLinkableEntityId(EntityIds.LINKABLE_ROLES_OF_AUTHORIZATIONS);
		bp.setSourceProperties(IRoleAuthorizationLink.AUTHORIZATION_ID_PROPERTY);
		bp.setDestinationProperties(IRoleAuthorizationLink.ROLE_ID_PROPERTY);
	}

	private IReaderService<Void> createPersonsOfRolesReader(final boolean related) {
		return getServiceFactory().relatedReaderService(
				Role.class,
				Person.class,
				RelationTypes.PERSON_ROLE,
				Direction.INCOMING,
				related,
				IPerson.ALL_PROPERTIES);
	}

	private IReaderService<Void> createRolesOfPersonsReader(final boolean related) {
		return getServiceFactory().relatedReaderService(
				Person.class,
				Role.class,
				RelationTypes.PERSON_ROLE,
				Direction.OUTGOING,
				related,
				IRole.ALL_PROPERTIES);
	}

	private IReaderService<Void> createRolesOfAuthorizationsReader(final boolean related) {
		return getServiceFactory().relatedReaderService(
				Authorization.class,
				Role.class,
				RelationTypes.ROLE_AUTHORIZATION,
				Direction.INCOMING,
				related,
				IRole.ALL_PROPERTIES);
	}

	private IReaderService<Void> createAuthorizationsOfRolesReader(final boolean related) {
		return getServiceFactory().relatedReaderService(
				Role.class,
				Authorization.class,
				RelationTypes.ROLE_AUTHORIZATION,
				Direction.OUTGOING,
				related,
				IAuthorization.ALL_PROPERTIES);
	}

}
