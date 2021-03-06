/*
 * Copyright (c) 2014, MGrossmann
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

package org.jowidgets.samples.fatclient.sample3.lookup.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.CapCommonToolkit;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.common.api.lookup.ILookUpEntry;
import org.jowidgets.cap.common.api.lookup.ILookUpToolkit;
import org.jowidgets.cap.service.api.adapter.ISyncLookUpService;
import org.jowidgets.samples.fatclient.sample3.tags.bean.Tag;
import org.jowidgets.samples.fatclient.sample3.tags.repository.TagRepository;

public final class TagLookUpService implements ISyncLookUpService {

	@Override
	public List<ILookUpEntry> readValues(final IExecutionCallback executionCallback) {
		final ILookUpToolkit lookUpToolkit = CapCommonToolkit.lookUpToolkit();
		final List<IBeanKey> emptyList = Collections.emptyList();
		final List<ILookUpEntry> result = new LinkedList<ILookUpEntry>();
		//allow that no tag is set
		result.add(lookUpToolkit.lookUpEntry(null, " "));

		//add the residual tags
		final List<Tag> tags = TagRepository.INSTANCE.read(emptyList, executionCallback);
		Collections.sort(tags);
		for (final Tag tag : tags) {
			result.add(lookUpToolkit.lookUpEntry(tag, tag.getLabel()));
		}
		return Collections.unmodifiableList(result);
	}
}
