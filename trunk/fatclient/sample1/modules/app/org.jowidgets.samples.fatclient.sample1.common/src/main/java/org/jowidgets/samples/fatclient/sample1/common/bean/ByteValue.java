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

package org.jowidgets.samples.fatclient.sample1.common.bean;

import java.io.Serializable;

import org.jowidgets.util.Assert;

public final class ByteValue implements Comparable<ByteValue>, Serializable {

	private static final long serialVersionUID = 8735052254044524119L;

	private static final long KB_MULTIPLIER = 1024;
	private static final long MB_MULTIPLIER = KB_MULTIPLIER * 1024;
	private static final long GB_MULTIPLIER = MB_MULTIPLIER * 1024;
	private static final long TB_MULTIPLIER = GB_MULTIPLIER * 1024;

	private final ByteUnit unit;
	private final int value;

	public enum ByteUnit {
		KB,
		MB,
		GB,
		TB;
	}

	public ByteValue(final int value, final ByteUnit unit) {
		Assert.paramNotNull(unit, "unit");
		this.unit = unit;
		this.value = value;
	}

	public ByteUnit getUnit() {
		return unit;
	}

	public int getValue() {
		return value;
	}

	private long getByteValue() {
		if (ByteUnit.KB.equals(unit)) {
			return value * KB_MULTIPLIER;
		}
		else if (ByteUnit.MB.equals(unit)) {
			return value * MB_MULTIPLIER;
		}
		else if (ByteUnit.GB.equals(unit)) {
			return value * GB_MULTIPLIER;
		}
		else if (ByteUnit.TB.equals(unit)) {
			return value * TB_MULTIPLIER;
		}
		else {
			throw new IllegalStateException("Unit '" + unit + "' is unknown");
		}
	}

	@Override
	public String toString() {
		return value + " " + unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + value;
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
		if (!(obj instanceof ByteValue)) {
			return false;
		}
		final ByteValue other = (ByteValue) obj;
		if (unit != other.unit) {
			return false;
		}
		if (value != other.value) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(final ByteValue other) {
		if (getByteValue() < other.getByteValue()) {
			return -1;
		}
		else if (getByteValue() > other.getByteValue()) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
