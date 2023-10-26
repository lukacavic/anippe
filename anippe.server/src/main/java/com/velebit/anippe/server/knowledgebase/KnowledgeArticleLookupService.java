package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.knowledgebase.IKnowledgeArticleLookupService;
import com.velebit.anippe.shared.knowledgebase.KnowledgeArticleLookupCall;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.List;

public class KnowledgeArticleLookupService extends AbstractSqlLookupService<Long> implements IKnowledgeArticleLookupService {
    protected String getSql(ILookupCall<Long> call) {
        KnowledgeArticleLookupCall c = (KnowledgeArticleLookupCall) call;

        StringBuffer varname1 = new StringBuffer();
        varname1.append(" SELECT ka.id, ka.title ");
        varname1.append(" FROM knowledge_articles ka  ");
        varname1.append(" WHERE ka.organisation_id = " + ServerSession.get().getCurrentOrganisation().getId());
        varname1.append(" AND ka.deleted_at IS NULL ");

        if (c.getProjectId() != null) {
            varname1.append(" AND ka.project_id = :projectId ");
        }
        varname1.append(" <key>AND ka.id = :key</key> ");
        varname1.append(" <text>AND ka.title ILIKE '%' || :text || '%'</text> ");
        varname1.append(" <all></all>");
        varname1.append(" ORDER BY ka.id, ka.title ");

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
