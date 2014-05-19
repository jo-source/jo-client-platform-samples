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

package org.jowidgets.samples.fatclient.sample2.common.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.exception.DeletedBeanException;
import org.jowidgets.cap.common.api.exception.ServiceException;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.service.repository.api.ICrudSupportBeanRepository;
import org.jowidgets.cap.service.repository.tools.HashMapCrudRepository;
import org.jowidgets.samples.fatclient.sample2.common.bean.RadioStation;
import org.jowidgets.samples.fatclient.sample2.common.bean.RadioStationList;
import org.jowidgets.util.EmptyCheck;

public final class RadioStationRepository {

	public static final ICrudSupportBeanRepository<RadioStation> INSTANCE = createInstance();

	private RadioStationRepository() {}

	private static ICrudSupportBeanRepository<RadioStation> createInstance() {
		return new RadioStationRepositoryImpl();
	};

	private static final class RadioStationRepositoryImpl extends HashMapCrudRepository<RadioStation> {

		private RadioStationRepositoryImpl() {
			super(RadioStation.class);
		}

		@Override
		public List<RadioStation> read(final List<? extends IBeanKey> parentBeanKeys, final IExecutionCallback executionCallback) {
			final RadioStationList radioStationList = findRadioStationList(parentBeanKeys, executionCallback, true);
			if (radioStationList == null) {
				return Collections.emptyList();
			}
			else {
				final List<RadioStation> result = new LinkedList<RadioStation>();
				for (final RadioStation radioStation : super.read(parentBeanKeys, executionCallback)) {
					if (radioStationList.equals(radioStation.getRadioStationList())) {
						result.add(radioStation);
					}
				}
				return result;
			}
		}

		@Override
		public void postCreate(
			final Collection<IBeanKey> parentBeanKeys,
			final RadioStation bean,
			final IExecutionCallback executionCallback) {
			bean.setRadioStationList(findRadioStationList(parentBeanKeys, executionCallback, false));
			super.postCreate(parentBeanKeys, bean, executionCallback);
		}

		private RadioStationList findRadioStationList(
			final Collection<? extends IBeanKey> parentBeanKeys,
			final IExecutionCallback executionCallback,
			final boolean allowNull) {
			final IBeanKey parentKey;
			if (!EmptyCheck.isEmpty(parentBeanKeys)) {
				parentKey = parentBeanKeys.iterator().next();
			}
			else if (allowNull) {
				return null;
			}
			else {
				throw new ServiceException("No parent key is set for created radion station");
			}

			final RadioStationList radioStationList = RadioStationListRepository.INSTANCE.find(
					parentKey.getId(),
					executionCallback);

			if (!allowNull && radioStationList == null) {
				throw new DeletedBeanException(parentKey.getId());
			}
			else {
				return radioStationList;
			}
		}

	}

}
