package com.velebit.anippe.shared.tickets;

import com.velebit.anippe.shared.AbstractRequest;

public class TicketRequest extends AbstractRequest {

    private Integer contactId;

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }
}
