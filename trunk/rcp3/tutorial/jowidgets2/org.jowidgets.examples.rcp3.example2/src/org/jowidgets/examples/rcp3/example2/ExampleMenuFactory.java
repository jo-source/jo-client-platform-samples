/*
 * Copyright (c) 2014, grossmann
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

package org.jowidgets.examples.rcp3.example2;

import org.jowidgets.api.model.item.IActionItemModel;
import org.jowidgets.api.model.item.ICheckedItemModel;
import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.api.model.item.IRadioItemModel;
import org.jowidgets.common.widgets.controller.IActionListener;
import org.jowidgets.common.widgets.controller.IItemStateListener;
import org.jowidgets.tools.model.item.MenuModel;

final class ExampleMenuFactory {

	private ExampleMenuFactory() {}

	static IMenuModel create() {

		final IMenuModel result = new MenuModel("Main menu");

		final IActionItemModel actionItem = result.addActionItem("ActionItem");
		actionItem.addActionListener(new IActionListener() {
			@Override
			public void actionPerformed() {
				//CHECKSTYLE:OFF
				System.out.println("Action Performed");
				//CHECKSTYLE:ON
			}
		});

		final ICheckedItemModel checkedItem = result.addCheckedItem("CheckedItem");
		checkedItem.setSelected(true);
		checkedItem.addItemListener(new IItemStateListener() {
			@Override
			public void itemStateChanged() {
				//CHECKSTYLE:OFF
				System.out.println(checkedItem.isSelected());
				//CHECKSTYLE:ON
			}
		});

		result.addSeparator();

		final IMenuModel subMenu = result.addMenu("SubMenu");
		final IRadioItemModel radio1 = subMenu.addRadioItem("Radio1");
		subMenu.addRadioItem("Radio2").setSelected(true);
		subMenu.addRadioItem("Radio3");

		radio1.addItemListener(new IItemStateListener() {
			@Override
			public void itemStateChanged() {
				//CHECKSTYLE:OFF
				System.out.println(radio1.isSelected());
				//CHECKSTYLE:ON
			}
		});

		return result;

	}

}
