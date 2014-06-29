/*
 * Copyright (c) 2014, Michael
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

package org.jowidgets.samples.fatclient.sample2.common.control;

import org.jowidgets.api.convert.IConverter;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IComboBox;
import org.jowidgets.api.widgets.IComposite;
import org.jowidgets.api.widgets.IInputField;
import org.jowidgets.api.widgets.blueprint.IComboBoxSelectionBluePrint;
import org.jowidgets.api.widgets.blueprint.IInputFieldBluePrint;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.VirtualKey;
import org.jowidgets.common.widgets.ISelectable;
import org.jowidgets.common.widgets.controller.IFocusListener;
import org.jowidgets.common.widgets.controller.IInputListener;
import org.jowidgets.common.widgets.controller.IKeyEvent;
import org.jowidgets.common.widgets.controller.IKeyListener;
import org.jowidgets.common.widgets.controller.IMouseListener;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.samples.fatclient.sample2.common.bean.HzValue;
import org.jowidgets.samples.fatclient.sample2.common.bean.HzValue.HzUnit;
import org.jowidgets.tools.controller.FocusObservable;
import org.jowidgets.tools.controller.KeyAdapter;
import org.jowidgets.tools.widgets.blueprint.BPF;
import org.jowidgets.tools.widgets.wrapper.AbstractInputControl;
import org.jowidgets.validation.IValidationConditionListener;
import org.jowidgets.validation.IValidationResult;
import org.jowidgets.validation.ValidationResult;

public final class HzValueControl extends AbstractInputControl<HzValue> implements ISelectable {

	private final IInputField<Double> valueField;
	private final IComboBox<HzUnit> unitCmb;
	private final FocusObservable focusObservable;

	private boolean lastFocus;

	public HzValueControl(final IComposite composite) {
		super(composite);

		this.focusObservable = new FocusObservable();

		composite.setLayout(new MigLayoutDescriptor("0[grow, 0::]0[]0", "0[grow]0"));

		final IComboBoxSelectionBluePrint<HzUnit> unitCmbBp = BPF.comboBoxSelection(HzUnit.values());
		unitCmbBp.autoCompletionOff().setValue(HzUnit.MHz);
		this.unitCmb = composite.add(unitCmbBp, "growy, sgy hg");

		final IConverter<Double> doubleNumbeConverter = Toolkit.getConverterProvider().doubleNumber();
		final IInputFieldBluePrint<Double> inputFieldBp = BPF.inputField(doubleNumbeConverter).setMaxLength(10);
		this.valueField = composite.add(0, inputFieldBp, "grow, w 0::, sgy hg");

		this.lastFocus = hasFocus();

		final IFocusListener focusListener = new FocusListener();
		valueField.addFocusListener(focusListener);
		unitCmb.addFocusListener(focusListener);

		final IValidationConditionListener validationConditionListener = new ValidationConditionListener();
		valueField.addValidationConditionListener(validationConditionListener);
		unitCmb.addValidationConditionListener(validationConditionListener);

		final IInputListener inputListener = new InputListener();
		valueField.addInputListener(inputListener);
		unitCmb.addInputListener(inputListener);

		valueField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(final IKeyEvent event) {
				if (VirtualKey.H.equals(event.getVirtualKey())) {
					unitCmb.setValue(HzUnit.Hz);
				}
				else if (VirtualKey.K.equals(event.getVirtualKey())) {
					unitCmb.setValue(HzUnit.KHz);
				}
				else if (VirtualKey.M.equals(event.getVirtualKey())) {
					unitCmb.setValue(HzUnit.MHz);
				}
				else if (VirtualKey.G.equals(event.getVirtualKey())) {
					unitCmb.setValue(HzUnit.GHz);
				}
			}
		});
	}

	@Override
	public boolean hasModifications() {
		return valueField.hasModifications() || unitCmb.hasModifications();
	}

	@Override
	public void resetModificationState() {
		valueField.resetModificationState();
		unitCmb.resetModificationState();
	}

	@Override
	public void setEditable(final boolean editable) {
		valueField.setEditable(editable);
		unitCmb.setEditable(editable);
	}

	@Override
	public boolean isEditable() {
		return valueField.isEditable() && unitCmb.isEditable();
	}

	@Override
	public void setValue(final HzValue value) {
		if (value != null) {
			valueField.setValue(Double.valueOf(value.getValue()));
			unitCmb.setValue(value.getUnit());
		}
		else {
			valueField.setValue(null);
			unitCmb.setValue(HzUnit.MHz);
		}
	}

	@Override
	public HzValue getValue() {
		final Double numericValue = valueField.getValue();
		final HzUnit unit = unitCmb.getValue();
		if (numericValue != null && unit != null) {
			return new HzValue(numericValue, unit);
		}
		else {
			return null;
		}
	}

	@Override
	protected IValidationResult createValidationResult() {
		return ValidationResult.ok();
	}

	@Override
	public boolean requestFocus() {
		final boolean result = valueField.requestFocus();
		valueField.selectAll();
		return result;
	}

	@Override
	public void addKeyListener(final IKeyListener listener) {
		valueField.addKeyListener(listener);
		unitCmb.addKeyListener(listener);
	}

	@Override
	public void removeKeyListener(final IKeyListener listener) {
		valueField.removeKeyListener(listener);
		unitCmb.removeKeyListener(listener);
	}

	@Override
	public void addMouseListener(final IMouseListener listener) {
		valueField.addMouseListener(listener);
		unitCmb.addMouseListener(listener);
	}

	@Override
	public void removeMouseListener(final IMouseListener listener) {
		valueField.removeMouseListener(listener);
		unitCmb.removeMouseListener(listener);
	}

	@Override
	public void addFocusListener(final IFocusListener listener) {
		focusObservable.addFocusListener(listener);
	}

	@Override
	public void removeFocusListener(final IFocusListener listener) {
		focusObservable.removeFocusListener(listener);
	}

	@Override
	public void setForegroundColor(final IColorConstant colorValue) {
		valueField.setForegroundColor(colorValue);
		unitCmb.setForegroundColor(colorValue);
	}

	@Override
	public void setBackgroundColor(final IColorConstant colorValue) {
		valueField.setBackgroundColor(colorValue);
		unitCmb.setBackgroundColor(colorValue);
	}

	@Override
	public Dimension getPreferredSize() {
		final Dimension valueSize = valueField.getPreferredSize();
		final Dimension unitSize = unitCmb.getPreferredSize();
		return new Dimension(valueSize.getWidth() + unitSize.getWidth() + 2, unitSize.getHeight());
	}

	@Override
	public boolean hasFocus() {
		return valueField.hasFocus() || unitCmb.hasFocus();
	}

	@Override
	public void select() {
		valueField.select();
	}

	private void focusChanged() {
		final boolean currentFocus = hasFocus();
		if (currentFocus != lastFocus) {
			lastFocus = currentFocus;
			if (currentFocus) {
				focusObservable.focusGained();
			}
			else {
				focusObservable.focusLost();
			}
		}
	}

	private final class FocusListener implements IFocusListener {

		@Override
		public void focusGained() {
			Toolkit.getUiThreadAccess().invokeLater(new Runnable() {
				@Override
				public void run() {
					focusChanged();
				}
			});

		}

		@Override
		public void focusLost() {
			Toolkit.getUiThreadAccess().invokeLater(new Runnable() {
				@Override
				public void run() {
					focusChanged();
				}
			});
		}

	}

	private final class ValidationConditionListener implements IValidationConditionListener {

		@Override
		public void validationConditionsChanged() {
			setValidationCacheDirty();
		}

	}

	private final class InputListener implements IInputListener {

		@Override
		public void inputChanged() {
			fireInputChanged();
		}

	}

}
