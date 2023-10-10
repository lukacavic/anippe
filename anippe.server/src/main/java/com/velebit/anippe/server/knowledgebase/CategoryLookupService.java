package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.knowledgebase.CategoryLookupCall;
import com.velebit.anippe.shared.knowledgebase.ICategoryLookupService;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.List;

public class CategoryLookupService extends AbstractSqlLookupService<Long> implements ICategoryLookupService {
    protected String getSql(ILookupCall<Long> call) {
        CategoryLookupCall c = (CategoryLookupCall) call;

        StringBuffer varname1 = new StringBuffer();
        varname1.append(" SELECT c.id, c.name ");
        varname1.append(" FROM knowledge_categories c  ");
        varname1.append(" WHERE c.organisation_id = " + ServerSession.get().getCurrentOrganisation().getId());
        varname1.append(" AND c.deleted_at IS NULL ");

        if(c.getProjectId() != null) {
            varname1.append(" AND c.project_id = :projectId ");
        }
        varname1.append(" <key>AND c.id = :key</key> ");
        varname1.append(" <text>AND c.name ILIKE '%' || :text || '%'</text> ");
        varname1.append(" <all></all>");
        varname1.append(" ORDER BY c.id, c.name ");

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
