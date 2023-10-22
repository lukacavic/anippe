package com.velebit.anippe.server.tickets.importer;

import com.velebit.anippe.server.contacts.ContactDao;
import com.velebit.anippe.shared.clients.Contact;
import com.velebit.anippe.shared.tickets.TicketDepartment;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

@Bean
public class EmailImapImportDao {


    public void updateTicketLastReply(Integer ticketId) {
        SQL.update("UPDATE tickets SET last_reply_at = now() WHERE id = :ticketId", new NVPair("ticketId", ticketId));
    }

    public Contact createContact(String email, Integer organisationId) {

        IntegerHolder contactId = new IntegerHolder();
        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO contacts ");
        varname1.append("            (first_name, ");
        varname1.append("             last_name, ");
        varname1.append("             organisation_id, ");
        varname1.append("             active, ");
        varname1.append("             created_at, ");
        varname1.append("             email) ");
        varname1.append("VALUES      (:firstName, ");
        varname1.append("             :lastName, ");
        varname1.append("             :organisationId, ");
        varname1.append("             true, ");
        varname1.append("             Now(), ");
        varname1.append("             :email) ");
        varname1.append("returning id INTO :contactId");
        SQL.selectInto(varname1.toString(), new NVPair("firstName", "Ime"), new NVPair("lastName", "Prezime"), new NVPair("organisationId", organisationId), new NVPair("email", email), new NVPair("contactId", contactId));

        return BEANS.get(ContactDao.class).find(contactId.getValue());
    }

    public Integer insertTicket(String subject, String content, Contact contact) {
        return null;
    }

    public Integer insertTicketReply(TicketDepartment ticketDepartment, Integer ticketId, Contact contact, String reply) {
        IntegerHolder replyId = new IntegerHolder();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO ticket_replies ");
        varname1.append("            (ticket_id, ");
        varname1.append("             reply, ");
        varname1.append("             contact_id, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:ticketId, ");
        varname1.append("             :Reply, ");
        varname1.append("             :contactId, ");
        varname1.append("             Now(), ");
        varname1.append("             :organisationId) ");
        varname1.append("RETURNING id INTO :replyId");
        SQL.selectInto(varname1.toString(), new NVPair("ticketId", ticketId),
                new NVPair("organisationId", ticketDepartment.getOrganisationId()),
                new NVPair("contactId", contact.getId()), new NVPair("replyId", replyId),
                new NVPair("Reply", reply));

        return replyId.getValue();
    }
}
