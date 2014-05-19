/*
 * Copyright (c) 2013, grossmann
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

import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.ui.api.CapUiToolkit;
import org.jowidgets.cap.ui.api.bean.IBeanProxy;
import org.jowidgets.cap.ui.api.bean.IBeanSelection;
import org.jowidgets.cap.ui.api.bean.IBeanSelectionEvent;
import org.jowidgets.cap.ui.api.bean.IBeanSelectionListener;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.widgets.IBeanTable;
import org.jowidgets.cap.ui.api.widgets.IBeanTableBluePrint;
import org.jowidgets.cap.ui.api.widgets.ICapApiBluePrintFactory;
import org.jowidgets.cap.ui.tools.model.BeanListModelListenerAdapter;
import org.jowidgets.tools.layout.MigLayoutFactory;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.tools.AbstractView;

public final class RadioStationTableView extends AbstractView {

	public static final String ID = RadioStationTableView.class.getName();
	public static final String DEFAULT_LABEL = "Radio Stations";

	public RadioStationTableView(
		final IViewContext context,
		final IBeanTableModel<IBeanDto> parentModel,
		final IBeanTableModel<IBeanDto> model) {
		final IContainer container = context.getContainer();
		container.setLayout(MigLayoutFactory.growingInnerCellLayout());

		final ICapApiBluePrintFactory cbpf = CapUiToolkit.bluePrintFactory();
		final IBeanTableBluePrint<IBeanDto> beanTableBp = cbpf.beanTable(model);
		beanTableBp.setDefaultCopyAction(true);
		beanTableBp.setDefaultPasteAction(true);
		beanTableBp.setEditable(true);
		final IBeanTable<IBeanDto> table = container.add(beanTableBp, MigLayoutFactory.GROWING_CELL_CONSTRAINTS);

		parentModel.addBeanSelectionListener(new IBeanSelectionListener<IBeanDto>() {
			@Override
			public void selectionChanged(final IBeanSelectionEvent<IBeanDto> event) {
				table.setEnabled(hasPersitentBean(event));
			}
		});

		parentModel.addBeanListModelListener(new BeanListModelListenerAdapter<IBeanDto>() {
			@Override
			public void beansChanged() {
				table.setEnabled(hasPersitentBean(parentModel.getBeanSelection()));
			}
		});

		table.setEnabled(hasPersitentBean(parentModel.getBeanSelection()));
	}

	private boolean hasPersitentBean(final IBeanSelection<IBeanDto> event) {
		final IBeanProxy<IBeanDto> bean = event.getFirstSelected();
		if (bean == null || bean.isDummy() || bean.isTransient()) {
			return false;
		}
		else {
			return true;
		}
	}

}
