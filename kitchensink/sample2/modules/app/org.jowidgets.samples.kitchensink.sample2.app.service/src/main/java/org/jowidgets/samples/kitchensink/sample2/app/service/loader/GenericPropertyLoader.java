/*
 * Copyright (c) 2016, bentele
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
package org.jowidgets.samples.kitchensink.sample2.app.service.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jowidgets.cap.service.jpa.api.EntityManagerFactoryProvider;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.GenericPropertyEntity;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericProperty;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.genericproperties.GenericProperty;

//TODO MG this must be reviewed
public final class GenericPropertyLoader {

	private static final Map<GenericPropertyEntity, List<GenericProperty>> GENERIC_PROPERTIES = new HashMap<GenericPropertyEntity, List<GenericProperty>>();

	private GenericPropertyLoader() {}

	public static List<GenericProperty> getGenericPropertyList(final GenericPropertyEntity entity) {
		if (GENERIC_PROPERTIES.get(entity) == null) {
			EntityManager entityManager = null;

			try {
				entityManager = EntityManagerFactoryProvider.get("sample2PersistenceUnit").createEntityManager();
				final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				final CriteriaQuery<GenericProperty> criteriaQuery = criteriaBuilder.createQuery(GenericProperty.class);
				final Root<GenericProperty> root = criteriaQuery.from(GenericProperty.class);
				criteriaQuery.where(criteriaBuilder.equal(root.get(IGenericProperty.ENTITY_PROPERTY), entity));
				criteriaQuery.orderBy(
						criteriaBuilder.asc(root.get(IGenericProperty.SORT_INDEX_PROPERTY)),
						criteriaBuilder.asc(root.get(IGenericProperty.LABEL_PROPERTY)));

				final List<GenericProperty> resultList = entityManager.createQuery(criteriaQuery).getResultList();
				GENERIC_PROPERTIES.put(entity, resultList);
			}
			finally {
				if (entityManager != null) {
					entityManager.close();
				}
			}
		}
		return GENERIC_PROPERTIES.get(entity);
	}

	public static GenericProperty getGenericProperty(final GenericPropertyEntity entity, final String propertyName) {
		final List<GenericProperty> genericProperties = getGenericPropertyList(entity);
		for (final GenericProperty genericProperty : genericProperties) {
			if (genericProperty.getPropertyName().equals(propertyName)) {
				return genericProperty;
			}
		}
		return null;
	}

}
