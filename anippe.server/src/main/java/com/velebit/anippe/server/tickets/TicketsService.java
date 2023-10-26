package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.tickets.ITicketsService;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketRequest;
import com.velebit.anippe.shared.tickets.TicketsTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;
import java.util.Optional;

public class TicketsService extends AbstractService implements ITicketsService {
    @Override
    public TicketsTablePageData getTicketsTableData(SearchFilter filter) {
        TicketsTablePageData pageData = new TicketsTablePageData();

        TicketRequest request = new TicketRequest();
        request.setUserId(getCurrentUserId());

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
            row.setAssignedUser(Optional.ofNullable(ticket.getAssignedUser()).map(User::getFullName).orElse(null));
            row.setCode(ticket.getCode());
            row.setStatus(ticket.getStatusId());
        }
        return pageData;
    }

    @Override
    public void delete(Integer ticketId) {
        SQL.update("UPDATE tickets SET deleted_at = now() WHERE id = :ticketId", new NVPair("ticketId", ticketId));
    }

    @Override
    public Integer findAssignedTicketsCount() {
        IntegerHolder holder = new IntegerHolder();

        SQL.selectInto("SELECT COUNT(0) FROM tickets WHERE assigned_user_id = :userId AND closed_at IS NULL AND deleted_at IS NULL INTO :holder",
                new NVPair("userId", getCurrentUserId()), new NVPair("holder", holder)
        );

        return holder.getValue();
    }
}
