/*
 * Copyright (c) 2015, Michael
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

package org.jowidgets.samples.kitchensink.sample2.app.service.entity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.bean.IBeanData;
import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.exception.ServiceCanceledException;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.common.api.execution.IResultCallback;
import org.jowidgets.cap.common.api.execution.UserQuestionResult;
import org.jowidgets.cap.common.api.service.ICreatorService;
import org.jowidgets.cap.service.api.CapServiceToolkit;
import org.jowidgets.cap.service.jpa.tools.entity.EntityManagerProvider;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IPerson;
import org.jowidgets.samples.kitchensink.sample2.app.service.dao.PersonDAO;
import org.jowidgets.util.Assert;
import org.jowidgets.util.EmptyCheck;

final class DecoratedPersonCreatorService implements ICreatorService {

	private final ICreatorService original;

	DecoratedPersonCreatorService(final ICreatorService original) {
		Assert.paramNotNull(original, "original");
		this.original = original;
	}

	@Override
	public void create(
		final IResultCallback<List<IBeanDto>> result,
		final List<? extends IBeanKey> parentBeanKeys,
		final Collection<? extends IBeanData> beans,
		final IExecutionCallback executionCallback) {
		final Collection<IBeanData> beansToCreate = getBeansToCreate(beans, executionCallback);
		if (EmptyCheck.isEmpty(beansToCreate)) {
			//TODO use the canceled exception instead after switched to no jo client platform version 0.45.0 or higher
			//at the moment <=0.44.0 the service canceled exception will produce an unknown error on default creator action 
			result.exception(new ServiceCanceledException());
			//result.exception(new ExecutableCheckException(null, "Creation abborted by user", "Creation abborted by user"));
		}
		else {
			original.create(result, parentBeanKeys, beansToCreate, executionCallback);
		}
	}

	private Collection<IBeanData> getBeansToCreate(
		final Collection<? extends IBeanData> beans,
		final IExecutionCallback executionCallback) {
		final List<IBeanData> result = new LinkedList<IBeanData>();
		for (final IBeanData bean : beans) {
			CapServiceToolkit.checkCanceled(executionCallback);
			if (isPersonCreationDesired(
					executionCallback,
					(String) bean.getValue(IPerson.NAME_PROPERTY),
					(String) bean.getValue(IPerson.LAST_NAME_PROPERTY))) {
				result.add(bean);
			}
		}
		return result;
	}

	private boolean isPersonCreationDesired(
		final IExecutionCallback executionCallback,
		final String firstName,
		final String lastName) {
		if (PersonDAO.hasPersonWithFirstAndLastName(EntityManagerProvider.get(), firstName, lastName)) {
			final UserQuestionResult questionResult = executionCallback.userQuestion("The user with the name '"
				+ firstName
				+ " "
				+ lastName
				+ "' already exists.\n Are you still want to create the user?");
			return questionResult == UserQuestionResult.YES;
		}
		else {
			return true;
		}
	}

}
