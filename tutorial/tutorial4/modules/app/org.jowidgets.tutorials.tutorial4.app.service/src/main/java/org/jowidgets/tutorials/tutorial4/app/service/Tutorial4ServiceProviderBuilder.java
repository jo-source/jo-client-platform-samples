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

package org.jowidgets.tutorials.tutorial4.app.service;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.service.IEntityService;
import org.jowidgets.cap.common.api.service.IExecutorService;
import org.jowidgets.cap.service.api.bean.IBeanAccess;
import org.jowidgets.cap.service.api.executor.IBeanExecutor;
import org.jowidgets.cap.service.hibernate.api.HibernateServiceToolkit;
import org.jowidgets.cap.service.jpa.api.IJpaServicesDecoratorProviderBuilder;
import org.jowidgets.cap.service.jpa.api.JpaServiceToolkit;
import org.jowidgets.cap.service.tools.CapServiceProviderBuilder;
import org.jowidgets.service.api.IServiceId;
import org.jowidgets.service.api.IServicesDecoratorProvider;
import org.jowidgets.tutorials.tutorial4.app.common.bean.IPerson;
import org.jowidgets.tutorials.tutorial4.app.common.service.executor.ExecutorServices;
import org.jowidgets.tutorials.tutorial4.app.common.service.security.AuthorizationProviderServiceId;
import org.jowidgets.tutorials.tutorial4.app.service.bean.Person;
import org.jowidgets.tutorials.tutorial4.app.service.entity.Tutorial4EntityServiceBuilder;
import org.jowidgets.tutorials.tutorial4.app.service.executor.PersonLongLastingExecutor;
import org.jowidgets.tutorials.tutorial4.app.service.security.AuthorizationProviderServiceImpl;

public class Tutorial4ServiceProviderBuilder extends CapServiceProviderBuilder {

	public Tutorial4ServiceProviderBuilder() {
		super();
		//security
		addService(AuthorizationProviderServiceId.ID, new AuthorizationProviderServiceImpl());

		//EntityService
		addService(IEntityService.ID, new Tutorial4EntityServiceBuilder(this).build());

		//Executor Services
		addPersonExecutorService(ExecutorServices.PERSON_LONG_LASTING, new PersonLongLastingExecutor());

		//jpa decorators
		addServiceDecorator(createJpaServiceDecoratorProvider());
		addServiceDecorator(createCancelServiceDecoratorProvider());
	}

	private IServicesDecoratorProvider createJpaServiceDecoratorProvider() {
		final IJpaServicesDecoratorProviderBuilder builder = JpaServiceToolkit.serviceDecoratorProviderBuilder("tutorial4PersistenceUnit");
		builder.addExceptionDecorator(HibernateServiceToolkit.exceptionDecorator());
		return builder.build();
	}

	private IServicesDecoratorProvider createCancelServiceDecoratorProvider() {
		return HibernateServiceToolkit.cancelServiceDecoratorProviderBuilder("tutorial4PersistenceUnit").build();
	}

	private <BEAN_TYPE extends IBean, PARAM_TYPE> void addPersonExecutorService(
		final IServiceId<? extends IExecutorService<PARAM_TYPE>> id,
		final IBeanExecutor<? extends BEAN_TYPE, PARAM_TYPE> beanExecutor) {
		final IBeanAccess<Person> beanAccess = JpaServiceToolkit.serviceFactory().beanAccess(Person.class);
		addExecutorService(id, beanExecutor, beanAccess, IPerson.ALL_PROPERTIES);
	}
}
