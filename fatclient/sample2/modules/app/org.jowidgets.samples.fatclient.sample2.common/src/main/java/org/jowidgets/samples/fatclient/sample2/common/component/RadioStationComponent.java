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

package org.jowidgets.samples.fatclient.sample2.common.component;

import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.service.repository.api.BeanRepositoryServiceFactory;
import org.jowidgets.cap.ui.api.model.IDataModel;
import org.jowidgets.cap.ui.api.model.LinkType;
import org.jowidgets.cap.ui.api.table.BeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModelBuilder;
import org.jowidgets.cap.ui.api.workbench.CapWorkbenchActionsProvider;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.samples.fatclient.sample2.common.attribute.RadioStationAttributes;
import org.jowidgets.samples.fatclient.sample2.common.attribute.RadioStationListAttributes;
import org.jowidgets.samples.fatclient.sample2.common.repository.RadioStationListRepository;
import org.jowidgets.samples.fatclient.sample2.common.repository.RadioStationRepository;
import org.jowidgets.workbench.api.IComponentContext;
import org.jowidgets.workbench.api.IView;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.tools.AbstractComponent;

public final class RadioStationComponent extends AbstractComponent {

	private final IBeanTableModel<IBeanDto> stationModel;
	private final IBeanTableModel<IBeanDto> stationListModel;

	public RadioStationComponent(final IComponentContext componentContext) {
		componentContext.setLayout(RadioStationComponentLayoutFactory.create());
		this.stationListModel = createStationListModel();
		stationListModel.load();

		this.stationModel = createStationModel(stationListModel);
		stationModel.load();
	}

	private IBeanTableModel<IBeanDto> createStationListModel() {
		final IBeanTableModelBuilder<IBeanDto> builder = createTableModelBuilder();
		builder.setAttributes(RadioStationListAttributes.INSTANCE);
		builder.setEntityLabelSingular("Radio station list");
		builder.setEntityLabelPlural("Radio station lists");
		builder.setEntityServices(BeanRepositoryServiceFactory.beanServices(RadioStationListRepository.INSTANCE));
		return builder.build();
	}

	private IBeanTableModel<IBeanDto> createStationModel(final IBeanTableModel<IBeanDto> parent) {
		final IBeanTableModelBuilder<IBeanDto> builder = createTableModelBuilder();
		builder.setAttributes(RadioStationAttributes.INSTANCE);
		builder.setEntityLabelSingular("Radio station");
		builder.setEntityLabelPlural("Radio stations");
		builder.setEntityServices(BeanRepositoryServiceFactory.beanServices(RadioStationRepository.INSTANCE));
		builder.setParent(parent, LinkType.SELECTION_FIRST);
		return builder.build();
	}

	private static IBeanTableModelBuilder<IBeanDto> createTableModelBuilder() {
		final IBeanTableModelBuilder<IBeanDto> builder = BeanTableModel.builder(IBeanDto.class);
		builder.setLastBeanEnabled(true);
		builder.setUseLastModificationForDefault(true);
		builder.setMetaAttributes();
		return builder;
	}

	@Override
	public IView createView(final String viewId, final IViewContext context) {
		if (RadioStationListTableView.ID.equals(viewId)) {
			return new RadioStationListTableView(context, stationListModel);
		}
		else if (RadioStationTableView.ID.equals(viewId)) {
			return new RadioStationTableView(context, stationListModel, stationModel);
		}
		else {
			throw new IllegalArgumentException("View id '" + viewId + "' is not known.");
		}
	}

	@Override
	public void onActivation() {
		addDataModel(stationModel);
		addDataModel(stationListModel);
	}

	@Override
	public void onDeactivation(final IVetoable vetoable) {
		removeDataModel(stationModel);
		removeDataModel(stationListModel);
	}

	private void addDataModel(final IDataModel dataModel) {
		CapWorkbenchActionsProvider.loadAction().addDataModel(dataModel);
		CapWorkbenchActionsProvider.saveAction().addDataModel(dataModel);
		CapWorkbenchActionsProvider.undoAction().addDataModel(dataModel);
		CapWorkbenchActionsProvider.cancelAction().addDataModel(dataModel);
	}

	private void removeDataModel(final IDataModel dataModel) {
		CapWorkbenchActionsProvider.loadAction().removeDataModel(dataModel);
		CapWorkbenchActionsProvider.saveAction().removeDataModel(dataModel);
		CapWorkbenchActionsProvider.undoAction().removeDataModel(dataModel);
		CapWorkbenchActionsProvider.cancelAction().removeDataModel(dataModel);
	}

}
