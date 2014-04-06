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

package org.jowidgets.examples.rcp3.example5;

import java.util.Date;

import org.jowidgets.api.widgets.IComboBox;
import org.jowidgets.api.widgets.IInputField;
import org.jowidgets.api.widgets.blueprint.IInputComponentValidationLabelBluePrint;
import org.jowidgets.api.widgets.blueprint.ITextLabelBluePrint;
import org.jowidgets.api.widgets.content.IInputContentContainer;
import org.jowidgets.api.widgets.content.IInputContentCreator;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.tools.validation.MandatoryValidator;
import org.jowidgets.tools.widgets.blueprint.BPF;
import org.jowidgets.validation.IValidationResult;
import org.jowidgets.validation.ValidationResult;

public final class PersonContentCreator implements IInputContentCreator<Person> {

	private IInputField<String> nameField;
	private IInputField<Date> dateField;
	private IComboBox<PersonType> stateCmb;

	@Override
	public void setValue(final Person person) {
		nameField.setValue(person != null ? person.getName() : null);
		dateField.setValue(person != null ? person.getDayOfBirth() : null);
		stateCmb.setValue(person != null ? person.getType() : null);
	}

	@Override
	public Person getValue() {
		final Person result = new Person();
		result.setName(nameField.getValue());
		result.setType(stateCmb.getValue());
		result.setDayOfBirth(dateField.getValue());
		return result;
	}

	@Override
	public void createContent(final IInputContentContainer content) {
		content.setLayout(new MigLayoutDescriptor("wrap", "[][grow, 0::][20!]", "[][][]"));

		final ITextLabelBluePrint textLabelBp = BPF.textLabel().alignRight();
		final IInputComponentValidationLabelBluePrint validationLabelBp = BPF.inputComponentValidationLabel();
		validationLabelBp.setShowValidationMessage(false);

		//Name field
		content.add(textLabelBp.setText("Name"));
		nameField = content.add(BPF.inputFieldString(), "growx, w 0::");
		content.add(validationLabelBp.setInputComponent(nameField), "growx, w 0::");
		nameField.addValidator(new MandatoryValidator<String>(createMandatoryResult("Name")));

		//Day of birth field
		content.add(textLabelBp.setText("Day of birth"));
		dateField = content.add(BPF.inputFieldDate(), "growx, w 0::");
		content.add(validationLabelBp.setInputComponent(dateField), "growx, w 0::");
		dateField.addValidator(new DateTestValidator());

		//Day of birth field
		content.add(textLabelBp.setText("State"));
		stateCmb = content.add(BPF.comboBoxSelection(PersonType.values()), "growx, w 0::");
		content.add(validationLabelBp.setInputComponent(stateCmb), "growx, w 0::");
	}

	private IValidationResult createMandatoryResult(final String propName) {
		return ValidationResult.infoError("Please enter value for " + propName + "!");
	}
}
