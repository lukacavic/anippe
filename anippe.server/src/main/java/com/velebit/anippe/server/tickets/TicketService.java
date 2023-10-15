package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.tasks.TaskDao;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData.TasksTable.TasksTableRowData;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import com.velebit.anippe.shared.tickets.*;
import com.velebit.anippe.shared.tickets.TicketFormData.NotesTable.NotesTableRowData;
import com.velebit.anippe.shared.tickets.TicketFormData.OtherTicketsTable.OtherTicketsTableRowData;
import com.velebit.anippe.shared.tickets.TicketFormData.RepliesTable.RepliesTableRowData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.platform.util.ChangeStatus;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.List;

public class TicketService extends AbstractService implements ITicketService {
    @Override
    public TicketFormData prepareCreate(TicketFormData formData) {

        return formData;
    }

    @Override
    public TicketFormData create(TicketFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO tickets ");
        varname1.append("            (subject, ");
        varname1.append("             contact_id, ");
        varname1.append("             status_id, ");
        varname1.append("             priority_id, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Subject, ");
        varname1.append("             :Contact, ");
        varname1.append("             :statusId, ");
        varname1.append("             :Priority, ");
        varname1.append("             now(), ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :ticketId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("statusId", Constants.TicketStatus.CREATED));
        return formData;
    }

    @Override
    public TicketFormData load(TicketFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT t.subject, ");
        varname1.append("       t.priority_id, ");
        varname1.append("       t.status_id, ");
        varname1.append("       t.contact_id, ");
        varname1.append("       t.code, ");
        varname1.append("       t.project_id, ");
        varname1.append("       t.project_id, ");
        varname1.append("       t.assigned_user_id ");
        varname1.append("FROM   tickets t ");
        varname1.append("WHERE  t.id = :ticketId ");
        varname1.append("INTO   :Subject, :Priority, :Status, :Contact, :Code, :Project, :projectId, :AssignedTo ");
        SQL.selectInto(varname1.toString(), formData);

        //Fetch private notes for ticket
        List<NotesTableRowData> noteRows = fetchNotes(formData.getTicketId());
        formData.getNotesTable().setRows(noteRows.toArray(new NotesTableRowData[0]));

        //Fetch replies for ticket
        List<RepliesTableRowData> replyRows = fetchReplies(formData.getTicketId());
        formData.getRepliesTable().setRows(replyRows.toArray(new RepliesTableRowData[0]));

        //Fetch related tickets
        List<OtherTicketsTableRowData> otherTicketsRows = fetchOtherTicketRows(formData.getContact().getValue(), formData.getTicketId());
        formData.getOtherTicketsTable().setRows(otherTicketsRows.toArray(new OtherTicketsTableRowData[0]));

        //Fetch tasks
        List<TasksTableRowData> taskRows = fetchTasks(formData.getTicketId());
        formData.getTasksBox().getTasksTable().setRows(taskRows.toArray(new TasksTableRowData[0]));

        return formData;
    }

    private List<OtherTicketsTableRowData> fetchOtherTicketRows(Long contactId, Integer ticketId) {
        TicketRequest request = new TicketRequest();
        request.setContactId(contactId.intValue());
        request.setExcludeIds(CollectionUtility.arrayList(ticketId));

        List<Ticket> tickets = BEANS.get(TicketDao.class).get(request);

        List<OtherTicketsTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(tickets)) return rows;

        for (Ticket ticket : tickets) {
            OtherTicketsTableRowData row = new OtherTicketsTableRowData();
            row.setTicket(ticket);
            row.setSubject(ticket.getSubject());
            row.setCreatedAt(ticket.getCreatedAt());
            row.setContact(ticket.getContact() != null ? ticket.getContact().getFullName() : null);
            row.setPriority(ticket.getPriorityId());
            row.setLastReply(ticket.getLastReply());
            row.setAssignedUser(ticket.getAssignedUser().getFullName());
            row.setCode(ticket.getCode());
            row.setStatus(ticket.getStatusId());

            rows.add(row);
        }
        return rows;
    }

