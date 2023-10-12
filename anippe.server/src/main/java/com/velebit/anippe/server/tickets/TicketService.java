package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.tickets.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;

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
        return formData;
    }

    @Override
    public TicketFormData store(TicketFormData formData) {
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
}
