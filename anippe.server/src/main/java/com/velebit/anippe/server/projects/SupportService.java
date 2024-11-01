package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.tickets.TicketDao;
import com.velebit.anippe.shared.projects.ISupportService;
import com.velebit.anippe.shared.projects.SupportFormData;
import com.velebit.anippe.shared.projects.SupportFormData.TicketsTable.TicketsTableRowData;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketRequest;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

public class SupportService implements ISupportService {

    @Override
    public List<TicketsTableRowData> fetchTickets(Integer projectId, Integer clientId) {
        TicketRequest request = new TicketRequest();
        request.setProjectId(projectId);
        request.setClientId(clientId);

        List<Ticket> tickets = BEANS.get(TicketDao.class).get(request);

        List<TicketsTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(tickets)) return rows;

        for (Ticket ticket : tickets) {
            TicketsTableRowData row = new TicketsTableRowData();
            row.setTicket(ticket);
            row.setSubject(ticket.getSubject());
            row.setDepartment(ticket.getTicketDepartment());
            row.setCreatedAt(ticket.getCreatedAt());
            row.setContact(ticket.getContact() != null ? ticket.getContact().getFullName() : null);
            row.setPriority(ticket.getPriorityId());
            row.setLastReply(ticket.getLastReply());
            row.setAssignedUser(ticket.getAssignedUser() != null ? ticket.getAssignedUser().getId().longValue() : null);
            row.setCode(ticket.getCode());
            row.setStatus(ticket.getStatusId());

            rows.add(row);
        }

        return rows;
    }

    @Override
    public SupportFormData prepareCreate(SupportFormData formData) {
        List<TicketsTableRowData> rows = fetchTickets(formData.getProjectId(), formData.getClientId());
        formData.getTicketsTable().setRows(rows.toArray(new TicketsTableRowData[0]));

        return formData;
    }

    @Override
    public SupportFormData create(SupportFormData formData) {
        return formData;
    }
}
