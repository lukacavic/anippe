package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.leads.ActivityLogFormData;
import com.velebit.anippe.shared.leads.IActivityLogService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class ActivityLogService extends AbstractService implements IActivityLogService {
    @Override
    public ActivityLogFormData prepareCreate(ActivityLogFormData formData) {
        return formData;
    }

    @Override
    public ActivityLogFormData create(ActivityLogFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO lead_activity_log ");
        varname1.append("            (content, ");
        varname1.append("             user_id, ");
        varname1.append("             organisation_id, ");
        varname1.append("             lead_id, ");
        varname1.append("             created_at) ");
        varname1.append("VALUES      (:Content, ");
        varname1.append("             :userId, ");
        varname1.append("             :organisationId, ");
        varname1.append("             :leadId, ");
        varname1.append("             Now())");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("userId", getCurrentUserId()));
        return formData;
    }

    @Override
    public ActivityLogFormData load(ActivityLogFormData formData) {
        SQL.selectInto("SELECT content FROM lead_activity_log WHERE id = :activityId INTO :Content", formData);

        return formData;
    }

    @Override
    public ActivityLogFormData store(ActivityLogFormData formData) {
        SQL.update("UPDATE lead_activity_log SET content = :Content WHERE id = :activityId", formData);

        return formData;
    }
}
