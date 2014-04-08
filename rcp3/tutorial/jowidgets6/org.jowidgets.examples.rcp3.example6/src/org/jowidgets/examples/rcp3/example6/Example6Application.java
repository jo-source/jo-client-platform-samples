/*
 * Copyright (c) 2013, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jowidgets.examples.rcp3.example6;

import java.util.Date;
import java.util.Random;

import org.jowidgets.api.model.table.ITableModel;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IFrame;
import org.jowidgets.api.widgets.blueprint.IFrameBluePrint;
import org.jowidgets.common.application.IApplication;
import org.jowidgets.common.application.IApplicationLifecycle;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.tools.widgets.blueprint.BPF;

public final class Example6Application implements IApplication {

	private static final Random RANDOM = new Random();
	private static final long AGE_VARIANZ = 1000 * 60 * 60 * 24 * 365 * 50;

	@Override
	public void start(final IApplicationLifecycle lifecycle) {

		final IFrameBluePrint frameBp = BPF.frame();
		frameBp.setSize(new Dimension(800, 600)).setTitle("Hello World");
		final IFrame frame = Toolkit.createRootFrame(frameBp, lifecycle);
		frame.setLayout(new MigLayoutDescriptor("0[grow, 0::]0", "0[grow, 0::]0"));

		//final ITableModel tableModel = createTableModel();
		//TODO add table here

		frame.setVisible(true);
	}

	@SuppressWarnings("unused")
	private static ITableModel createTableModel() {
		final BeanTableModel<Person> result = new BeanTableModel<>(new PersonTableRenderer());

		final Person person1 = new Person();
		person1.setName("Ben Ebelt");
		person1.setGender(Gender.MALE);
		person1.setDayOfBirth(createDate());
		result.addBean(person1);

		final Person person2 = new Person();
		person2.setName("Dieter Rasse");
		person2.setGender(Gender.MALE);
		person2.setDayOfBirth(createDate());
		result.addBean(person2);

		final Person person3 = new Person();
		person3.setName("Klara Fall");
		person3.setGender(Gender.FEMALE);
		person3.setDayOfBirth(createDate());
		result.addBean(person3);

		return result;
	}

	private static Date createDate() {
		return new Date((long) (System.currentTimeMillis() - (RANDOM.nextDouble() * AGE_VARIANZ)));
	}
}
