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

package org.jowidgets.tutorials.tutorial3.app.ui.application;

import org.jowidgets.addons.icons.silkicons.SilkIcons;
import org.jowidgets.cap.ui.api.workbench.CapWorkbenchToolkit;
import org.jowidgets.cap.ui.api.workbench.IEntityComponentNodesFactory;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.tutorials.tutorial3.app.common.entity.EntityIds;
import org.jowidgets.workbench.toolkit.api.IComponentNodeModel;
import org.jowidgets.workbench.toolkit.api.IWorkbenchApplicationModel;
import org.jowidgets.workbench.toolkit.api.IWorkbenchApplicationModelBuilder;
import org.jowidgets.workbench.tools.WorkbenchApplicationModelBuilder;

public final class Tutorial3ApplicationFactory {

	private Tutorial3ApplicationFactory() {}

	public static IWorkbenchApplicationModel create() {
		final IWorkbenchApplicationModelBuilder builder = new WorkbenchApplicationModelBuilder();
		builder.setId(Tutorial3ApplicationFactory.class.getName());
		builder.setLabel("Admin");
		builder.setTooltip("User administration");
		builder.setIcon(SilkIcons.GROUP);
		createComponentTree(builder);
		return builder.build();
	}

	private static void createComponentTree(final IWorkbenchApplicationModelBuilder builder) {
		builder.addChild(createGenericNode(EntityIds.PERSON, SilkIcons.USER, true));
		builder.addChild(createGenericNode(EntityIds.ROLE, SilkIcons.GROUP));
	}

	private static IComponentNodeModel createGenericNode(final EntityIds entityId, final IImageConstant icon) {
		return createGenericNode(entityId, icon, false);
	}

	private static IComponentNodeModel createGenericNode(final EntityIds entityId, final IImageConstant icon, final boolean select) {
		final IEntityComponentNodesFactory factory = CapWorkbenchToolkit.entityComponentNodesFactory();
		final IComponentNodeModel result = factory.createNode(entityId);
		result.setIcon(icon);
		result.setSelected(select);
		return result;
	}

}
