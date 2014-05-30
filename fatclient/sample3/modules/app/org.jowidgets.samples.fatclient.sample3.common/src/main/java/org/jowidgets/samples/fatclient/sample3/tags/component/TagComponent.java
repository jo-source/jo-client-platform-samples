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

package org.jowidgets.samples.fatclient.sample3.tags.component;

import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.service.repository.api.BeanRepositoryServiceFactory;
import org.jowidgets.cap.ui.api.model.IProcessStateListener;
import org.jowidgets.cap.ui.api.table.BeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModelBuilder;
import org.jowidgets.cap.ui.api.workbench.CapWorkbenchActionsProvider;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.samples.fatclient.sample3.lookup.ui.LookupClient;
import org.jowidgets.samples.fatclient.sample3.tags.attribute.TagAttributes;
import org.jowidgets.samples.fatclient.sample3.tags.renderer.TagTableCellRenderer;
import org.jowidgets.samples.fatclient.sample3.tags.repository.TagRepository;
import org.jowidgets.workbench.api.IComponentContext;
import org.jowidgets.workbench.api.IView;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.tools.AbstractComponent;

public final class TagComponent extends AbstractComponent {

	private final IBeanTableModel<IBeanDto> model;

	public TagComponent(final IComponentContext componentContext) {
		componentContext.setLayout(TagComponentLayoutFactory.create());
		this.model = createModel();

		model.addProcessStateListener(new IProcessStateListener() {
			@Override
			public void processStateChanged() {
				LookupClient.clearCache();
			}
		});

		model.load();
	}

	private IBeanTableModel<IBeanDto> createModel() {
		final IBeanTableModelBuilder<IBeanDto> builder = BeanTableModel.builder(IBeanDto.class);
		builder.setLastBeanEnabled(true);
		builder.setMetaAttributes();
		builder.setAttributes(TagAttributes.INSTANCE);
		builder.setEntityLabelSingular("Tag");
		builder.setEntityLabelPlural("Tags");
		builder.setAutoSelection(false);
		builder.addCellRenderer(new TagTableCellRenderer());
		builder.setEntityServices(BeanRepositoryServiceFactory.beanServices(TagRepository.INSTANCE));
		return builder.build();
	}

	@Override
	public IView createView(final String viewId, final IViewContext context) {
		if (TagTableView.ID.equals(viewId)) {
			return new TagTableView(context, model);
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
