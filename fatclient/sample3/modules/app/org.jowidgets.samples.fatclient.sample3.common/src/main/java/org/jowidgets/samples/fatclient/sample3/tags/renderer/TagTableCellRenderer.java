/*
 * Copyright (c) 2014, MGrossmann
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

package org.jowidgets.samples.fatclient.sample3.tags.renderer;

import org.jowidgets.api.color.Colors;
import org.jowidgets.api.model.table.ITableCellBluePrint;
import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.ui.api.attribute.IAttribute;
import org.jowidgets.cap.ui.api.bean.IBeanProxy;
import org.jowidgets.cap.ui.api.table.IBeanTableCellRenderer;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.model.ITableCell;
import org.jowidgets.common.types.Markup;
import org.jowidgets.samples.fatclient.sample3.tags.bean.Tag;

public final class TagTableCellRenderer implements IBeanTableCellRenderer<IBeanDto> {

	@Override
	public void render(
		final ITableCellBluePrint bluePrint,
		final ITableCell original,
		final IBeanProxy<IBeanDto> bean,
		final IAttribute<Object> attribute,
		final int rowIndex,
		final int columnIndex,
		final boolean addedBean) {

		//disable default behavior of bean table;
		bluePrint.setMarkup(Markup.DEFAULT);
		bluePrint.setForegroundColor(Colors.BLACK);
		bluePrint.setBackgroundColor(Colors.WHITE);

		//get rendering hints from tag
		final Object markup = bean.getValue(Tag.MARKUP_PROPERTY);
		final Object foreground = bean.getValue(Tag.FOREGROUND_PROPERTY);
		final Object background = bean.getValue(Tag.BACKGROUND_PROPERTY);

		if (markup instanceof Markup) {
			bluePrint.setMarkup((Markup) markup);
		}

		if (columnIndex == 0) {
			if (foreground instanceof IColorConstant) {
				bluePrint.setForegroundColor((IColorConstant) foreground);
			}

			if (background instanceof IColorConstant) {
				bluePrint.setBackgroundColor((IColorConstant) background);
			}
		}

		else {
			if (background instanceof IColorConstant) {
				bluePrint.setBackgroundColor((IColorConstant) background);
				bluePrint.setSelectedBackgroundColor((IColorConstant) background);
			}
			if (foreground instanceof IColorConstant) {
				bluePrint.setForegroundColor((IColorConstant) foreground);
				bluePrint.setSelectedForegroundColor((IColorConstant) foreground);
			}
		}

	}

}
