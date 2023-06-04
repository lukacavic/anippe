package com.velebit.anippe.shared.contacts;

import com.velebit.anippe.shared.AbstractRequest;

public class ContactRequest extends AbstractRequest {

    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
