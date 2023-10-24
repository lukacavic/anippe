package com.velebit.anippe.shared.contacts;

import com.velebit.anippe.shared.AbstractRequest;

public class ContactRequest extends AbstractRequest {

    private String firstName;
    private String lastName;
    private Integer clientId;
    private String email;

    public ContactRequest(Integer contactId) {
        super();
        this.setId(contactId);
    }

    public ContactRequest() {
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
