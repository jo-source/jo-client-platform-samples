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

package org.jowidgets.samples.mongodb.sample1.mongodb.impl;

import java.util.Collection;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.execution.IExecutableChecker;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.common.api.validation.IBeanValidator;
import org.jowidgets.cap.service.api.bean.IBeanDtoFactory;
import org.jowidgets.cap.service.api.bean.IBeanInitializer;
import org.jowidgets.cap.service.tools.creator.AbstractSyncCreatorServiceImpl;
import org.jowidgets.samples.mongodb.sample1.mongodb.api.BeanTypeIdMapperProvider;
import org.jowidgets.util.Assert;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

final class SyncMongoDBCreatorServiceImpl<BEAN_TYPE extends IBean> extends AbstractSyncCreatorServiceImpl<BEAN_TYPE> {

	private final Class<? extends BEAN_TYPE> beanType;
	private final Object beanTypeId;

	SyncMongoDBCreatorServiceImpl(
		final Class<? extends BEAN_TYPE> beanType,
		final Object beanTypeId,
		final IBeanDtoFactory<BEAN_TYPE> dtoFactory,
		final IBeanInitializer<BEAN_TYPE> beanInitializer,
		final IExecutableChecker<BEAN_TYPE> executableChecker,
		final IBeanValidator<BEAN_TYPE> beanValidator) {

		super(beanType, dtoFactory, beanInitializer, executableChecker, beanValidator);

		Assert.paramNotNull(beanType, "beanType");
		Assert.paramNotNull(beanTypeId, "beanTypeId");

		this.beanType = beanType;
		this.beanTypeId = beanTypeId;
	}

	@Override
	protected BEAN_TYPE createBean(final Collection<IBeanKey> parentBeanKeys, final IExecutionCallback executionCallback) {
		try {
			final BEAN_TYPE result = beanType.newInstance();
			return result;
		}
		catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void persistBean(
		final Collection<IBeanKey> parentBeanKeys,
		final BEAN_TYPE bean,
		final IExecutionCallback executionCallback) {
		final DBCollection collection = BeanTypeIdMapperProvider.getCollection(beanTypeId);
		collection.insert((DBObject) bean);
	}

}
