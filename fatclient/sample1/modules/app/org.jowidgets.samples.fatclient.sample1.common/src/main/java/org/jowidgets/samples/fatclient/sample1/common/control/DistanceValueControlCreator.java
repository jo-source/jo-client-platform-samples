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

package org.jowidgets.samples.fatclient.sample1.common.control;

import org.jowidgets.api.widgets.IUnitValueField;
import org.jowidgets.api.widgets.blueprint.IUnitValueFieldBluePrint;
import org.jowidgets.common.widgets.factory.ICustomWidgetCreator;
import org.jowidgets.common.widgets.factory.ICustomWidgetFactory;
import org.jowidgets.tools.widgets.blueprint.BPF;
import org.jowidgets.unit.tools.converter.LongDoubleUnitConverter;
import org.jowidgets.unit.tools.units.MillimeterUnitSet;

public final class DistanceValueControlCreator implements ICustomWidgetCreator<IUnitValueField<Long, Double>> {

	@Override
	public IUnitValueField<Long, Double> create(final ICustomWidgetFactory widgetFactory) {
		final IUnitValueFieldBluePrint<Long, Double> bluePrint = BPF.unitValueField(Double.class);
		bluePrint.setUnitSet(MillimeterUnitSet.instance());
		bluePrint.setDefaultUnit(MillimeterUnitSet.CENTI_METER);
		bluePrint.setUnitConverter(new LongDoubleUnitConverter(MillimeterUnitSet.CENTI_METER));
		bluePrint.setUnitComboMinSize(51);
		return widgetFactory.create(bluePrint);
	}

}
