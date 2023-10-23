package com.velebit.anippe.server.tickets;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.tickets.ITicketDepartmentLookupService;
import com.velebit.anippe.shared.tickets.PredefinedReplyLookupCall;
import com.velebit.anippe.shared.tickets.TicketDepartmentLookupCall;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.List;

public class TicketDepartmentLookupService extends AbstractSqlLookupService<Long> implements ITicketDepartmentLookupService {
    protected String getSql(ILookupCall<Long> call) {
        TicketDepartmentLookupCall c = (TicketDepartmentLookupCall) call;

        StringBuffer varname1 = new StringBuffer();
        varname1.append(" SELECT td.id, td.name ");
        varname1.append(" FROM ticket_departments td  ");
        varname1.append(" WHERE td.organisation_id = " + ServerSession.get().getCurrentOrganisation().getId());
        varname1.append(" AND td.deleted_at IS NULL ");
        varname1.append(" AND td.active IS TRUE ");

        if (c.getProjectId() != null) {
            varname1.append(" AND td.project_id = :projectId ");
        }

        varname1.append(" <key>AND td.id = :key</key> ");
        varname1.append(" <text>AND td.name ILIKE '%' || :text || '%' </text> ");
        varname1.append(" <all></all>");
        varname1.append(" ORDER BY td.name ");

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
