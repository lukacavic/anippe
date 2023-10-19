package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.leads.ILeadStatusLookupService;
import com.velebit.anippe.shared.leads.LeadSourceLookupCall;
import com.velebit.anippe.shared.leads.LeadStatusLookupCall;
import com.velebit.anippe.shared.settings.users.IUserLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.List;

public class LeadStatusLookupService extends AbstractSqlLookupService<Long> implements ILeadStatusLookupService {

    protected String getSql(ILookupCall<Long> call) {
        LeadStatusLookupCall c = (LeadStatusLookupCall) call;

        StringBuffer varname1 = new StringBuffer();
        varname1.append(" SELECT ls.id, ls.name ");
        varname1.append(" FROM lead_statuses ls  ");
        varname1.append(" WHERE ls.organisation_id = " + ServerSession.get().getCurrentOrganisation().getId());
        varname1.append(" AND ls.deleted_at IS NULL ");

        if(c.getProjectId() != null) {
            varname1.append(" AND ls.project_id = :projectId ");
        }

        varname1.append(" <key>AND ls.id = :key</key> ");
        varname1.append(" <text>AND ls.name ILIKE '%' || :text || '%' </text> ");
        varname1.append(" <all></all>");
        varname1.append(" ORDER BY ls.name ");

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
