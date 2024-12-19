package com.velebit.anippe.server.settings.users;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.settings.users.IUserLookupService;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.List;

public class UserLookupService extends AbstractSqlLookupService<Long> implements IUserLookupService {

    protected String getSql(ILookupCall<Long> call) {
        UserLookupCall c = (UserLookupCall) call;

        StringBuffer varname1 = new StringBuffer();
        varname1.append(" SELECT u.id, u.first_name || ' ' || u.last_name ");
        varname1.append(" FROM users u  ");
        varname1.append(" WHERE u.organisation_id = " + ServerSession.get().getCurrentOrganisation().getId());
        varname1.append(" AND u.deleted_at IS NULL ");
        varname1.append(" AND u.active IS true ");

        if(!CollectionUtility.isEmpty(c.getExcludeIds())) {
            varname1.append(" AND u.id != :excludeIds ");
        }

        if(c.getProjectId() != null) {
            varname1.append(" AND u.id IN (SELECT user_id FROM link_project_users WHERE project_id = :projectId) ");
        }

        varname1.append(" <key>AND u.id = :key</key> ");
        varname1.append(" <text>AND (u.last_name ILIKE '%' || :text || '%' OR u.first_name ILIKE '%' || :text || '%' )</text> ");
        varname1.append(" <all></all>");
        varname1.append(" ORDER BY u.id, u.first_name, u.last_name ");

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
