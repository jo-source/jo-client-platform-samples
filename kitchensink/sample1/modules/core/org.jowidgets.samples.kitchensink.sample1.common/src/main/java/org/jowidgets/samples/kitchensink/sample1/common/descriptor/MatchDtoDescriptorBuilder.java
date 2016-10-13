/*
 * Copyright (c) 2011, grossmann
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

package org.jowidgets.samples.kitchensink.sample1.common.descriptor;

import org.jowidgets.cap.common.api.bean.IBeanPropertyBluePrint;
import org.jowidgets.cap.common.tools.bean.BeanDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample1.common.entity.IMatch;

public class MatchDtoDescriptorBuilder extends BeanDtoDescriptorBuilder {

	public MatchDtoDescriptorBuilder() {
		super(IMatch.class);

		setLabelSingular(Messages.getString("MatchDtoDescriptorBuilder.labelSingular"));
		setLabelPlural(Messages.getString("MatchDtoDescriptorBuilder.labelPlural"));

		IBeanPropertyBluePrint propertyBp;

		propertyBp = addProperty(IMatch.TITLE_PROPERTY);
		propertyBp.setLabel(Messages.getString("MatchDtoDescriptorBuilder.title")); //$NON-NLS-1$
		propertyBp.setDescription(Messages.getString("MatchDtoDescriptorBuilder.title_description")); //$NON-NLS-1$

		propertyBp = addProperty(IMatch.FINISHED_PROPERTY);
		propertyBp.setLabel(Messages.getString("MatchDtoDescriptorBuilder.finished")); //$NON-NLS-1$
		propertyBp.setDescription(Messages.getString("MatchDtoDescriptorBuilder.finished_description")); //$NON-NLS-1$

		propertyBp = addProperty(IMatch.MINUTES_PROPERTY);
		propertyBp.setLabel(Messages.getString("MatchDtoDescriptorBuilder.minutes")); //$NON-NLS-1$
		propertyBp.setDescription(Messages.getString("MatchDtoDescriptorBuilder.minutes_description")); //$NON-NLS-1$

		propertyBp = addProperty(IMatch.SCORE_A_PROPERTY);
		propertyBp.setLabel(Messages.getString("MatchDtoDescriptorBuilder.scoreA")); //$NON-NLS-1$
		propertyBp.setDescription(Messages.getString("MatchDtoDescriptorBuilder.scoreA_description")); //$NON-NLS-1$

		propertyBp = addProperty(IMatch.SCORE_B_PROPERTY);
		propertyBp.setLabel(Messages.getString("MatchDtoDescriptorBuilder.scoreB")); //$NON-NLS-1$
		propertyBp.setDescription(Messages.getString("MatchDtoDescriptorBuilder.scoreB_description")); //$NON-NLS-1$
	}

}
