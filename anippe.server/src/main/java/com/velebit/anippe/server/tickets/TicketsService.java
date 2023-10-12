package com.velebit.anippe.server.tickets;

import com.velebit.anippe.shared.tickets.ITicketsService;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketRequest;
import com.velebit.anippe.shared.tickets.TicketsTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

public class TicketsService implements ITicketsService {
    @Override
    public TicketsTablePageData getTicketsTableData(SearchFilter filter) {
        TicketsTablePageData pageData = new TicketsTablePageData();

        TicketRequest request = new TicketRequest();

        List<Ticket> tickets = BEANS.get(TicketDao.class).get(request);

        if (CollectionUtility.isEmpty(tickets)) return pageData;

        for (Ticket ticket : tickets) {
            TicketsTablePageData.TicketsTableRowData row = pageData.addRow();
            row.setTicket(ticket);
            row.setSubject(ticket.getSubject());
            row.setCreatedAt(ticket.getCreatedAt());
            row.setContact(ticket.getContact() != null ? ticket.getContact().getFullName() : null);
            row.setPriority(ticket.getPriorityId());
            row.setLastReply(ticket.getLastReply());
            row.setAssignedUser(ticket.getAssignedUser().getFullName());
            row.setCode(ticket.getCode());
            row.setStatus(ticket.getStatusId());
        }
        return pageData;
    }

    @Override
    public void delete(Integer ticketId) {
        SQL.update("UPDATE tickets SET deleted_at = now() WHERE id = :ticketId", new NVPair("ticketId", ticketId));
    }
}
