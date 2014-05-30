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

package org.jowidgets.samples.fatclient.sample3.tags.component;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Shell;
import org.jowidgets.addons.icons.silkicons.SilkIcons;
import org.jowidgets.api.model.item.IToolBarModel;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.ui.api.CapUiToolkit;
import org.jowidgets.cap.ui.api.bean.IBeanProxy;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.widgets.IBeanTable;
import org.jowidgets.cap.ui.api.widgets.IBeanTableBluePrint;
import org.jowidgets.cap.ui.api.widgets.ICapApiBluePrintFactory;
import org.jowidgets.cap.ui.api.workbench.CapWorkbenchActionsProvider;
import org.jowidgets.common.color.ColorValue;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.MouseButton;
import org.jowidgets.common.widgets.controller.ITableCellMouseEvent;
import org.jowidgets.samples.fatclient.sample3.tags.bean.Tag;
import org.jowidgets.tools.controller.TableCellAdapter;
import org.jowidgets.tools.layout.MigLayoutFactory;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.tools.AbstractView;

public final class TagTableView extends AbstractView {

	public static final String ID = TagTableView.class.getName();
	public static final String DEFAULT_LABEL = "Tags";
	public static final IImageConstant DEFAULT_ICON = SilkIcons.TAG_BLUE;

	public TagTableView(final IViewContext context, final IBeanTableModel<IBeanDto> model) {
		final IContainer container = context.getContainer();
		container.setLayout(MigLayoutFactory.growingInnerCellLayout());

		final ICapApiBluePrintFactory cbpf = CapUiToolkit.bluePrintFactory();
		final IBeanTableBluePrint<IBeanDto> beanTableBp = cbpf.beanTable(model);
		beanTableBp.setDefaultCopyAction(true);
		beanTableBp.setDefaultPasteAction(true);
		beanTableBp.setDefaultEditAction(false);
		beanTableBp.setEditable(true);

		final IBeanTable<IBeanDto> table = container.add(beanTableBp, MigLayoutFactory.GROWING_CELL_CONSTRAINTS);
		table.addTableCellListener(new TableCellAdapter() {

			@Override
			public void mousePressed(final ITableCellMouseEvent event) {

				final int row = event.getRowIndex();
				final int column = event.getColumnIndex();

				final IBeanProxy<IBeanDto> bean = table.getModel().getBean(row);
				if (event.getMouseButton() != MouseButton.LEFT
					|| column == 0
					|| bean == null
					|| bean.isDummy()
					|| bean.isLastRowDummy()) {
					return;
				}

				if (column == 2) {//background
					final IColorConstant color = (IColorConstant) bean.getValue(Tag.BACKGROUND_PROPERTY);
					bean.setValue(Tag.BACKGROUND_PROPERTY, getNewColor(color));
				}
				else if (column == 3) {//foreground
					final IColorConstant color = (IColorConstant) bean.getValue(Tag.FOREGROUND_PROPERTY);
					bean.setValue(Tag.FOREGROUND_PROPERTY, getNewColor(color));
				}

			}

		});

		final IToolBarModel toolBar = context.getToolBar();

		toolBar.addAction(table.getDefaultDeleterAction());
		toolBar.addAction(CapWorkbenchActionsProvider.undoAction());
		toolBar.addAction(CapWorkbenchActionsProvider.saveAction());
	}

	private IColorConstant getNewColor(final IColorConstant color) {
		final ColorDialog dlg = new ColorDialog((Shell) Toolkit.getActiveWindow().getUiReference());

		if (color != null) {
			final ColorValue value = color.getDefaultValue();
			dlg.setRGB(new RGB(value.getRed(), value.getGreen(), value.getBlue()));
		}

		dlg.setText("Choose a Color");

		final RGB rgb = dlg.open();
		if (rgb != null) {
			return new ColorValue(rgb.red, rgb.green, rgb.blue);

		}
		else {
			return color;
		}
	}
}
