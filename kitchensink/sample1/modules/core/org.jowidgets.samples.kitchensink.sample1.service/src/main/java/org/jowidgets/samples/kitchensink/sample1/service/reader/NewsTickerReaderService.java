/*
 * Copyright (c) 2016, NBeuck
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

package org.jowidgets.samples.kitchensink.sample1.service.reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.common.api.bean.IBeanDtosClearUpdate;
import org.jowidgets.cap.common.api.bean.IBeanDtosUpdate;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.common.api.execution.IResultCallback;
import org.jowidgets.cap.common.api.execution.IUpdatableResultCallback;
import org.jowidgets.cap.common.api.filter.IFilter;
import org.jowidgets.cap.common.api.service.IReaderService;
import org.jowidgets.cap.common.api.sort.ISort;
import org.jowidgets.cap.common.tools.bean.BeanDtosChangeUpdate;
import org.jowidgets.cap.common.tools.bean.BeanDtosInsertionUpdate;
import org.jowidgets.cap.service.api.CapServiceToolkit;
import org.jowidgets.cap.service.api.bean.IBeanDtoFactory;
import org.jowidgets.cap.service.tools.bean.BeanDtoFactoryHelper;
import org.jowidgets.logging.api.ILogger;
import org.jowidgets.logging.api.LoggerProvider;
import org.jowidgets.samples.kitchensink.sample1.common.entity.IMatch;
import org.jowidgets.samples.kitchensink.sample1.service.entity.Match;
import org.jowidgets.util.concurrent.DaemonThreadFactory;

/**
 * Example for a reader service that serves an {@link IUpdatableResultCallback}.
 * 
 * TODO updates that respect filter and sorting
 * 
 * @author NBeuck
 */
public class NewsTickerReaderService implements IReaderService<Void> {

	private static final ILogger LOGGER = LoggerProvider.get(NewsTickerReaderService.class);

	private final IBeanDtoFactory<IMatch> dtoFactory;

	private final Map<IUpdatableResultCallback<IBeanDtosUpdate, ?>, IExecutionCallback> updateCallbacks;
	private final Map<Object, Match> matches;

	private final AtomicBoolean stopped;

	public NewsTickerReaderService() {
		dtoFactory = CapServiceToolkit.dtoFactory(IMatch.class, IMatch.ALL_PROPERTIES);
		stopped = new AtomicBoolean(false);
		updateCallbacks = new ConcurrentHashMap<IUpdatableResultCallback<IBeanDtosUpdate, ?>, IExecutionCallback>();
		matches = new ConcurrentHashMap<Object, Match>();

		Executors.newSingleThreadExecutor(new DaemonThreadFactory("Newsticker")).execute(new MatchGenerator());
	}

	@Override
	public void read(
		final IResultCallback<List<IBeanDto>> resultCallback,
		final List<? extends IBeanKey> parentBeanKeys,
		final IFilter filter,
		final List<? extends ISort> sorting,
		final int firstRow,
		final int maxRows,
		final Void parameter,
		final IExecutionCallback executionCallback) {

		if (resultCallback instanceof IUpdatableResultCallback) {
			@SuppressWarnings({"unchecked", "rawtypes"})
			final IUpdatableResultCallback<IBeanDtosUpdate, List<IBeanDto>> updatableResult = (IUpdatableResultCallback) resultCallback;
			updateCallbacks.put(updatableResult, executionCallback);
		}

		final List<IBeanDto> beanDtos = toBeanDtos(new ArrayList<IBean>(matches.values()), executionCallback);
		resultCallback.finished(sortAndFilterList(beanDtos, sorting, filter, executionCallback));
	}

	@Override
	public void count(
		final IResultCallback<Integer> result,
		final List<? extends IBeanKey> parentBeanKeys,
		final IFilter filter,
		final Void parameter,
		final IExecutionCallback executionCallback) {

		// don't provide a count when using updates
		result.finished(null);
	}

	private List<IBeanDto> toBeanDtos(final List<IBean> beans, final IExecutionCallback executionCallback) {
		if (executionCallback != null) {
			return BeanDtoFactoryHelper.createDtos(dtoFactory, beans, executionCallback);
		}
		else {
			return BeanDtoFactoryHelper.createDtos(dtoFactory, beans);
		}
	}

	private List<IBeanDto> sortAndFilterList(
		final List<IBeanDto> beanDtos,
		final List<? extends ISort> sorting,
		final IFilter filter,
		final IExecutionCallback executionCallback) {

		List<IBeanDto> result = new ArrayList<IBeanDto>(beanDtos);
		if (filter != null) {
			result = CapServiceToolkit.beanDtoCollectionFilter().filter(beanDtos, filter, executionCallback);
		}
		if (sorting != null && sorting.size() > 0) {
			result = CapServiceToolkit.beanDtoCollectionSorter().sort(result, sorting, executionCallback);
		}

		return result;
	}

