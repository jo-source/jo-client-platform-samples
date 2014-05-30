/*
 * Copyright (c) 2014, grossmann
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

package org.jowidgets.samples.fatclient.sample3.tags.repository;

import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.service.repository.api.ICrudSupportBeanRepository;
import org.jowidgets.cap.service.repository.tools.HashMapCrudRepository;
import org.jowidgets.common.color.ColorValue;
import org.jowidgets.common.types.Markup;
import org.jowidgets.samples.fatclient.sample3.books.bean.Book;
import org.jowidgets.samples.fatclient.sample3.books.repository.BookRepository;
import org.jowidgets.samples.fatclient.sample3.tags.bean.Tag;

public final class TagRepository {

	public static final Tag MISSING = new Tag("Missing", new ColorValue(255, 0, 0), Markup.STRONG);
	public static final Tag AVAILABLE = new Tag("Available", new ColorValue(0, 128, 0));
	public static final Tag LENT = new Tag("Lent", new ColorValue(255, 128, 0));
	public static final Tag LENDING_PERIOD_EXPIRED = new Tag("Lending period expired", new ColorValue(255, 128, 0), Markup.STRONG);

	public static final ICrudSupportBeanRepository<Tag> INSTANCE = createInstance();

	private TagRepository() {}

	private static ICrudSupportBeanRepository<Tag> createInstance() {
		return new HashMapCrudRepository<Tag>(Tag.class) {

			{
				add(AVAILABLE);
				add(LENT);
				add(LENDING_PERIOD_EXPIRED);
				add(MISSING);
			}

			@Override
			public Object getId(final Tag tag) {
				return tag.getId();
			}

			@Override
			public void delete(final Tag bean, final IExecutionCallback executionCallback) {
				for (final Book book : BookRepository.INSTANCE.read()) {
					if (bean != null && bean.equals(book.getTag())) {
						book.setTag(null);
					}
				}
				super.delete(bean, executionCallback);
			}

		};
	};

}
