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

package org.jowidgets.samples.mongodb.sample1.mongodb.api;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.ServiceLoader;

import org.jowidgets.util.Assert;

import com.mongodb.MongoClient;

public final class MongoDBClientProvider {

	private static IMongoDBClientProvider instance;

	private MongoDBClientProvider() {}

	public static synchronized void initialize(final IMongoDBClientProvider instance) {
		Assert.paramNotNull(instance, "instance");
		if (MongoDBClientProvider.instance == null) {
			MongoDBClientProvider.instance = instance;
		}
		else {
			throw new IllegalStateException("The IMongoDBClientProvider is already initialized");
		}
	}

	public static IMongoDBClientProvider getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	public static MongoClient get() {
		return getInstance().get();
	}

	private static synchronized void createInstance() {
		if (instance == null) {
			final ServiceLoader<IMongoDBClientProvider> serviceLoader = ServiceLoader.load(IMongoDBClientProvider.class);
			final Iterator<IMongoDBClientProvider> iterator = serviceLoader.iterator();

			if (!iterator.hasNext()) {
				instance = new DefaultMongoDBClientProvider();
			}
			else {
				instance = iterator.next();
				if (iterator.hasNext()) {
					throw new IllegalStateException("More than one implementation found for '"
						+ IMongoDBServiceToolkit.class.getName()
						+ "'");
				}
			}
		}
	}

	private static final class DefaultMongoDBClientProvider implements IMongoDBClientProvider {

		private MongoClient client;

		@Override
		public synchronized MongoClient get() {
			if (client == null) {
				client = createClient();
			}
			return client;
		}

		private MongoClient createClient() {
			try {
				return new MongoClient();
			}
			catch (final UnknownHostException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