	private void sendUpdate(final IBeanDtosUpdate update) {
		final List<IUpdatableResultCallback<IBeanDtosUpdate, ?>> canceledCallbacks = new ArrayList<IUpdatableResultCallback<IBeanDtosUpdate, ?>>();
		for (final Entry<IUpdatableResultCallback<IBeanDtosUpdate, ?>, IExecutionCallback> entry : updateCallbacks.entrySet()) {
			if (entry.getValue().isCanceled()) {
				canceledCallbacks.add(entry.getKey());
			}
			else {
				entry.getKey().update(update);
			}
		}
		for (final IUpdatableResultCallback<IBeanDtosUpdate, ?> callback : canceledCallbacks) {
			updateCallbacks.remove(callback);
		}
	}

	private void addMatches(final List<Match> matchesToAdd) {
		LOGGER.debug("Adding matches");

		for (final Match match : matchesToAdd) {
			matches.put(match.getId(), match);
		}

		final List<IBeanDto> beanDtos = toBeanDtos(new ArrayList<IBean>(matchesToAdd), null);
		sendUpdate(new BeanDtosInsertionUpdate(beanDtos));
	}

	private void updateMatch(final Match matchToUpdate, final int minutesPassed, final boolean teamAScored) {
		LOGGER.debug("updating " + matchToUpdate.getTitle() + ": +" + minutesPassed + "mins, teamA? " + teamAScored);

		final int matchLength = 90;

		final Match updatedMatch = new Match(matchToUpdate);

		if (matchToUpdate.getMinute() + minutesPassed > matchLength) {
			updatedMatch.setMinute(matchLength);
			updatedMatch.setFinished(true);
		}
		else {
			updatedMatch.setMinute(matchToUpdate.getMinute() + minutesPassed);

			if (teamAScored) {
				updatedMatch.setScoreA(matchToUpdate.getScoreA() + 1);
			}
			else {
				updatedMatch.setScoreB(matchToUpdate.getScoreB() + 1);
			}
		}

		matches.put(matchToUpdate.getId(), updatedMatch);
		sendUpdate(new BeanDtosChangeUpdate(toBeanDtos(new ArrayList<IBean>(Collections.singletonList(updatedMatch)), null)));
	}

	private void clearMatches() {
		LOGGER.debug("Clearing matches");
		matches.clear();
		sendUpdate(new IBeanDtosClearUpdate() {});
	}

	private class MatchGenerator implements Runnable {

		private static final int TIME_BETWEEN_UPDATES_IN_MS = 1000;
		private static final int MAX_NUMBER_OF_MATCHES = 20;

		private final Random rng = new Random();

		@Override
		public void run() {
			while (!stopped.get()) {

				if (checkAllMatchesFinished()) {
					clearMatches();
				}
				else if (matches.isEmpty() || matches.size() < MAX_NUMBER_OF_MATCHES && rng.nextDouble() < 0.5d) {
					createMatches(Math.min(3, MAX_NUMBER_OF_MATCHES - matches.size()));
				}
				else {
					updateAMatch();
				}

				try {
					Thread.sleep(TIME_BETWEEN_UPDATES_IN_MS);
				}
				catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		}

		private void createMatches(final int upTo) {
			final int n = rng.nextInt(upTo) + 1;
			final List<Match> newMatches = new ArrayList<Match>(n);

			for (int i = 0; i < n; i++) {
				final int matchNumber = matches.size() + i + 1;
				newMatches.add(new Match(Integer.valueOf(matchNumber), "Match " + matchNumber));
			}

			addMatches(newMatches);
		}

		private void updateAMatch() {
			final List<Match> openMatches = new ArrayList<Match>(matches.size());
			for (final Match match : matches.values()) {
				if (!match.getFinished()) {
					openMatches.add(match);
				}
			}

			final Match matchToUpdate = openMatches.get(rng.nextInt(openMatches.size()));

			updateMatch(matchToUpdate, someMinutesPassed(), rng.nextBoolean());
		}

		private int someMinutesPassed() {
			return rng.nextInt(55) + 5;
		}

		private boolean checkAllMatchesFinished() {
			boolean result = true;

			if (matches.size() < MAX_NUMBER_OF_MATCHES) {
				result = false;
			}
			else {
				for (final IMatch match : matches.values()) {
					if (!match.getFinished()) {
						result = false;
						break;
					}
				}
			}
			return result;
		}
	}

}
