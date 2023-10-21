package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketRequest;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.Date;
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

        if (request.getContactId() != null) {
            varname1.append(" AND t.contact_id = :{request.contactId} ");
        }

        if (!CollectionUtility.isEmpty(request.getExcludeIds())) {
            varname1.append(" AND t.id != :{request.excludeIds} ");
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
String s =         SQL.createPlainText(varname1.toString(), new NVPair("holder", dto), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("request", request));

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

    public Integer addReply(Integer ticketId, String reply, List<BinaryResource> attachments) {
        //Save reply to database
        IntegerHolder replyId = new IntegerHolder();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO ticket_replies ");
        varname1.append("            (ticket_id, ");
        varname1.append("             reply, ");
        varname1.append("             user_id, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:ticketId, ");
        varname1.append("             :Reply, ");
        varname1.append("             :userId, ");
        varname1.append("             Now(), ");
        varname1.append("             :organisationId) ");
        varname1.append("RETURNING id INTO :replyId");
        SQL.selectInto(varname1.toString(), new NVPair("ticketId", ticketId),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()),
                new NVPair("userId", ServerSession.get().getCurrentUser().getId()), new NVPair("replyId", replyId),
                new NVPair("Reply", reply));

        updateLastReply(ticketId);

        if (!CollectionUtility.isEmpty(attachments)) {
            saveAttachmentsForTicketReply(replyId.getValue(), attachments);
        }

        return replyId.getValue();
    }

    private void saveAttachmentsForTicketReply(Integer replyId, List<BinaryResource> attachments) {
        for (BinaryResource binaryResource : attachments) {
            Attachment attachment = new Attachment();
            attachment.setAttachment((binaryResource).getContent());
            attachment.setCreatedAt(new Date());
            attachment.setFileName(binaryResource.getFilename());
            attachment.setFileExtension(binaryResource.getContentType());
            attachment.setFileSize(binaryResource.getContentLength());
            attachment.setRelatedId(replyId);
            attachment.setRelatedTypeId(Constants.Related.TICKET_REPLY);
            attachment.setName(binaryResource.getFilename());

            BEANS.get(IAttachmentService.class).saveAttachment(attachment);
        }
    }

    public void updateLastReply(Integer ticketId) {
        SQL.update("UPDATE tickets SET last_reply_at = now() WHERE id = :ticketId", new NVPair("ticketId", ticketId));
    }

    public void deleteReply(Integer ticketReplyId) {
        SQL.update("UPDATE ticket_replies SET deleted_at = now() WHERE id = :ticketReplyId", new NVPair("ticketReplyId", ticketReplyId));
    }

    public void delete(Integer ticketId) {
        SQL.update("UPDATE tickets SET deleted_at = now() WHERE id = :ticketId", new NVPair("ticketId", ticketId));
    }
}
