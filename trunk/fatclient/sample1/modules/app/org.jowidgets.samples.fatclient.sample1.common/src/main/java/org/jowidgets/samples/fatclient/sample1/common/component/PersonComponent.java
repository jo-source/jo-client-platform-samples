/*
 * Copyright (c) 2011, grossmann
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

package org.jowidgets.samples.fatclient.sample1.common.component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jowidgets.cap.common.api.bean.IBeanData;
import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.common.api.execution.IResultCallback;
import org.jowidgets.cap.common.api.filter.IFilter;
import org.jowidgets.cap.common.api.service.ICreatorService;
import org.jowidgets.cap.common.api.service.IDeleterService;
import org.jowidgets.cap.common.api.service.IReaderService;
import org.jowidgets.cap.common.api.service.IRefreshService;
import org.jowidgets.cap.common.api.sort.ISort;
import org.jowidgets.cap.ui.api.table.BeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModelBuilder;
import org.jowidgets.cap.ui.api.workbench.CapWorkbenchActionsProvider;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.samples.fatclient.sample1.common.attribute.PersonAttributes;
import org.jowidgets.workbench.api.IComponentContext;
import org.jowidgets.workbench.api.IView;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.tools.AbstractComponent;

public final class PersonComponent extends AbstractComponent {

	private final IBeanTableModel<IBeanDto> model;

	public PersonComponent(final IComponentContext componentContext) {
		componentContext.setLayout(PersonComponentLayoutFactory.create());
		this.model = createPersonModel();

		model.load();
	}

	private IBeanTableModel<IBeanDto> createPersonModel() {
		final IBeanTableModelBuilder<IBeanDto> builder = BeanTableModel.builder(IBeanDto.class);

		builder.setMetaAttributes();
		builder.setAttributes(PersonAttributes.INSTANCE);

		builder.setReaderService(new IReaderService<Void>() {
			@SuppressWarnings("unchecked")
			@Override
			public void read(
				final IResultCallback<List<IBeanDto>> result,
				final List<? extends IBeanKey> parentBeanKeys,
				final IFilter filter,
				final List<? extends ISort> sorting,
				final int firstRow,
				final int maxRows,
				final Void parameter,
				final IExecutionCallback executionCallback) {
				result.finished(Collections.EMPTY_LIST);
			}

			@Override
			public void count(
				final IResultCallback<Integer> result,
				final List<? extends IBeanKey> parentBeanKeys,
				final IFilter filter,
				final Void parameter,
				final IExecutionCallback executionCallback) {
				result.finished(Integer.valueOf(0));
			}
		});

		builder.setCreatorService(new ICreatorService() {
			@SuppressWarnings("unchecked")
			@Override
			public void create(
				final IResultCallback<List<IBeanDto>> result,
				final Collection<? extends IBeanData> beansData,
				final IExecutionCallback executionCallback) {
				result.finished(Collections.EMPTY_LIST);
			}
		});

		builder.setDeleterService(new IDeleterService() {
			@Override
			public void delete(
				final IResultCallback<Void> result,
				final Collection<? extends IBeanKey> beanKeys,
				final IExecutionCallback executionCallback) {
				result.finished(null);
			}
		});

		builder.setRefreshService(new IRefreshService() {
			@SuppressWarnings("unchecked")
			@Override
			public void refresh(
				final IResultCallback<List<IBeanDto>> result,
				final Collection<? extends IBeanKey> beanKeys,
				final IExecutionCallback executionCallback) {
				result.finished(Collections.EMPTY_LIST);
			}
		});

		return builder.build();
	}

	@Override
	public IView createView(final String viewId, final IViewContext context) {
		if (PersonTableView.ID.equals(viewId)) {
			return new PersonTableView(context, model);
		}
		else if (PersonDetailView.ID.equals(viewId)) {
			return new PersonDetailView(context, model);
		}
		else {
			throw new IllegalArgumentException("View id '" + viewId + "' is not known.");
		}
	}

	@Override
	public void onActivation() {
		CapWorkbenchActionsProvider.loadAction().addDataModel(model);
		CapWorkbenchActionsProvider.saveAction().addDataModel(model);
		CapWorkbenchActionsProvider.undoAction().addDataModel(model);
		CapWorkbenchActionsProvider.cancelAction().addDataModel(model);
	}

	@Override
	public void onDeactivation(final IVetoable vetoable) {
		CapWorkbenchActionsProvider.loadAction().removeDataModel(model);
		CapWorkbenchActionsProvider.saveAction().removeDataModel(model);
		CapWorkbenchActionsProvider.undoAction().removeDataModel(model);
		CapWorkbenchActionsProvider.cancelAction().removeDataModel(model);
	}

}
