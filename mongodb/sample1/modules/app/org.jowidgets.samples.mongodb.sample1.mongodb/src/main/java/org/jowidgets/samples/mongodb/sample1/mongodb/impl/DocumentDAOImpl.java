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

package org.jowidgets.samples.mongodb.sample1.mongodb.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.samples.mongodb.sample1.mongodb.api.BeanTypeIdMapperProvider;
import org.jowidgets.samples.mongodb.sample1.mongodb.api.IDocumentDAO;
import org.jowidgets.util.Assert;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

final class DocumentDAOImpl implements IDocumentDAO {

	@Override
	public DBCursor find(final Class<? extends IBean> beanType, final Object beanTypeId) {
		return find(beanType, beanTypeId, (DBObject) null);
	}

	@Override
	public DBCursor find(final Class<? extends IBean> beanType, final Object beanTypeId, final DBObject query) {
		Assert.paramNotNull(beanType, "beanType");
		Assert.paramNotNull(beanTypeId, "beanTypeId");
		final DBCollection collection = BeanTypeIdMapperProvider.getCollection(beanTypeId);
		collection.setObjectClass(beanType);
		final DBObject typeQuery = BeanTypeIdMapperProvider.getQuery(beanTypeId);
		final DBObject conjunctedQuery = conjunct(query, typeQuery);
		if (conjunctedQuery != null) {
			return collection.find(conjunctedQuery);
		}
		else {
			return collection.find();
		}
	}

	@Override
	public long count(final Class<? extends IBean> beanType, final Object beanTypeId) {
		return count(beanType, beanTypeId, (DBObject) null);
	}

	@Override
	public long count(final Class<? extends IBean> beanType, final Object beanTypeId, final DBObject query) {
		Assert.paramNotNull(beanType, "beanType");
		Assert.paramNotNull(beanTypeId, "beanTypeId");
		final DBCollection collection = BeanTypeIdMapperProvider.getCollection(beanTypeId);
		collection.setObjectClass(beanType);
		final DBObject typeQuery = BeanTypeIdMapperProvider.getQuery(beanTypeId);
		final DBObject conjunctedQuery = conjunct(query, typeQuery);
		if (conjunctedQuery != null) {
			return collection.count(conjunctedQuery);
		}
		else {
			return collection.count();
		}
	}

	@Override
	public DBCursor findByIds(
		final Class<? extends IBean> beanType,
		final Object beanTypeId,
		final Collection<? extends ObjectId> ids) {
		Assert.paramNotNull(beanType, "beanType");
		Assert.paramNotNull(beanTypeId, "beanTypeId");
		Assert.paramNotNull(ids, "ids");
		final DBObject idQuery = QueryBuilder.start("_id").in(ids).get();
		final DBCollection collection = BeanTypeIdMapperProvider.getCollection(beanTypeId);
		collection.setObjectClass(beanType);
		final DBObject typeQuery = BeanTypeIdMapperProvider.getQuery(beanTypeId);
		final DBObject conjunctedQuery = conjunct(idQuery, typeQuery);
		return collection.find(conjunctedQuery);
	}

	@Override
	public DBObject findById(final Class<? extends IBean> beanType, final Object beanTypeId, final ObjectId id) {
		Assert.paramNotNull(beanType, "beanType");
		Assert.paramNotNull(beanTypeId, "beanTypeId");
		Assert.paramNotNull(id, "id");
		final DBObject idQuery = QueryBuilder.start("_id").in(id).get();
		final DBCollection collection = BeanTypeIdMapperProvider.getCollection(beanTypeId);
		collection.setObjectClass(beanType);
		final DBObject typeQuery = BeanTypeIdMapperProvider.getQuery(beanTypeId);
		final DBObject conjunctedQuery = conjunct(idQuery, typeQuery);
		return collection.findOne(conjunctedQuery);
	}

	@Override
	public DBCursor findByBeanKeys(
		final Class<? extends IBean> beanType,
		final Object beanTypeId,
		final Collection<? extends IBeanKey> keys) {
		Assert.paramNotNull(beanType, "beanType");
		Assert.paramNotNull(beanTypeId, "beanTypeId");
		Assert.paramNotNull(keys, "keys");
		final List<ObjectId> ids = new LinkedList<ObjectId>();
		for (final IBeanKey key : keys) {
			ids.add((ObjectId) key.getId());
		}
		return findByIds(beanType, beanTypeId, ids);
	}

	private DBObject conjunct(final DBObject object1, final DBObject object2) {
		if (object1 != null && object2 != null) {
			return QueryBuilder.start().and(object1, object2).get();
		}
		else if (object1 != null) {
			return object1;
		}
		else if (object2 != null) {
			return object2;
		}
		else {
			return null;
		}
	}

}
