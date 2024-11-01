package com.velebit.anippe.shared.contacts;

import com.velebit.anippe.shared.AbstractRequest;

public class ContactRequest extends AbstractRequest {

    private String name;
    private Integer clientId;
    private String email;

    public ContactRequest(Integer contactId) {
        super();
        this.setId(contactId);
    }

    public ContactRequest() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
