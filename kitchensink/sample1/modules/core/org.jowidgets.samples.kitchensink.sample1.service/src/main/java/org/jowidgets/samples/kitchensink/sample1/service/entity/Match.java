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

package org.jowidgets.samples.kitchensink.sample1.service.entity;

import org.jowidgets.samples.kitchensink.sample1.common.entity.IMatch;

public class Match extends AbstractSampleBean implements IMatch {

	private String title;

	private Integer scoreA;
	private Integer scoreB;

	private Integer minute;

	private Boolean finished;

	public Match(final Object id, final String title) {
		super(id);

		this.title = title;

		this.scoreA = Integer.valueOf(0);
		this.scoreB = Integer.valueOf(0);
		this.minute = Integer.valueOf(1);

		this.finished = Boolean.FALSE;
	}

	public Match(final Match other) {
		super(other.getId());

		this.title = other.title;

		this.scoreA = other.scoreA;
		this.scoreB = other.scoreB;

		this.finished = other.finished;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public Integer getScoreA() {
		return scoreA;
	}

	public void setScoreA(final Integer scoreA) {
		this.scoreA = scoreA;
	}

	@Override
	public Integer getScoreB() {
		return scoreB;
	}

	public void setScoreB(final Integer scoreB) {
		this.scoreB = scoreB;
	}

	@Override
	public Integer getMinute() {
		return minute;
	}

	public void setMinute(final Integer minute) {
		this.minute = minute;
	}

	@Override
	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(final Boolean finished) {
		this.finished = finished;
	}

}
