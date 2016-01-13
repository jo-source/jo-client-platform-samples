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

public class GenericPropertyLoader {

	private static Map<GenericPropertyEntity, List<GenericProperty>> GENERIC_PROPERTIES = new HashMap<GenericPropertyEntity, List<GenericProperty>>();

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
