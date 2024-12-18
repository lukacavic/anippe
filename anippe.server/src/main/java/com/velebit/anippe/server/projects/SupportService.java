package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.tickets.TicketDao;
import com.velebit.anippe.shared.constants.Constants.TicketStatus;
import com.velebit.anippe.shared.projects.ISupportService;
import com.velebit.anippe.shared.projects.SupportFormData;
import com.velebit.anippe.shared.projects.SupportFormData.TicketsTable.TicketsTableRowData;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketRequest;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;
import java.util.stream.Collectors;

public class SupportService implements ISupportService {

    private static void addStatusRowsForHierarchy(List<TicketsTableRowData> rows, List<Ticket> tickets) {
        List<Integer> ticketStatusIds = tickets.stream().map(Ticket::getStatusId).collect(Collectors.toList());

        if (ticketStatusIds.contains(TicketStatus.CREATED)) {
            TicketsTableRowData created = new TicketsTableRowData();
            created.setTicket(new Ticket());
            created.setPrimaryID("STATUS_" + TicketStatus.CREATED);
            created.setSubject(TEXTS.get("TicketCreated"));
            rows.add(created);
        }

        if (ticketStatusIds.contains(TicketStatus.IN_PROGRESS)) {
            TicketsTableRowData inProgress = new TicketsTableRowData();
            inProgress.setTicket(new Ticket());
            inProgress.setPrimaryID("STATUS_" + TicketStatus.IN_PROGRESS);
            inProgress.setSubject(TEXTS.get("InProgress"));
            rows.add(inProgress);
        }

        if (ticketStatusIds.contains(TicketStatus.ANSWERED)) {
            TicketsTableRowData answered = new TicketsTableRowData();
            answered.setTicket(new Ticket());
            answered.setPrimaryID("STATUS_" + TicketStatus.ANSWERED);
            answered.setSubject(TEXTS.get("Answered"));
            rows.add(answered);
        }

        if (ticketStatusIds.contains(TicketStatus.ON_HOLD)) {
            TicketsTableRowData onHold = new TicketsTableRowData();
            onHold.setTicket(new Ticket());
            onHold.setPrimaryID("STATUS_" + TicketStatus.ON_HOLD);
            onHold.setSubject(TEXTS.get("OnHold"));
            rows.add(onHold);
        }

        if (ticketStatusIds.contains(TicketStatus.CLOSED)) {
            TicketsTableRowData closed = new TicketsTableRowData();
            closed.setTicket(new Ticket());
            closed.setPrimaryID("STATUS_" + TicketStatus.CLOSED);
            closed.setSubject(TEXTS.get("Closed"));
            rows.add(closed);
        }

    }

    @Override
    public List<TicketsTableRowData> fetchTickets(Integer projectId, Integer clientId) {
        TicketRequest request = new TicketRequest();
        request.setProjectId(projectId);
        request.setClientId(clientId);

        List<Ticket> tickets = BEANS.get(TicketDao.class).get(request);

        List<TicketsTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(tickets)) return rows;

        //Add status rows
        addStatusRowsForHierarchy(rows, tickets);

        for (Ticket ticket : tickets) {

            TicketsTableRowData row = new TicketsTableRowData();
            row.setTicket(ticket);
            row.setPrimaryID(ticket.getId().toString());
            row.setParentID("STATUS_" + ticket.getStatusId());
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
