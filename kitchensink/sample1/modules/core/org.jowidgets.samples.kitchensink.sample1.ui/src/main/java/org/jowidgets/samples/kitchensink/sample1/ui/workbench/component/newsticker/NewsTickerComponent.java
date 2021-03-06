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

package org.jowidgets.samples.kitchensink.sample1.ui.workbench.component.newsticker;

import org.jowidgets.cap.ui.api.CapUiToolkit;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModelBuilder;
import org.jowidgets.cap.ui.api.workbench.CapWorkbenchActionsProvider;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.samples.kitchensink.sample1.common.entity.IMatch;
import org.jowidgets.samples.kitchensink.sample1.common.service.reader.ReaderServices;
import org.jowidgets.samples.kitchensink.sample1.ui.attribute.MatchAttributesFactory;
import org.jowidgets.samples.kitchensink.sample1.ui.workbench.component.newsticker.view.NewsTableView;
import org.jowidgets.workbench.api.IComponent;
import org.jowidgets.workbench.api.IComponentContext;
import org.jowidgets.workbench.api.IView;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.toolkit.api.IComponentNodeModel;
import org.jowidgets.workbench.tools.AbstractComponent;

public class NewsTickerComponent extends AbstractComponent implements IComponent {

	private final IBeanTableModel<IMatch> newsTableModel;

	public NewsTickerComponent(final IComponentNodeModel componentNodeModel, final IComponentContext componentContext) {
		componentContext.setLayout(new NewsComponentDefaultLayout().getLayout());
		this.newsTableModel = createNewsTableModel();
	}

	@Override
	public IView createView(final String viewId, final IViewContext context) {
		if (NewsTableView.ID.equals(viewId)) {
			return new NewsTableView(context, newsTableModel);
		}
		else {
			throw new IllegalArgumentException("View id '" + viewId + "' is not known.");
		}
	}

	@Override
	public void onDispose() {}

	@Override
	public void onActivation() {
		CapWorkbenchActionsProvider.loadAction().addDataModel(newsTableModel);
		CapWorkbenchActionsProvider.saveAction().addDataModel(newsTableModel);
		CapWorkbenchActionsProvider.undoAction().addDataModel(newsTableModel);
		CapWorkbenchActionsProvider.cancelAction().addDataModel(newsTableModel);
	}

	@Override
	public void onDeactivation(final IVetoable vetoable) {
		CapWorkbenchActionsProvider.loadAction().removeDataModel(newsTableModel);
		CapWorkbenchActionsProvider.saveAction().removeDataModel(newsTableModel);
		CapWorkbenchActionsProvider.undoAction().removeDataModel(newsTableModel);
		CapWorkbenchActionsProvider.cancelAction().removeDataModel(newsTableModel);
	}

	private IBeanTableModel<IMatch> createNewsTableModel() {
		final IBeanTableModelBuilder<IMatch> builder = CapUiToolkit.beanTableModelBuilder(IMatch.class);

		builder.setAddUpdatesSorted(false);
		builder.setPagingEnabled(false);
		builder.setPageSize(100);

		builder.setAttributes(new MatchAttributesFactory().tableAttributes());
		builder.setReaderService(ReaderServices.NEWS_TICKER);
		return builder.build();
	}
}
