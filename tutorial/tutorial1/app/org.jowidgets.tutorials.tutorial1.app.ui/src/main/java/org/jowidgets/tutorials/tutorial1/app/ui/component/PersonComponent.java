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

package org.jowidgets.tutorials.tutorial1.app.ui.component;

import org.jowidgets.cap.ui.api.table.BeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModelBuilder;
import org.jowidgets.cap.ui.api.workbench.CapWorkbenchActionsProvider;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.tutorials.tutorial1.app.common.bean.IPerson;
import org.jowidgets.tutorials.tutorial1.app.common.service.creator.CreatorServices;
import org.jowidgets.tutorials.tutorial1.app.common.service.deleter.DeleterServices;
import org.jowidgets.tutorials.tutorial1.app.common.service.reader.ReaderServices;
import org.jowidgets.tutorials.tutorial1.app.common.service.updater.UpdaterServices;
import org.jowidgets.tutorials.tutorial1.app.ui.attribute.PersonAttributesFactory;
import org.jowidgets.workbench.api.IComponentContext;
import org.jowidgets.workbench.api.IView;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.tools.AbstractComponent;

public final class PersonComponent extends AbstractComponent {

	private final IBeanTableModel<IPerson> personModel;

	public PersonComponent(final IComponentContext componentContext) {
		componentContext.setLayout(PersonComponentLayoutFactory.create());
		this.personModel = createPersonModel();

		personModel.load();
	}

	private IBeanTableModel<IPerson> createPersonModel() {
		final IBeanTableModelBuilder<IPerson> builder = BeanTableModel.builder(IPerson.class);
		builder.setAttributes(PersonAttributesFactory.create());
		builder.setReaderService(ReaderServices.ALL_PERSONS);
		builder.setCreatorService(CreatorServices.CREATE_PERSON);
		builder.setUpdaterService(UpdaterServices.UPDATE_PERSON);
		builder.setDeleterService(DeleterServices.DELETE_PERSON);
		return builder.build();
	}

	@Override
	public IView createView(final String viewId, final IViewContext context) {
		if (PersonTableView.ID.equals(viewId)) {
			return new PersonTableView(context, personModel);
		}
		else if (PersonDetailView.ID.equals(viewId)) {
			return new PersonDetailView(context, personModel);
		}
		else {
			throw new IllegalArgumentException("View id '" + viewId + "' is not known.");
		}
	}

	@Override
	public void onActivation() {
		CapWorkbenchActionsProvider.loadAction().addDataModel(personModel);
		CapWorkbenchActionsProvider.saveAction().addDataModel(personModel);
		CapWorkbenchActionsProvider.undoAction().addDataModel(personModel);
		CapWorkbenchActionsProvider.cancelAction().addDataModel(personModel);
	}

	@Override
	public void onDeactivation(final IVetoable vetoable) {
		CapWorkbenchActionsProvider.loadAction().removeDataModel(personModel);
		CapWorkbenchActionsProvider.saveAction().removeDataModel(personModel);
		CapWorkbenchActionsProvider.undoAction().removeDataModel(personModel);
		CapWorkbenchActionsProvider.cancelAction().removeDataModel(personModel);
	}

}
