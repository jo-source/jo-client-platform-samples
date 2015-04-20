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

package org.jowidgets.samples.kitchensink.sample1.ui.workbench;

import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.content.IContentCreator;
import org.jowidgets.cap.ui.tools.workbench.CapWorkbenchModelBuilder;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.samples.kitchensink.sample1.ui.SampleDefaultsInitializer;
import org.jowidgets.samples.kitchensink.sample1.ui.workbench.application.SampleApplicationFactory;
import org.jowidgets.security.tools.SecurityContext;
import org.jowidgets.tools.widgets.blueprint.BPF;
import org.jowidgets.workbench.api.IWorkbench;
import org.jowidgets.workbench.api.IWorkbenchContext;
import org.jowidgets.workbench.api.IWorkbenchFactory;
import org.jowidgets.workbench.toolkit.api.IWorkbenchInitializeCallback;
import org.jowidgets.workbench.toolkit.api.IWorkbenchModel;
import org.jowidgets.workbench.toolkit.api.IWorkbenchModelBuilder;
import org.jowidgets.workbench.toolkit.api.WorkbenchToolkit;

public class SampleWorkbench implements IWorkbenchFactory {

	@Override
	public IWorkbench create() {
		SampleDefaultsInitializer.initialize();

		final IWorkbenchModelBuilder builder = new CapWorkbenchModelBuilder();
		builder.setLabel(Messages.getString("SampleWorkbench.sample_application_1"));

		builder.addInitializeCallback(new IWorkbenchInitializeCallback() {
			@Override
			public void onContextInitialize(final IWorkbenchModel model, final IWorkbenchContext context) {
				model.addApplication(SampleApplicationFactory.create());
			}
		});

		builder.setStatusBarCreator(new IContentCreator() {
			@Override
			public void createContent(final IContainer container) {
				container.setLayout(new MigLayoutDescriptor("[grow]", "2[]2"));
				container.add(BPF.textLabel(SecurityContext.getUsername()).alignRight(), "alignx r");
			}
		});

		return WorkbenchToolkit.getWorkbenchPartFactory().workbench(builder.build());
	}

}
