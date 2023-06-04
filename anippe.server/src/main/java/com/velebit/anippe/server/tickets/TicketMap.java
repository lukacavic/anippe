package com.velebit.anippe.server.tickets;

import com.velebit.anippe.shared.tickets.Ticket;
import org.modelmapper.PropertyMap;

public class TicketMap extends PropertyMap<TicketDto, Ticket> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setSubject(source.getSubject());
        map().setCreatedAt(source.getCreatedAt());
        map().setStatusId(source.getStatusId());
        map().setPriorityId(source.getPriorityId());

        map().getContact().setId(source.getContactId());
        map().getContact().setFirstName(source.getContactFirstName());
        map().getContact().setLastName(source.getContactLastName());
    }
}
