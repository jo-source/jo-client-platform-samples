package org.jowidgets.samples.kitchensink.sample2.app.service.bean.genericproperties;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.jowidgets.cap.service.jpa.api.ddl.UpperIndex;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericPropertyValue;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Bean;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Person;

@Entity
@Table
public class PersonGenericPropertyValue extends Bean implements IGenericPropertyValue {

	@Index(name = "IGPV_PROPERTYNAME")
	@Column(nullable = false)
	private String propertyName;

	@Basic
	@UpperIndex(name = "IGPV_VALUE_INDEX")
	@Column(nullable = false)
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name = "IGPV_Person_ID_INDEX")
	@JoinColumn(name = "PERSON_ID", nullable = false)
	@ForeignKey(name = "FK_PGPV_PERSON_ID")
	private Person person;

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(final String propValue) {
		this.value = propValue;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(final Person person) {
		this.person = person;
	}

}
