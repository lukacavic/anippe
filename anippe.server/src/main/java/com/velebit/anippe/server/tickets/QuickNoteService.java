package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.tickets.IQuickNoteService;
import com.velebit.anippe.shared.tickets.QuickNoteFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class QuickNoteService extends AbstractService implements IQuickNoteService {
    @Override
    public QuickNoteFormData prepareCreate(QuickNoteFormData formData) {
        return formData;
    }

    @Override
    public QuickNoteFormData create(QuickNoteFormData formData) {
        if (formData.getNote().getValue() == null) return formData;

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO ticket_notes ");
        varname1.append("            (note, ");
        varname1.append("             user_id, ");
        varname1.append("             ticket_id, ");
        varname1.append("             created_at) ");
        varname1.append("VALUES      (:Note, ");
        varname1.append("             :userId, ");
        varname1.append("             :ticketId, ");
        varname1.append("             now())");
        SQL.insert(varname1.toString(), formData, new NVPair("userId", getCurrentUserId()));

        return formData;
    }

    @Override
    public QuickNoteFormData load(QuickNoteFormData formData) {
        return formData;
    }

    @Override
    public QuickNoteFormData store(QuickNoteFormData formData) {
        return formData;
    }
}
