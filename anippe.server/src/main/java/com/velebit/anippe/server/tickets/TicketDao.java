package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketRequest;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.List;

@Bean
public class TicketDao {

    public List<Ticket> get(TicketRequest request) {
        BeanArrayHolder<TicketDto> dto = new BeanArrayHolder<TicketDto>(TicketDto.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   t.id, ");
        varname1.append("         t.code, ");
        varname1.append("         t.subject, ");
        varname1.append("         t.created_at, ");
        varname1.append("         t.status_id, ");
        varname1.append("         t.priority_id, ");
        varname1.append("         c.id, ");
        varname1.append("         c.first_name, ");
        varname1.append("         c.last_name, ");
        varname1.append("         au.id, ");
        varname1.append("         au.first_name, ");
        varname1.append("         au.last_name, ");
        varname1.append("         t.last_reply_at ");
        varname1.append("FROM     tickets t ");
        varname1.append("LEFT OUTER JOIN contacts c ON c.id = t.contact_id ");
        varname1.append("LEFT OUTER JOIN users au ON au.id = t.assigned_user_id ");
        varname1.append("WHERE    t.deleted_at IS NULL ");
        varname1.append("AND      t.organisation_id = :organisationId ");

        if (request.getProjectId() != null) {
            varname1.append(" AND t.project_id = :{request.projectId} ");
        }

        varname1.append("ORDER BY t.created_at ");
        varname1.append("INTO     :{holder.id}, ");
        varname1.append("         :{holder.code}, ");
        varname1.append("         :{holder.subject}, ");
        varname1.append("         :{holder.createdAt}, ");
        varname1.append("         :{holder.statusId}, ");
        varname1.append("         :{holder.priorityId}, ");
        varname1.append("         :{holder.contactId}, ");
        varname1.append("         :{holder.contactFirstName}, ");
        varname1.append("         :{holder.contactLastName}, ");
        varname1.append("         :{holder.assignedUserId}, ");
        varname1.append("         :{holder.assignedUserFirstName}, ");
        varname1.append("         :{holder.assignedUserLastName}, ");
        varname1.append("         :{holder.lastReplyAt} ");
        SQL.selectInto(varname1.toString(), new NVPair("holder", dto), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("request", request));
        List<TicketDto> dtos = CollectionUtility.arrayList(dto.getBeans());

        List<Ticket> tickets = CollectionUtility.emptyArrayList();

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new TicketMap());
        dtos.forEach(item -> tickets.add(mapper.map(item, Ticket.class)));

        return tickets;
    }

    public void changeStatus(Integer ticketId, Integer statusId) {
        SQL.update("UPDATE tickets SET status_id = :statusId WHERE id = :ticketId", new NVPair("ticketId", ticketId), new NVPair("statusId", statusId));
    }
}
