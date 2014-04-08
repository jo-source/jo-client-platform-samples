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

package org.jowidgets.tutorials.rcp3.tutorial1.common.property;

import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.CapCommonToolkit;
import org.jowidgets.cap.common.api.bean.IBeanPropertyBuilder;
import org.jowidgets.cap.common.api.bean.IProperty;
import org.jowidgets.tutorials.rcp3.tutorial1.common.bean.IPerson;

public final class PersonPropertiesFactory {

	private PersonPropertiesFactory() {}

	public static List<IProperty> create() {
		final List<IProperty> result = new LinkedList<IProperty>();

		//IPerson.NAME_PROPERTY
		IBeanPropertyBuilder builder = builder(IPerson.NAME_PROPERTY);
		builder.setLabel("Name");
		builder.setDescription("The name of the person");
		builder.setMandatory(true);
		result.add(builder.build());

		//IPerson.DATE_OF_BIRTH_PROPERTY
		builder = builder(IPerson.DATE_OF_BIRTH_PROPERTY);
		builder.setLabel("Date of birth");
		builder.setDescription("The date when the person was born");
		result.add(builder.build());

		//IPerson.GENDER_PROPERTY
		builder = builder(IPerson.GENDER_PROPERTY);
		builder.setLabel("Gender");
		builder.setDescription("The persons gender");
		result.add(builder.build());

		return result;
	}

	private static IBeanPropertyBuilder builder(final String propertyName) {
		return CapCommonToolkit.beanPropertyBuilder(IPerson.class, propertyName);
	}

}
