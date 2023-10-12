package com.velebit.anippe.server.tickets;

import com.velebit.anippe.shared.tickets.TicketReply;
import org.eclipse.scout.rt.platform.Bean;

@Bean
public class TicketReplyDao {

    public TicketReply find(Integer ticketReplyId) {
        return new TicketReply();
    }
}
