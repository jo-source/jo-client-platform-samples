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

import org.jowidgets.api.convert.IConverter;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.unit.api.IUnit;
import org.jowidgets.unit.api.IUnitValue;
import org.jowidgets.util.Assert;

public final class HzValue implements IUnitValue<Double>, Comparable<HzValue>, Serializable {

	private static final long serialVersionUID = 8735052254044524119L;

	private static final IConverter<Double> CONVERTER = Toolkit.getConverterProvider().doubleNumber();

	private final IUnit unit;
	private final double value;

	public HzValue(final IUnitValue<Double> value) {
		this(value.getValue().doubleValue(), value.getUnit());
	}

	public HzValue(final double value, final IUnit unit) {
		Assert.paramNotNull(unit, "unit");
		this.unit = unit;
		this.value = value;
	}

	@Override
	public IUnit getUnit() {
		return unit;
	}

	@Override
	public Double getValue() {
		return Double.valueOf(value);
	}

	private double getHzValue() {
		return value * unit.getConversionFactor();
	}

	@Override
	public String toString() {
		return CONVERTER.convertToString(value) + " " + unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		final long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HzValue)) {
			return false;
		}
		final HzValue other = (HzValue) obj;
		if (unit != other.unit) {
			return false;
		}
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(final HzValue other) {
		if (getHzValue() < other.getHzValue()) {
			return -1;
		}
		else if (getHzValue() > other.getHzValue()) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
