/*
 * Copyright (c) 2013, Lukas Gross, grossmann
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

package org.jowidgets.samples.mongodb.sample1.mongodb.api;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.util.Assert;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public final class BeanTypeIdMapperProvider {

	private static IBeanTypeIdMapperProvider instance;

	private BeanTypeIdMapperProvider() {}

	public static synchronized void initialize(final IBeanTypeIdMapperProvider instance) {
		Assert.paramNotNull(instance, "instance");
		if (BeanTypeIdMapperProvider.instance == null) {
			BeanTypeIdMapperProvider.instance = instance;
		}
		else {
			throw new IllegalStateException("The IBeanTypeIdMapperProvider is already initialized");
		}
	}

	public static IBeanTypeIdMapperProvider getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	public static IBeanTypeIdMapper get(final Object beanTypeId) {
		return getInstance().get(beanTypeId);
	}

	public static <BEAN_TYPE extends IBean> DBCollection getCollection(final Object beanTypeId) {
		return getInstance().get(beanTypeId).getCollection();
	}

	public static <BEAN_TYPE extends IBean> DBObject getQuery(final Object beanTypeId) {
		return getInstance().get(beanTypeId).getQuery();
	}

	private static synchronized void createInstance() {
		if (instance == null) {
			final ServiceLoader<IBeanTypeIdMapperProvider> serviceLoader = ServiceLoader.load(IBeanTypeIdMapperProvider.class);
			final Iterator<IBeanTypeIdMapperProvider> iterator = serviceLoader.iterator();

			if (!iterator.hasNext()) {
				instance = new DefaultBeanTypeIdMapperProvider();
			}
			else {
				instance = iterator.next();
				if (iterator.hasNext()) {
					throw new IllegalStateException("More than one implementation found for '"
						+ IBeanTypeIdMapperProvider.class.getName()
						+ "'");
				}
			}
		}
	}

	private static final class DefaultBeanTypeIdMapperProvider implements IBeanTypeIdMapperProvider {

		@Override
		public IBeanTypeIdMapper get(final Object beanTypeId) {
			if (!(beanTypeId instanceof String)) {
				throw new IllegalArgumentException(
					"Param 'beanTypeId' must be the collection name and therefore a string, or a custom beanTypeIdMapper must be injected!");
			}
			final String collectionName = (String) beanTypeId;

			return new IBeanTypeIdMapper() {

				@Override
				public DBCollection getCollection() {
					return MongoDBProvider.get().getCollection(collectionName);
				}

				@Override
				public DBObject getQuery() {
					return null;
				}

			};

		}

	}

}
