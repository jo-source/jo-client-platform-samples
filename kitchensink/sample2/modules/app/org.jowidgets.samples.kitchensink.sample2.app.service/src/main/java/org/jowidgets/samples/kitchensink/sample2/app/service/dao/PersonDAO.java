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

package org.jowidgets.samples.kitchensink.sample2.app.service.dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IPerson;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Person;

public final class PersonDAO {

	private PersonDAO() {}

	public static Person findPersonById(final EntityManager em, final Object id) {
		return em.find(Person.class, id);
	}

	public static boolean hasPersonWithFirstAndLastName(final EntityManager em, final String firstName, final String lastName) {

		final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		final CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
		final Root<Person> root = query.from(Person.class);
		final Path<String> firstNamePath = root.get(IPerson.NAME_PROPERTY);
		final Path<String> lastNamePath = root.get(IPerson.LAST_NAME_PROPERTY);

		final Predicate firstNamePredicate;
		if (firstName != null) {
			firstNamePredicate = criteriaBuilder.like(firstNamePath, firstName);
		}
		else {
			firstNamePredicate = criteriaBuilder.isNull(firstNamePath);
		}

		final Predicate lastNamePredicate;
		if (lastName != null) {
			lastNamePredicate = criteriaBuilder.like(lastNamePath, lastName);
		}
		else {
			lastNamePredicate = criteriaBuilder.isNull(lastNamePath);
		}

		final Predicate conjunction = criteriaBuilder.and(lastNamePredicate, firstNamePredicate);
		query.where(conjunction);

		return em.createQuery(query).getResultList().size() > 0;
	}
}
