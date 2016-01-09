/*
 * Copyright (c) 2012, grossmann
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

package org.jowidgets.samples.kitchensink.sample2.app.ui.initializer;

import org.jowidgets.addons.icons.silkicons.SilkIconsSubstitude;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.types.AutoPackPolicy;
import org.jowidgets.api.widgets.blueprint.ITableBluePrint;
import org.jowidgets.api.widgets.blueprint.defaults.IDefaultInitializer;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintProxyFactory;
import org.jowidgets.cap.ui.api.widgets.IBeanRelationTreeBluePrint;
import org.jowidgets.cap.ui.api.widgets.IBeanSelectionFormBluePrint;
import org.jowidgets.cap.ui.api.widgets.IBeanTableBluePrint;
import org.jowidgets.cap.ui.api.widgets.ILookUpComboBoxSelectionBluePrint;

public final class SampleDefaultsInitializer {

	private SampleDefaultsInitializer() {}

	public static void initialize() {
		SilkIconsSubstitude.substitude();

		final IBluePrintProxyFactory bppf = Toolkit.getBluePrintProxyFactory();

		bppf.addDefaultsInitializer(
				ILookUpComboBoxSelectionBluePrint.class,
				new IDefaultInitializer<ILookUpComboBoxSelectionBluePrint<?>>() {
					@Override
					public void initialize(final ILookUpComboBoxSelectionBluePrint<?> bluePrint) {
						bluePrint.setAutoRefreshOnFocus(true);
					}
				});

		bppf.addDefaultsInitializer(ITableBluePrint.class, new IDefaultInitializer<ITableBluePrint>() {
			@Override
			public void initialize(final ITableBluePrint bluePrint) {
				bluePrint.setEditable(true);
			}
		});

		bppf.addDefaultsInitializer(IBeanTableBluePrint.class, new IDefaultInitializer<IBeanTableBluePrint<?>>() {
			@Override
			public void initialize(final IBeanTableBluePrint<?> bluePrint) {
				bluePrint.setAutoPackPolicy(AutoPackPolicy.ONCE);
				bluePrint.setEditable(true);
				bluePrint.setDefaultCopyAction(true);
				bluePrint.setDefaultPasteAction(true);
			}
		});

		bppf.addDefaultsInitializer(IBeanRelationTreeBluePrint.class, new IDefaultInitializer<IBeanRelationTreeBluePrint<?>>() {
			@Override
			public void initialize(final IBeanRelationTreeBluePrint<?> bluePrint) {
				bluePrint.setDefaultCopyAction(true);
				bluePrint.setDefaultLinkPasteAction(true);
			}
		});

		bppf.addDefaultsInitializer(IBeanSelectionFormBluePrint.class, new IDefaultInitializer<IBeanSelectionFormBluePrint>() {
			@Override
			public void initialize(final IBeanSelectionFormBluePrint bluePrint) {
				bluePrint.setHideReadonlyAttributes(false);
			}
		});
	}

}
