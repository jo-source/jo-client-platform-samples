package org.jowidgets.samples.kitchensink.sample2.app.service.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jowidgets.cap.common.api.CapCommonToolkit;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.common.api.lookup.ILookUpEntry;
import org.jowidgets.cap.common.api.lookup.ILookUpEntryBuilder;
import org.jowidgets.cap.common.api.lookup.ILookUpToolkit;
import org.jowidgets.cap.service.api.adapter.ISyncLookUpService;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericProperty;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.genericproperties.GenericProperty;
import org.jowidgets.samples.kitchensink.sample2.app.service.entity.EntityManagerProvider;

public class GenericPropertyLookupService implements ISyncLookUpService {

	@Override
	public List<ILookUpEntry> readValues(final IExecutionCallback executionCallback) {

		final ILookUpToolkit lookUpToolkit = CapCommonToolkit.lookUpToolkit();

		final EntityManager entityManager = EntityManagerProvider.get();

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<GenericProperty> criteriaQuery = criteriaBuilder.createQuery(GenericProperty.class);
		final Root<GenericProperty> root = criteriaQuery.from(GenericProperty.class);
		criteriaQuery.orderBy(
				criteriaBuilder.asc(root.get(IGenericProperty.SORT_INDEX_PROPERTY)),
				criteriaBuilder.asc(root.get(IGenericProperty.LABEL_PROPERTY)));

		final List<GenericProperty> resultList = entityManager.createQuery(criteriaQuery).getResultList();

		final List<ILookUpEntry> result = new ArrayList<ILookUpEntry>();
		result.add(lookUpToolkit.lookUpEntry(null, ""));
		for (final GenericProperty genericProperty : resultList) {
			final ILookUpEntryBuilder entryBuilder = lookUpToolkit.lookUpEntryBuilder();
			entryBuilder.setKey(genericProperty.getPropertyName());
			entryBuilder.setValue(genericProperty.getLabel());
			entryBuilder.setDescription(genericProperty.getDescription());
			entryBuilder.setValue(IGenericProperty.TYPE_PROPERTY, genericProperty.getType());
			entryBuilder.setValue(IGenericProperty.ENTITY_PROPERTY, genericProperty.getEntity());
			entryBuilder.setValid(true);
			result.add(entryBuilder.build());
		}

		return Collections.unmodifiableList(result);
	}

}
