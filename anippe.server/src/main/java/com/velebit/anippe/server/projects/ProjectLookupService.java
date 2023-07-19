package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.projects.IProjectLookupService;
import com.velebit.anippe.shared.projects.ProjectLookupCall;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.List;

public class ProjectLookupService extends AbstractSqlLookupService<Long> implements IProjectLookupService {
    protected String getSql(ILookupCall<Long> call) {
        ProjectLookupCall c = (ProjectLookupCall) call;

        StringBuffer varname1 = new StringBuffer();
        varname1.append(" SELECT p.id, p.name ");
        varname1.append(" FROM projects p  ");
        varname1.append(" WHERE p.organisation_id = " + ServerSession.get().getCurrentOrganisation().getId());
        varname1.append(" AND p.deleted_at IS NULL ");
        varname1.append(" <key>AND p.id = :key</key> ");
        varname1.append(" <text>AND p.name ILIKE '%' || :text || '%'</text> ");
        varname1.append(" <all></all>");
        varname1.append(" ORDER BY p.name ");

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
