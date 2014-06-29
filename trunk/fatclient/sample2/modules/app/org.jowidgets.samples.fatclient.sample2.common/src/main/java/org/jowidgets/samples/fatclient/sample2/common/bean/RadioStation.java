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

package org.jowidgets.samples.fatclient.sample2.common.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public final class RadioStation implements Serializable {

	public static final String FREQUENCY_PROPERTY = "frequency";
	public static final String BANDWIDTH_PROPERTY = "bandwidth";
	public static final String BAND_PROPERTY = "band";
	public static final String MODULATION_PROPERTY = "modulation";
	public static final String DISTANCE_PROPERTY = "distance";
	public static final String CATEGORY_PROPERTY = "category";
	public static final String NAME_PROPERTY = "name";

	private static final long serialVersionUID = 4460355581200878674L;

	private HzValue frequency;
	private HzValue bandwidth;
	private Long distance;
	private Band band;
	private Modulation modulation;
	private Category category;
	private String name;

	private RadioStationList radioStationList;

	@NotNull
	public HzValue getFrequency() {
		return frequency;
	}

	public void setFrequency(final HzValue frequency) {
		this.frequency = frequency;
	}

	@NotNull
	public Band getBand() {
		return band;
	}

	public void setBand(final Band band) {
		this.band = band;
	}

	@NotNull
	public HzValue getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(final HzValue bandwidth) {
		this.bandwidth = bandwidth;
	}

	@NotNull
	public Modulation getModulation() {
		return modulation;
	}

	public void setModulation(final Modulation modulation) {
		this.modulation = modulation;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(final Long distance) {
		this.distance = distance;
	}

	@NotNull
	public Category getCategory() {
		return category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public RadioStationList getRadioStationList() {
		return radioStationList;
	}

	public void setRadioStationList(final RadioStationList radioStationList) {
		this.radioStationList = radioStationList;
	}

	@Override
	public String toString() {
		return "RadioStation [frequency="
			+ frequency
			+ ", bandwidth="
			+ bandwidth
			+ ", band="
			+ band
			+ ", modulation="
			+ modulation
			+ ", category="
			+ category
			+ ", name="
			+ name
			+ ", radioStationList="
			+ radioStationList
			+ "]";
	}

}
