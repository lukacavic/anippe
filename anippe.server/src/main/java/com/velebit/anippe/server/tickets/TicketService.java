package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.tickets.ITicketService;
import com.velebit.anippe.shared.tickets.TicketFormData;
import com.velebit.anippe.shared.tickets.TicketFormData.NotesTable.NotesTableRowData;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.List;

public class TicketService implements ITicketService {
    @Override
    public TicketFormData prepareCreate(TicketFormData formData) {
        return formData;
    }

    @Override
    public TicketFormData create(TicketFormData formData) {
        StringBuffer  varname1 = new StringBuffer();
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
        varname1.append("       t.assigned_user_id ");
        varname1.append("FROM   tickets t ");
        varname1.append("WHERE  t.id = :ticketId ");
        varname1.append("INTO   :Subject, :Priority, :Status, :AssignedTo ");
        SQL.selectInto(varname1.toString(), formData);

        //Fetch private notes for ticket
        List<NotesTableRowData> noteRows = fetchNotes(formData.getTicketId());
        formData.getNotesTable().setRows(noteRows.toArray(new NotesTableRowData[0]));

        return formData;
    }

    @Override
    public TicketFormData store(TicketFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
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
    public void deleteNote(Integer noteId) {
        SQL.update("UPDATE ticket_notes SET deleted_at = now() WHERE id = :noteId", new NVPair("noteId", noteId));
    }
}
