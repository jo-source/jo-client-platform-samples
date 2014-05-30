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

package org.jowidgets.samples.fatclient.sample3.books.repository;

import java.util.Random;

import org.jowidgets.cap.service.repository.tools.HashMapCrudRepository;
import org.jowidgets.samples.fatclient.sample3.books.bean.Book;
import org.jowidgets.samples.fatclient.sample3.tags.repository.TagRepository;

public final class BookRepository {

	public static final HashMapCrudRepository<Book> INSTANCE = createInstance();

	private BookRepository() {}

	private static HashMapCrudRepository<Book> createInstance() {
		return new HashMapCrudRepository<Book>(Book.class) {

			{
				add(new Book("Jazz piano tutorial", "Evan Steve", "978-1898137009"));
				add(new Book("Cooking muffins", "Richard Devil", "928-1838113009", TagRepository.LENT));
				add(new Book("Math of semitones", "Evan Steve", "921-1831232009"));
				add(new Book("Secret of 42", "Bill Bang", "921-1823432009", TagRepository.MISSING));
				add(new Book("Almost all prime numbers listed", "Evan Steve", "921-18234532009"));

				//add some generic books
				final Random random = new Random();
				for (int i = 0; i < 300; i++) {
					final Book book = new Book("Title " + i, "Generic Ghost Writer", "921-" + (1831232009 + i));
					final double prob = random.nextDouble();
					if (prob < 0.75) {
						book.setTag(TagRepository.AVAILABLE);
					}
					else if (prob < 0.85) {
						book.setTag(TagRepository.LENT);
					}
					else if (prob < 0.95) {
						book.setTag(TagRepository.LENDING_PERIOD_EXPIRED);
					}
					else {
						book.setTag(TagRepository.MISSING);
					}
					add(book);
				}
			}

			@Override
			public Object getId(final Book book) {
				return book.getId();
			}

		};
	};

}
