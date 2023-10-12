package com.velebit.anippe.server.contacts;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.contacts.ContactLookupCall;
import com.velebit.anippe.shared.contacts.IContactLookupService;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.List;

public class ContactLookupService extends AbstractSqlLookupService<Long> implements IContactLookupService {
    protected String getSql(ILookupCall<Long> call) {
        ContactLookupCall c = (ContactLookupCall) call;

        StringBuffer varname1 = new StringBuffer();
        varname1.append(" SELECT c.id, c.last_name || ' ' || c.first_name ");
        varname1.append(" FROM contacts c  ");
        varname1.append(" WHERE c.organisation_id = " + ServerSession.get().getCurrentOrganisation().getId());
        varname1.append(" AND c.deleted_at IS NULL ");

        if(c.getClientId() != null) {
            varname1.append(" AND c.client_id = :clientId ");
        }
        varname1.append(" <key>AND c.id = :key</key> ");
        varname1.append(" <text>AND (c.last_name ILIKE '%' || :text || '%' OR c.first_name ILIKE '%' || :text || '%' )</text> ");
        varname1.append(" <all></all>");
        varname1.append(" ORDER BY c.id, c.last_name, c.first_name ");

        return varname1.toString();
    }

    @Override
    public List<ILookupRow<Long>> getDataByAll(ILookupCall<Long> call) {
        return execLoadLookupRows(getSql(call), filterSqlByAll(getSql(call)), call);
    }

    @Override
    public List<ILookupRow<Long>> getDataByText(ILookupCall<Long> call) {
        if (call.getText() != null)
            call.setText(call.getText().substring(0, call.getText().length() - 1));
        return execLoadLookupRows(getSql(call), filterSqlByText(getSql(call)), call);
    }

    @Override
    public List<ILookupRow<Long>> getDataByKey(ILookupCall<Long> call) {
        return execLoadLookupRows(getSql(call), filterSqlByKey(getSql(call)), call);
    }
}
