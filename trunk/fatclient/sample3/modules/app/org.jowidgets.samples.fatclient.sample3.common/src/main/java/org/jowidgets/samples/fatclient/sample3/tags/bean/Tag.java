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

package org.jowidgets.samples.fatclient.sample3.tags.bean;

import java.io.Serializable;
import java.util.UUID;

import org.jowidgets.api.color.Colors;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Markup;

public final class Tag implements Serializable {

	public static final String ID_PROPERTY = "id";
	public static final String LABEL_PROPERTY = "label";
	public static final String MARKUP_PROPERTY = "markup";
	public static final String BACKGROUND_PROPERTY = "background";
	public static final String FOREGROUND_PROPERTY = "foreground";

	private static final long serialVersionUID = 4460355581200878674L;

	private Object id;
	private String label;
	private Markup markup;
	private IColorConstant background;
	private IColorConstant foreground;

	public Tag() {
		this(null);
	}

	public Tag(final String label) {
		this(label, Colors.BLACK, Colors.WHITE, Markup.DEFAULT);
	}

	public Tag(final String label, final IColorConstant foreground) {
		this(label, foreground, Colors.WHITE, Markup.DEFAULT);
	}

	public Tag(final String label, final IColorConstant foreground, final Markup markup) {
		this(label, foreground, Colors.WHITE, markup);
	}

	public Tag(final String label, final IColorConstant foreground, final IColorConstant background, final Markup markup) {
		this.id = UUID.randomUUID().toString();
		this.label = label;
		this.background = background;
		this.foreground = foreground;
		this.markup = markup;
	}

	public Object getId() {
		return id;
	}

	public void setId(final Object id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public Markup getMarkup() {
		return markup;
	}

	public void setMarkup(final Markup markup) {
		this.markup = markup;
	}

	public IColorConstant getBackground() {
		return background;
	}

	public void setBackground(final IColorConstant background) {
		this.background = background;
	}

	public IColorConstant getForeground() {
		return foreground;
	}

	public void setForeground(final IColorConstant foreground) {
		this.foreground = foreground;
	}

}
