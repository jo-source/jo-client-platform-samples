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

package org.jowidgets.samples.fatclient.sample3.books.bean;

import java.io.Serializable;
import java.util.UUID;

import org.jowidgets.samples.fatclient.sample3.tags.bean.Tag;
import org.jowidgets.samples.fatclient.sample3.tags.repository.TagRepository;

public final class Book implements Serializable {

	public static final String ID_PROPERTY = "id";
	public static final String TITLE_PROPERTY = "title";
	public static final String AUTHOR_PROPERTY = "author";
	public static final String ISBN_PROPERTY = "isbn";
	public static final String TAG_PROPERTY = "tag";

	private static final long serialVersionUID = 9074936692512836147L;

	private Object id;
	private String title;
	private String author;
	private String isbn;
	private Tag tag;

	public Book() {
		this(null, null, null, TagRepository.AVAILABLE);
	}

	public Book(final String title, final String author, final String isbn) {
		this(title, author, isbn, TagRepository.AVAILABLE);
	}

	public Book(final String title, final String author, final String isbn, final Tag tag) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.tag = tag;
	}

	public Object getId() {
		return id;
	}

	public void setId(final Object id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(final String isbn) {
		this.isbn = isbn;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(final Tag tag) {
		this.tag = tag;
	}

}
