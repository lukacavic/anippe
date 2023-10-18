package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.projects.IOverviewService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.TypeCastUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Map;
import java.util.TreeMap;

public class OverviewService extends AbstractService implements IOverviewService {

    @Override
    public Map<String, Integer> fetchLeadsByStatus(Integer projectId) {
        Map<String, Integer> map = new TreeMap<>();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT ls.id, ");
        varname1.append("       ls.name, ");
        varname1.append("       l.total_leads ");
        varname1.append("FROM   lead_statuses ls ");
        varname1.append("       LEFT OUTER JOIN (SELECT status_id, ");
        varname1.append("                               Count(0) AS total_leads ");
        varname1.append("                        FROM   leads ");
        varname1.append("                        WHERE  deleted_at IS NULL ");
        varname1.append("                               AND lost IS FALSE ");
        varname1.append("                        GROUP  BY status_id) l ");
        varname1.append("                    ON l.status_id = ls.id ");
        varname1.append("WHERE  ls.deleted_at IS NULL ");
        varname1.append("       AND ls.organisation_id = :organisationId ");
        varname1.append("GROUP  BY ls.id, ");
        varname1.append("          ls.name, ");
        varname1.append("          l.total_leads");
        Object[][] resultSet = SQL.select(varname1.toString(), new NVPair("organisationId", getCurrentOrganisationId()));

        for (Object[] object : resultSet) {
            String statusName = TypeCastUtility.castValue(object[1], String.class);
            Integer totalLeads = TypeCastUtility.castValue(object[2], Integer.class);

            map.put(statusName, totalLeads);
        }

        return map;
    }
}
