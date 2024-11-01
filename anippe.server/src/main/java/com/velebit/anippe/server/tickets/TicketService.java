package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.constants.Constants.TicketStatus;
import com.velebit.anippe.shared.tickets.ITicketService;
import com.velebit.anippe.shared.tickets.TicketFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

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
        varname1.append("             code, ");
        varname1.append("             project_id, ");
        varname1.append("             status_id, ");
        varname1.append("             priority_id, ");
        varname1.append("             department_id, ");
        varname1.append("             assigned_user_id, ");
        varname1.append("             created_at, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Title, ");
        varname1.append("             :Contact, ");
        varname1.append("             :code, ");
        varname1.append("             :Project, ");
        varname1.append("             :statusCreated, ");
        varname1.append("             :Priority, ");
        varname1.append("             :Department, ");
        varname1.append("             :AssignedTo, ");
        varname1.append("             Now(), ");
        varname1.append("             :organisationId) ");
        varname1.append("returning id INTO :ticketId");
        SQL.selectInto(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("code", "SS"), new NVPair("statusCreated", TicketStatus.CREATED));

        insertTicketReply(formData);

        return formData;
    }

    private void insertTicketReply(TicketFormData formData) {
        //Insert ticket reply
        StringBuffer  varname1 = new StringBuffer();
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
        varname1.append("             :organisationId)");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("userId", getCurrentUserId()));
    }

    @Override
    public TicketFormData load(TicketFormData formData) {
        return formData;
    }

    @Override
    public TicketFormData store(TicketFormData formData) {
        return formData;
    }
}
