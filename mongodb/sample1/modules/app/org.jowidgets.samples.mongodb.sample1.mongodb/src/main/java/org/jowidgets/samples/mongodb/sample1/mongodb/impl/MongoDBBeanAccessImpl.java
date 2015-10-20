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
import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.service.api.CapServiceToolkit;
import org.jowidgets.cap.service.api.bean.IBeanAccess;
import org.jowidgets.samples.mongodb.sample1.mongodb.api.DocumentDAO;
import org.jowidgets.util.Assert;

import com.mongodb.DBCursor;

final class MongoDBBeanAccessImpl<BEAN_TYPE extends IBean> implements IBeanAccess<BEAN_TYPE> {

	private final Class<BEAN_TYPE> beanType;
	private final Object beanTypeId;

	@SuppressWarnings("unchecked")
	MongoDBBeanAccessImpl(final Class<? extends BEAN_TYPE> beanType, final Object beanTypeId) {
		Assert.paramNotNull(beanType, "beanType");
		Assert.paramNotNull(beanTypeId, "beanTypeId");

		this.beanTypeId = beanTypeId;
		this.beanType = (Class<BEAN_TYPE>) beanType;
	}

	@Override
	public List<BEAN_TYPE> getBeans(final Collection<? extends IBeanKey> keys, final IExecutionCallback executionCallback) {
		Assert.paramNotNull(keys, "keys");

		final DBCursor cursor = DocumentDAO.findByBeanKeys(beanType, beanTypeId, keys);

		final List<BEAN_TYPE> result = new LinkedList<BEAN_TYPE>();
		try {
			while (cursor.hasNext()) {
				CapServiceToolkit.checkCanceled(executionCallback);
				@SuppressWarnings("unchecked")
				final BEAN_TYPE bean = (BEAN_TYPE) cursor.next();
				result.add(bean);
			}
		}
		finally {
			cursor.close();
		}

		return result;
	}

	@Override
	public Class<BEAN_TYPE> getBeanType() {
		return beanType;
	}

	@Override
	public Object getBeanTypeId() {
		return beanTypeId;
	}

	@Override
	public Object getId(final BEAN_TYPE bean) {
		return bean.getId();
	}

	@Override
	public long getVersion(final BEAN_TYPE bean) {
		return bean.getVersion();
	}

	@Override
	public void flush() {}

}
