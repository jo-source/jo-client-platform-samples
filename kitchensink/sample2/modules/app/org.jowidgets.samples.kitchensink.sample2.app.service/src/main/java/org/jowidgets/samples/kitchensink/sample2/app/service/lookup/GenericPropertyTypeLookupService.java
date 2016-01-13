package org.jowidgets.samples.kitchensink.sample2.app.service.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jowidgets.cap.common.api.CapCommonToolkit;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.common.api.lookup.ILookUpEntry;
import org.jowidgets.cap.common.api.lookup.ILookUpEntryBuilder;
import org.jowidgets.cap.common.api.lookup.ILookUpToolkit;
import org.jowidgets.cap.service.api.adapter.ISyncLookUpService;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.GenericPropertyType;

public class GenericPropertyTypeLookupService implements ISyncLookUpService {

	@Override
	public List<ILookUpEntry> readValues(final IExecutionCallback executionCallback) {

		final ILookUpToolkit lookUpToolkit = CapCommonToolkit.lookUpToolkit();

		final List<ILookUpEntry> result = new ArrayList<ILookUpEntry>();
		result.add(lookUpToolkit.lookUpEntry(null, ""));
		for (final GenericPropertyType genericPropertyType : GenericPropertyType.values()) {
			final ILookUpEntryBuilder entryBuilder = lookUpToolkit.lookUpEntryBuilder();
			entryBuilder.setKey(genericPropertyType);
			entryBuilder.setValue(genericPropertyType.getMessage());
			entryBuilder.setValid(true);
			result.add(entryBuilder.build());
		}

		return Collections.unmodifiableList(result);
	}

}
