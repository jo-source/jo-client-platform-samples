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

import org.jowidgets.samples.kitchensink.sample1.ui.workbench.component.newsticker.view.NewsTableView;
import org.jowidgets.workbench.api.ILayout;
import org.jowidgets.workbench.toolkit.api.IFolderLayoutBuilder;
import org.jowidgets.workbench.toolkit.api.ILayoutBuilder;
import org.jowidgets.workbench.tools.FolderLayoutBuilder;
import org.jowidgets.workbench.tools.LayoutBuilder;

public class NewsComponentDefaultLayout {

	public static final String DEFAULT_LAYOUT_ID = "DEFAULT_LAYOUT_ID";
	public static final String MASTER_FOLDER_ID = "MASTER_FOLDER_ID";

	private final ILayout layout;

	public NewsComponentDefaultLayout() {
		final ILayoutBuilder builder = new LayoutBuilder();
		builder.setId(DEFAULT_LAYOUT_ID).setLayoutContainer(createMasterFolder());
		this.layout = builder.build();
	}

	public ILayout getLayout() {
		return layout;
	}

	private IFolderLayoutBuilder createMasterFolder() {
		final IFolderLayoutBuilder result = new FolderLayoutBuilder(MASTER_FOLDER_ID);
		result.addView(NewsTableView.ID, NewsTableView.DEFAULT_LABEL, NewsTableView.DEFAULT_TOOLTIP);
		return result;
	}

}
