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

package org.jowidgets.tutorials.rcp3.tutorial1.service;

import org.jowidgets.cap.service.hibernate.api.HibernateServiceToolkit;
import org.jowidgets.cap.service.jpa.api.IJpaServicesDecoratorProviderBuilder;
import org.jowidgets.cap.service.jpa.api.JpaServiceToolkit;
import org.jowidgets.service.api.IServicesDecoratorProvider;
import org.jowidgets.service.tools.ServiceProviderBuilder;
import org.jowidgets.tutorials.rcp3.tutorial1.common.service.security.AuthorizationProviderServiceId;
import org.jowidgets.tutorials.rcp3.tutorial1.service.security.AuthorizationProviderServiceImpl;

public class Tutorial1ServiceProviderBuilder extends ServiceProviderBuilder {

	public Tutorial1ServiceProviderBuilder() {
		super();
		//security
		addService(AuthorizationProviderServiceId.ID, new AuthorizationProviderServiceImpl());

		//jpa decorators
		addServiceDecorator(createJpaServiceDecoratorProvider());
		addServiceDecorator(createCancelServiceDecoratorProvider());
	}

	private IServicesDecoratorProvider createJpaServiceDecoratorProvider() {
		final IJpaServicesDecoratorProviderBuilder builder = JpaServiceToolkit.serviceDecoratorProviderBuilder("tutorial1PersistenceUnit");
		builder.addExceptionDecorator(HibernateServiceToolkit.exceptionDecorator());
		return builder.build();
	}

	private IServicesDecoratorProvider createCancelServiceDecoratorProvider() {
		return HibernateServiceToolkit.cancelServiceDecoratorProviderBuilder("tutorial1PersistenceUnit").build();
	}

}
