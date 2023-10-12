package com.velebit.anippe.server.tickets;

import com.velebit.anippe.shared.tickets.Ticket;
import org.modelmapper.PropertyMap;

public class TicketMap extends PropertyMap<TicketDto, Ticket> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setCode(source.getCode());
        map().setSubject(source.getSubject());
        map().setCreatedAt(source.getCreatedAt());
        map().setStatusId(source.getStatusId());
        map().setPriorityId(source.getPriorityId());
        map().setLastReply(source.getLastReplyAt());

        map().getContact().setId(source.getContactId());
        map().getContact().setFirstName(source.getContactFirstName());
        map().getContact().setLastName(source.getContactLastName());

        map().getAssignedUser().setId(source.getAssignedUserId());
        map().getAssignedUser().setFirstName(source.getAssignedUserFirstName());
        map().getAssignedUser().setLastName(source.getAssignedUserLastName());
    }
}
