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

package org.jowidgets.samples.mongodb.sample1.app.service.persistence;

import org.jowidgets.samples.mongodb.sample1.app.common.bean.IPhoneNumber;

import com.mongodb.DBObject;

public class PhoneNumber extends GenericBean implements IPhoneNumber {

	public static final String BEAN_TYPE_ID = "PHONE_NUMBER";

	private static final long serialVersionUID = -872265010584978183L;

	public PhoneNumber() {
		this(null);
	}

	public PhoneNumber(final DBObject dbObject) {
		super(IPhoneNumber.BEAN_TYPE_ID, dbObject);
	}

	@Override
	public String getNumber() {
		return getString(NUMBER_PROPERTY);
	}

	@Override
	public void setNumber(final String number) {
		put(NUMBER_PROPERTY, number);
	}

	@Override
	public String getProvider() {
		return getString(PROVIDER_PROPERTY);
	}

	@Override
	public void setProvider(final String provider) {
		put(PROVIDER_PROPERTY, provider);
	}

	@Override
	public String toString() {
		return "PhoneNumer [number=" + getNumber() + ", provider=" + getProvider() + "]";
	}

}
