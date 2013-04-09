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

import java.util.Collection;

import org.bson.types.ObjectId;
import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IBeanKey;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public final class DocumentDAO {

	private DocumentDAO() {}

	public static DBCursor find(final Class<? extends IBean> beanType, final Object beanTypeId) {
		return MongoDBServiceToolkit.documentDAO().find(beanType, beanTypeId);
	}

	public static DBCursor find(final Class<? extends IBean> beanType, final Object beanTypeId, final DBObject query) {
		return MongoDBServiceToolkit.documentDAO().find(beanType, beanTypeId, query);
	}

	public static long count(final Class<? extends IBean> beanType, final Object beanTypeId) {
		return MongoDBServiceToolkit.documentDAO().count(beanType, beanTypeId);
	}

	public static long count(final Class<? extends IBean> beanType, final Object beanTypeId, final DBObject query) {
		return MongoDBServiceToolkit.documentDAO().count(beanType, beanTypeId, query);
	}

	public static DBCursor findByIds(
		final Class<? extends IBean> beanType,
		final Object beanTypeId,
		final Collection<? extends ObjectId> ids) {
		return MongoDBServiceToolkit.documentDAO().findByIds(beanType, beanTypeId, ids);
	}

	public static DBCursor findByBeanKeys(
		final Class<? extends IBean> beanType,
		final Object beanTypeId,
		final Collection<? extends IBeanKey> keys) {
		return MongoDBServiceToolkit.documentDAO().findByBeanKeys(beanType, beanTypeId, keys);
	}

	public static DBObject findById(final Class<? extends IBean> beanType, final Object beanTypeId, final ObjectId id) {
		return MongoDBServiceToolkit.documentDAO().findById(beanType, beanTypeId, id);
	}
}
