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

package org.jowidgets.samples.fatclient.sample3.common.workbench;

import org.jowidgets.addons.icons.silkicons.SilkIconsSubstitude;
import org.jowidgets.cap.ui.tools.workbench.CapWorkbenchModelBuilder;
import org.jowidgets.samples.fatclient.sample3.common.application.FatClientSample3ApplicationFactory;
import org.jowidgets.samples.fatclient.sample3.lookup.service.LookUpServiceInitializer;
import org.jowidgets.samples.fatclient.sample3.lookup.ui.LookupClient;
import org.jowidgets.workbench.api.IWorkbench;
import org.jowidgets.workbench.api.IWorkbenchFactory;
import org.jowidgets.workbench.toolkit.api.IWorkbenchModelBuilder;
import org.jowidgets.workbench.toolkit.api.WorkbenchToolkit;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class FatClientSample3Workbench implements IWorkbenchFactory {

	private final boolean rwt;

	public FatClientSample3Workbench() {
		this(false);
	}

	public FatClientSample3Workbench(final boolean rwt) {
		this.rwt = rwt;
	}

	@Override
	public IWorkbench create() {

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		SilkIconsSubstitude.substitude();

		LookUpServiceInitializer.registerLookUpService();
		LookupClient.initializeLookupsAsync();

		final IWorkbenchModelBuilder builder = new CapWorkbenchModelBuilder();
		builder.setLoginCallback(null);
		builder.setLabel("Fat client sample3");
		builder.addApplication(FatClientSample3ApplicationFactory.create());
		//builder.setApplicationNavigator(false);
		builder.setInitialMaximized(rwt);
		builder.setDecorated(!rwt);

		return WorkbenchToolkit.getWorkbenchPartFactory().workbench(builder.build());
	}

}
