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

package org.jowidgets.examples.rcp3.example7.component;

import org.jowidgets.addons.icons.silkicons.SilkIcons;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IInputComposite;
import org.jowidgets.api.widgets.blueprint.IInputCompositeBluePrint;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.examples.rcp3.example7.form.RoleContentCreator;
import org.jowidgets.examples.rcp3.example7.model.BeanTableModel;
import org.jowidgets.examples.rcp3.example7.model.Role;
import org.jowidgets.tools.controller.TableDataModelAdapter;
import org.jowidgets.tools.layout.MigLayoutFactory;
import org.jowidgets.tools.widgets.blueprint.BPF;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.tools.AbstractView;

public final class RoleDetailView extends AbstractView {

	public static final String ID = RoleDetailView.class.getName();
	public static final String DEFAULT_LABEL = "Role form";
	public static final String DEFAULT_TOOLTIP = "Shows the detail form of the role";
	public static final IImageConstant DEFAULT_ICON = SilkIcons.GROUP;

	public RoleDetailView(final IViewContext context, final BeanTableModel<Role> model) {
		final IContainer container = context.getContainer();
		container.setLayout(MigLayoutFactory.growingInnerCellLayout());

		final IInputCompositeBluePrint<Role> inputCompositeBp = BPF.inputComposite(new RoleContentCreator(true));
		final IInputComposite<Role> inputComposite = container.add(inputCompositeBp, MigLayoutFactory.GROWING_CELL_CONSTRAINTS);
		inputComposite.setEditable(false);

		model.addDataModelListener(new TableDataModelAdapter() {
			@Override
			public void selectionChanged() {
				inputComposite.setValue(model.getSelectedBean());
			}

			@Override
			public void dataChanged() {
				inputComposite.setValue(model.getSelectedBean());
			}
		});
	}
}
