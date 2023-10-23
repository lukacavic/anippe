package com.velebit.anippe.server.projects.settings.support;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.projects.IPredefinedReplyService;
import com.velebit.anippe.shared.projects.PredefinedReplyFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class PredefinedReplyService extends AbstractService implements IPredefinedReplyService {
    @Override
    public PredefinedReplyFormData prepareCreate(PredefinedReplyFormData formData) {
        return formData;
    }

    @Override
    public PredefinedReplyFormData create(PredefinedReplyFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO predefined_replies ");
        varname1.append("            (title, ");
        varname1.append("             content, ");
        varname1.append("             organisation_id, ");
        varname1.append("             created_at, ");
        varname1.append("             project_id) ");
        varname1.append("VALUES      (:Title, ");
        varname1.append("             :Content, ");
        varname1.append("             :organisationId, ");
        varname1.append("             Now(), ");
        varname1.append("             :projectId)");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()));

        return formData;
    }

    @Override
    public PredefinedReplyFormData load(PredefinedReplyFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT title, ");
        varname1.append("       content, ");
        varname1.append("       project_id ");
        varname1.append("FROM   predefined_replies ");
        varname1.append("WHERE  id = :predefinedReplyId ");
        varname1.append("INTO   :Title, :Content, :projectId");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public PredefinedReplyFormData store(PredefinedReplyFormData formData) {

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("UPDATE predefined_replies ");
        varname1.append("SET title      = :Title, ");
        varname1.append("    content    = :Content, ");
        varname1.append("    updated_at = now() ");
        varname1.append("WHERE id = :predefinedReplyId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }
}
