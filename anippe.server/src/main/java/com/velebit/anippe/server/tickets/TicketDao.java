package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.sequence.SequenceGenerator;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.constants.Constants.SequenceFormat;
import com.velebit.anippe.shared.constants.Constants.SequenceType;
import com.velebit.anippe.shared.constants.Constants.TicketStatus;
import com.velebit.anippe.shared.sequence.ISequenceService;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketRequest;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
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

        if (request.getUserId() != null) {
            varname1.append(" AND t.assigned_user_id = :{request.userId} ");
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

        List<TicketDto> dtos = CollectionUtility.arrayList(dto.getBeans());

        List<Ticket> tickets = CollectionUtility.emptyArrayList();

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new TicketMap());
        dtos.forEach(item -> tickets.add(mapper.map(item, Ticket.class)));

        return tickets;
    }

    public void changeStatus(Integer ticketId, Integer statusId) {
        SQL.update("UPDATE tickets SET status_id = :statusId, closed_at = null WHERE id = :ticketId", new NVPair("ticketId", ticketId), new NVPair("statusId", statusId));

        if(statusId.equals(TicketStatus.CLOSED)) {
            SQL.update("UPDATE tickets SET closed_at = now() WHERE id = :ticketId", new NVPair("ticketId", ticketId));
        }
    }

    public Integer addReply(Integer ticketId, String reply, Integer userId, Integer contactId, List<BinaryResource> attachments) {
        //Save reply to database
        IntegerHolder replyId = new IntegerHolder();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO ticket_replies ");
        varname1.append("            (ticket_id, ");
        varname1.append("             reply, ");
        varname1.append("             user_id, ");
        varname1.append("             contact_id, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:ticketId, ");
        varname1.append("             :Reply, ");
        varname1.append("             :userId, ");
        varname1.append("             :contactId, ");
        varname1.append("             Now(), ");
        varname1.append("             :organisationId) ");
        varname1.append("RETURNING id INTO :replyId");
        SQL.selectInto(varname1.toString(), new NVPair("ticketId", ticketId),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()),
                new NVPair("contactId", contactId),
                new NVPair("userId", userId), new NVPair("replyId", replyId),
                new NVPair("Reply", reply));

        appendReplyToConversationHistory(ticketId, reply);

        if (!CollectionUtility.isEmpty(attachments)) {
            saveAttachmentsForTicketReply(replyId.getValue(), attachments);
        }

        return replyId.getValue();
    }

    public void updateStatusAndLastReply(Integer ticketId) {
        SQL.update("UPDATE tickets SET status_id = :statusId, last_reply_at = now() WHERE id = :ticketId", new NVPair("statusId", TicketStatus.ANSWERED), new NVPair("ticketId", ticketId));
    }

    public void appendReplyToConversationHistory(Integer ticketId, String reply) {
        SQL.update("UPDATE tickets SET conversation_history = :reply || '</br></br>-------------------------------------<br><br>' || COALESCE(conversation_history, '') WHERE id = :ticketId", new NVPair("ticketId", ticketId), new NVPair("reply", reply));
    }

    public String generateSequence() {
        String prefix = "CS";
        String formatType = SequenceFormat.Format1;
        Integer leadingZeroCount = 6;

        Integer sequence = BEANS.get(ISequenceService.class).getSequence(SequenceType.TICKET, "TICKER", formatType);

        return BEANS.get(SequenceGenerator.class).generate(sequence, prefix, formatType, leadingZeroCount);
    }

    public void saveAttachmentsForTicketReply(Integer replyId, List<BinaryResource> attachments) {
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

    public void deleteReply(Integer ticketReplyId) {
        SQL.update("UPDATE ticket_replies SET deleted_at = now() WHERE id = :ticketReplyId", new NVPair("ticketReplyId", ticketReplyId));
    }

    public void delete(Integer ticketId) {
        SQL.update("UPDATE tickets SET deleted_at = now() WHERE id = :ticketId", new NVPair("ticketId", ticketId));
    }

    public void assignToCurrentUser(Integer ticketId) {
        SQL.update("UPDATE tickets SET assigned_user_id = :assignedUserId WHERE id = :ticketId", new NVPair("ticketId", ticketId), new NVPair("assignedUserId", ServerSession.get().getCurrentUser().getId()));
    }

    public Ticket create(TicketRequest request) {
        IntegerHolder ticketId = new IntegerHolder();

        String code = BEANS.get(TicketDao.class).generateSequence();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO tickets ");
        varname1.append("            (subject, ");
        varname1.append("             contact_id, ");
        varname1.append("             department_id, ");
        varname1.append("             last_reply_at, ");
        varname1.append("             status_id, ");
        varname1.append("             priority_id, ");
        varname1.append("             project_id, ");
        varname1.append("             created_at, ");
        varname1.append("             conversation_history, ");
        varname1.append("             code, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:{request.subject}, ");
        varname1.append("             :{request.contactId}, ");
        varname1.append("             :{request.departmentId}, ");
        varname1.append("             now(), ");
        varname1.append("             :{request.statusId}, ");
        varname1.append("             :{request.priorityId}, ");
        varname1.append("             :{request.projectId}, ");
        varname1.append("             now(), ");
        varname1.append("             :{request.content}, ");
        varname1.append("             :code, ");
        varname1.append("             :organisationId) ");
        varname1.append("RETURNING id INTO :ticketId");
        SQL.selectInto(varname1.toString(),
                new NVPair("request", request),
                new NVPair("ticketId", ticketId),
                new NVPair("code", code),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        //Add first reply
        addReply(ticketId.getValue(), request.getContent(), request.getUserId(), request.getContactId(), request.getAttachments());

        return find(new TicketRequest(ticketId.getValue()));
    }

    public Ticket find(TicketRequest ticketRequest) {
        TicketDto ticketDto = new TicketDto();

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

        if (!StringUtility.isNullOrEmpty(ticketRequest.getCode())) {
            varname1.append(" AND t.code = :{request.code} ");
        }

        if (ticketRequest.getDepartmentId() != null) {
            varname1.append(" AND t.department_id = :{request.departmentId} ");
        }

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
        SQL.selectInto(varname1.toString(),
                new NVPair("holder", ticketDto),
                new NVPair("request", ticketRequest)
        );

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new TicketMap());

        return mapper.map(ticketDto, Ticket.class);
    }

    public String getConversationHistory(Integer ticketId) {
        StringHolder holder = new StringHolder();

        SQL.selectInto("SELECT conversation_history FROM tickets WHERE id = :ticketId INTO :holder", new NVPair("ticketId", ticketId), new NVPair("holder", holder));

        return holder.getValue();
    }
}
