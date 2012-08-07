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
import org.jowidgets.cap.service.api.entity.IBeanEntityBluePrint;
import org.jowidgets.cap.service.api.entity.IBeanEntityLinkBluePrint;
import org.jowidgets.cap.service.neo4j.api.Neo4JServiceToolkit;
import org.jowidgets.cap.service.tools.entity.BeanEntityServiceBuilderWrapper;
import org.jowidgets.samples.neo4j.sample1.app.common.bean.IPersonRoleLink;
import org.jowidgets.samples.neo4j.sample1.app.common.entity.EntityIds;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.Authorization;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.Person;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.PersonRoleLink;
import org.jowidgets.samples.neo4j.sample1.app.service.bean.Role;
import org.jowidgets.samples.neo4j.sample1.app.service.descriptor.AuthorizationDtoDescriptorBuilder;
import org.jowidgets.samples.neo4j.sample1.app.service.descriptor.PersonDtoDescriptorBuilder;
import org.jowidgets.samples.neo4j.sample1.app.service.descriptor.RoleDtoDescriptorBuilder;
import org.jowidgets.service.api.IServiceRegistry;

public final class Sample1EntityServiceBuilder extends BeanEntityServiceBuilderWrapper {

	public Sample1EntityServiceBuilder(final IServiceRegistry registry) {
		super(Neo4JServiceToolkit.serviceFactory(), registry);

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

		//Linked persons of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKED_PERSONS_OF_ROLES).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
		//entityBp.setReaderService(createPersonsOfRolesReader(true));
		addPersonLinkDescriptors(entityBp);

		//Linkable persons of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_PERSONS_OF_ROLES).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
		//entityBp.setReaderService(createPersonsOfRolesReader(false));

		//Linked roles of persons
		entityBp = addEntity().setEntityId(EntityIds.LINKED_ROLES_OF_PERSONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		//entityBp.setReaderService(createRolesOfPersonsReader(true));
		entityBp.setDeleterService((IDeleterService) null);
		addRoleLinkDescriptors(entityBp);

		//Linkable roles of persons
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_ROLES_OF_PERSONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		//entityBp.setReaderService(createRolesOfPersonsReader(false));
		entityBp.setDeleterService((IDeleterService) null);

	}

	private void addPersonLinkDescriptors(final IBeanEntityBluePrint entityBp) {
		addPersonRoleLinkDescriptor(entityBp);
	}

	private void addRoleLinkDescriptors(final IBeanEntityBluePrint entityBp) {
		addRolePersonLinkDescriptor(entityBp);
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

	//	private IReaderService<Void> createPersonsOfRolesReader(final boolean linked) {
	//		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Person.class);
	//		queryBuilder.setParentPropertyPath(linked, "personRoleLinks", "role");
	//		if (!linked) {
	//			final IFilter filter = ArithmeticFilter.create(IPerson.ACTIVE_PROPERTY, ArithmeticOperator.EQUAL, Boolean.TRUE);
	//			queryBuilder.addFilter(filter);
	//		}
	//		return getServiceFactory().readerService(Person.class, queryBuilder.build(), IPerson.ALL_PROPERTIES);
	//	}
	//
	//	private IReaderService<Void> createRolesOfPersonsReader(final boolean linked) {
	//		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Role.class);
	//		queryBuilder.setParentPropertyPath(linked, "personRoleLinks", "person");
	//		return getServiceFactory().readerService(Role.class, queryBuilder.build(), IRole.ALL_PROPERTIES);
	//	}

}