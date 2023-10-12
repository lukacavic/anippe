package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.tickets.IPredefinedReplyLookupService;
import com.velebit.anippe.shared.tickets.PredefinedReplyLookupCall;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.List;

public class PredefinedReplyLookupService extends AbstractSqlLookupService<Long> implements IPredefinedReplyLookupService {
    protected String getSql(ILookupCall<Long> call) {
        PredefinedReplyLookupCall c = (PredefinedReplyLookupCall) call;

        StringBuffer varname1 = new StringBuffer();
        varname1.append(" SELECT pr.id, pr.title ");
        varname1.append(" FROM predefined_replies pr  ");
        varname1.append(" WHERE pr.organisation_id = " + ServerSession.get().getCurrentOrganisation().getId());
        varname1.append(" AND pr.deleted_at IS NULL ");

        if (c.getProjectId() != null) {
            varname1.append(" AND pr.project_id = :projectId ");
        }

        varname1.append(" <key>AND pr.id = :key</key> ");
        varname1.append(" <text>AND pr.title ILIKE '%' || :text || '%' </text> ");
        varname1.append(" <all></all>");
        varname1.append(" ORDER BY pr.title ");

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
