package com.velebit.anippe.server.projects.settings;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.projects.settings.IPredefinedRepliesService;
import com.velebit.anippe.shared.projects.settings.PredefinedRepliesTablePageData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class PredefinedRepliesService extends AbstractService implements IPredefinedRepliesService {
    @Override
    public PredefinedRepliesTablePageData getPredefinedRepliesTableData(SearchFilter filter, Integer projectId) {
        PredefinedRepliesTablePageData pageData = new PredefinedRepliesTablePageData();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT id, title, content ");
        varname1.append("FROM predefined_replies ");
        varname1.append("WHERE project_id = :projectId ");
        varname1.append("  AND deleted_at IS NULL ");
        varname1.append("  AND organisation_id = :organisationId ");
        varname1.append("  INTO :{pageData.PredefinedReplyId},:{pageData.Title},:{pageData.Content}");
        SQL.selectInto(varname1.toString(), new NVPair("projectId", projectId), new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("pageData", pageData));

        return pageData;
    }

    @Override
    public void delete(Integer predefinedReplyId) {
        SQL.update("UPDATE predefined_replies SET deleted_at = now() WHERE id = :predefinedReplyId", new NVPair("predefinedReplyId", predefinedReplyId));
    }
}
