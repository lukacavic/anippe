package com.velebit.anippe.shared.contacts;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "com.velebit.anippe.client.contacts.ContactForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class ContactFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public Active getActive() {
		return getFieldByClass(Active.class);
	}

	/**
	 * access method for property ClientId.
	 */
	public Integer getClientId() {
		return getClientIdProperty().getValue();
	}

	/**
	 * access method for property ClientId.
	 */
	public void setClientId(Integer clientId) {
		getClientIdProperty().setValue(clientId);
	}

	public ClientIdProperty getClientIdProperty() {
		return getPropertyByClass(ClientIdProperty.class);
	}

	/**
	 * access method for property ContactId.
	 */
	public Integer getContactId() {
		return getContactIdProperty().getValue();
	}

	/**
	 * access method for property ContactId.
	 */
	public void setContactId(Integer contactId) {
		getContactIdProperty().setValue(contactId);
	}

	public ContactIdProperty getContactIdProperty() {
		return getPropertyByClass(ContactIdProperty.class);
	}

	public Email getEmail() {
		return getFieldByClass(Email.class);
	}

	public FirstName getFirstName() {
		return getFieldByClass(FirstName.class);
	}

	public LastName getLastName() {
		return getFieldByClass(LastName.class);
	}

	public Phone getPhone() {
		return getFieldByClass(Phone.class);
	}

	public Position getPosition() {
		return getFieldByClass(Position.class);
	}

	public PrimaryContact getPrimaryContact() {
		return getFieldByClass(PrimaryContact.class);
	}

	public static class Active extends AbstractValueFieldData<Boolean> {
		private static final long serialVersionUID = 1L;
	}

	public static class ClientIdProperty extends AbstractPropertyData<Integer> {
		private static final long serialVersionUID = 1L;
	}

	public static class ContactIdProperty extends AbstractPropertyData<Integer> {
		private static final long serialVersionUID = 1L;
	}

	public static class Email extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;
	}

	public static class FirstName extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;
	}

	public static class LastName extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;
	}

	public static class Phone extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;
	}

	public static class Position extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;
	}

	public static class PrimaryContact extends AbstractValueFieldData<Boolean> {
		private static final long serialVersionUID = 1L;
	}
}
