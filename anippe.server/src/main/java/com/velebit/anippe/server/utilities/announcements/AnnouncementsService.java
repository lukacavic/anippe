package com.velebit.anippe.server.utilities.announcements;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.utilities.announcements.AnnouncementsTablePageData;
import com.velebit.anippe.shared.utilities.announcements.IAnnouncementsService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class AnnouncementsService extends AbstractService implements IAnnouncementsService {
    @Override
    public AnnouncementsTablePageData getAnnouncementsTableData(SearchFilter filter) {
        AnnouncementsTablePageData pageData = new AnnouncementsTablePageData();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT a.id, ");
        varname1.append("       a.subject, ");
        varname1.append("       u.first_name ");
        varname1.append("       || ' ' ");
        varname1.append("       || u.last_name, ");
        varname1.append("       a.created_at ");
        varname1.append("FROM   announcements a, ");
        varname1.append("       users u ");
        varname1.append("WHERE  a.user_id = u.id ");
        varname1.append("       AND a.deleted_at IS NULL ");
        varname1.append("       AND u.id = :userId ");
        varname1.append("       AND a.organisation_id = :organisationId ");
        varname1.append("INTO   :AnnouncementId, :Name, :CreatedBy, :CreatedAt");
        SQL.selectInto(varname1.toString(), pageData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("userId", getCurrentUserId()));

        return pageData;
    }

    @Override
    public void delete(Integer announcementId) {
        SQL.update("UPDATE announcements SET deleted_at = now() WHERE id = :announcementId ", new NVPair("announcementId", announcementId));
    }
}
