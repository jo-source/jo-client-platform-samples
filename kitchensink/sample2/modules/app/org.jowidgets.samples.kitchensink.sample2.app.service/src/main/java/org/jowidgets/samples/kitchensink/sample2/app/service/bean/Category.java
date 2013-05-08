/*
 * Copyright (c) 2013, M. Grossmann
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
package org.jowidgets.samples.kitchensink.sample2.app.service.bean;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.ICategory;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Category extends Bean implements ICategory {

	@Basic
	@Index(name = "CategoryNameIndex")
	private String name;

	@Basic
	private String description;

	@OneToMany(mappedBy = "category")
	private List<RoleCategoryLink> categoryRoleLinks = new LinkedList<RoleCategoryLink>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPER_CATEGORY_ID", nullable = true, insertable = false, updatable = false)
	private Category superCategory;

	@Column(name = "SUPER_CATEGORY_ID", nullable = true)
	private Long superCategoryId;

	@OneToMany(mappedBy = "superCategory")
	private final List<Category> subCategories = new LinkedList<Category>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	public List<RoleCategoryLink> getCategoryRoleLinks() {
		return categoryRoleLinks;
	}

	public void setCategoryRoleLinks(final List<RoleCategoryLink> categoryRoleLinks) {
		this.categoryRoleLinks = categoryRoleLinks;
	}

	@Override
	public void setSuperCategoryId(final Long superCategoryId) {
		this.superCategoryId = superCategoryId;
	}

	@Override
	public Long getSuperCategoryId() {
		return superCategoryId;
	}

}