    @Override
    public TicketFormData store(TicketFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE tickets ");
        varname1.append("SET assigned_user_id = :AssignedTo, ");
        varname1.append("    subject          = :Subject, ");
        varname1.append("    priority_id      = :Priority ");
        varname1.append("WHERE id = :ticketId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }

    @Override
    public String fetchPredefinedReplyContent(Long predefinedReplyId) {
        StringHolder holder = new StringHolder();

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT content ");
        varname1.append("FROM   predefined_replies ");
        varname1.append("WHERE  id = :predefinedReplyId ");
        varname1.append("INTO   :holder");
        SQL.selectInto(varname1.toString(), new NVPair("predefinedReplyId", predefinedReplyId), new NVPair("holder", holder));

        return holder.getValue();
    }

    @Override
    public List<NotesTableRowData> fetchNotes(Integer ticketId) {
        BeanArrayHolder<NotesTableRowData> holder = new BeanArrayHolder<>(NotesTableRowData.class);

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT   tn.id, ");
        varname1.append("         tn.note, ");
        varname1.append("         tn.user_id, ");
        varname1.append("         u.first_name ");
        varname1.append("                  || ' ' ");
        varname1.append("                  || u.last_name, ");
        varname1.append("         tn.created_at ");
        varname1.append("FROM     ticket_notes tn, ");
        varname1.append("         users u ");
        varname1.append("WHERE    tn.user_id = u.id ");
        varname1.append("AND      tn.deleted_at IS NULL ");
        varname1.append("AND      tn.ticket_id = :ticketId ");
        varname1.append("ORDER BY tn.created_at ");
        varname1.append("into     :{holder.NoteId}, ");
        varname1.append("         :{holder.Note}, ");
        varname1.append("         :{holder.UserId}, ");
        varname1.append("         :{holder.User}, ");
        varname1.append("         :{holder.CreatedAt}");
        SQL.selectInto(varname1.toString(), new NVPair("ticketId", ticketId), new NVPair("holder", holder));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public List<TasksTableRowData> fetchTasks(Integer ticketId) {
        TaskRequest request = new TaskRequest();
        request.setRelatedId(ticketId);
        request.setRelatedType(Related.TICKET);

        List<Task> tasks = BEANS.get(TaskDao.class).get(request);

        List<TasksTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(tasks)) return CollectionUtility.emptyArrayList();

        for (Task task : tasks) {
            TasksTableRowData row = new TasksTableRowData();
            row.setTask(task);
            row.setName(task.getTitle());
            row.setPriority(task.getPriorityId());
            row.setStartAt(task.getStartAt());
            row.setDeadlineAt(task.getDeadlineAt());
            row.setStatus(task.getStatusId());
            rows.add(row);
        }

        return rows;
    }

    @Override
    public void deleteNote(Integer noteId) {
        SQL.update("UPDATE ticket_notes SET deleted_at = now() WHERE id = :noteId", new NVPair("noteId", noteId));
    }

    @Override
    public List<RepliesTableRowData> fetchReplies(Integer ticketId) {
        BeanArrayHolder<RepliesTableRowData> holder = new BeanArrayHolder<>(RepliesTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT          tr.id, ");
        varname1.append("                u.first_name ");
        varname1.append("                                || ' ' ");
        varname1.append("                                || u.last_name, ");
        varname1.append("                u.id, ");
        varname1.append("                c.first_name ");
        varname1.append("                                || ' ' ");
        varname1.append("                                || c.last_name, ");
        varname1.append("                tr.created_at, ");
        varname1.append("                tr.reply ");
        varname1.append("FROM            ticket_replies tr ");
        varname1.append("LEFT OUTER JOIN users u ");
        varname1.append("ON              u.id = tr.user_id ");
        varname1.append("LEFT OUTER JOIN contacts c ");
        varname1.append("ON              c.id = tr.contact_id ");
        varname1.append("WHERE           tr.deleted_at IS NULL ");
        varname1.append("AND             tr.ticket_id = :ticketId ");
        varname1.append("ORDER BY        tr.created_at DESC ");
        varname1.append("into            :{replies.TicketReplyId}, ");
        varname1.append("                :{replies.Sender}, ");
        varname1.append("                :{replies.UserId}, ");
        varname1.append("                :{replies.Contact}, ");
        varname1.append("                :{replies.CreatedAt}, ");
        varname1.append("                :{replies.Reply}");
        SQL.selectInto(varname1.toString(), new NVPair("ticketId", ticketId), new NVPair("replies", holder));
        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void addReply(TicketFormData formData) {
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
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("userId", getCurrentUserId()), new NVPair("replyId", replyId));

        //Update last reply for ticket
        SQL.update("UPDATE tickets SET last_reply_at = now() WHERE id = :ticketId", new NVPair("ticketId", formData.getTicketId()));
        //Save reply attachments if any

        //Send email to client

        //Change status of ticket if it is set.
        BEANS.get(TicketDao.class).changeStatus(formData.getTicketId(), formData.getChangeStatus().getValue());

        //Emit event that reply has been made to ticket
        TicketReply ticketReply = BEANS.get(TicketReplyDao.class).find(replyId.getValue());

        emitModuleEvent(TicketReply.class, ticketReply, ChangeStatus.INSERTED);
    }

    @Override
    public void deleteReply(Integer ticketReplyId) {
        SQL.update("UPDATE ticket_replies SET deleted_at = now() WHERE id = :ticketReplyId", new NVPair("ticketReplyId", ticketReplyId));
    }

    @Override
    public void changeStatus(Integer ticketId, Integer statusId) {
        BEANS.get(TicketDao.class).changeStatus(ticketId, statusId);

        emitModuleEvent(Ticket.class, new Ticket(), ChangeStatus.UPDATED);
    }
}
