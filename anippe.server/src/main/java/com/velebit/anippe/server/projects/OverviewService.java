package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.projects.IOverviewService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
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
        varname1.append("       COALESCE(l.total_leads, 0) ");
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
        varname1.append("       AND ls.project_id = :projectId ");
        varname1.append("GROUP  BY ls.id, ");
        varname1.append("          ls.name, ");
        varname1.append("          l.total_leads");
        Object[][] resultSet = SQL.select(varname1.toString(), new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("projectId", projectId));

        for (Object[] object : resultSet) {
            String statusName = TypeCastUtility.castValue(object[1], String.class);
            Integer totalLeads = TypeCastUtility.castValue(object[2], Integer.class);

            map.put(statusName, totalLeads);
        }

        return map;
    }

    @Override
    public Map<String, Integer> fetchTicketsByStatus(Integer projectId) {
        Map<String, Integer> map = new TreeMap<>();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT Count(*) filter (WHERE status_id = 1) AS created, ");
        varname1.append("       count(*) filter (WHERE status_id = 2) AS in_progress, ");
        varname1.append("       count(*) filter (WHERE status_id = 3) AS answered, ");
        varname1.append("       count(*) filter (WHERE status_id = 4) AS on_hold, ");
        varname1.append("       count(*) filter (WHERE status_id = 5) AS completed ");
        varname1.append("FROM   tickets t ");
        varname1.append("WHERE  t.project_id = :projectId ");
        varname1.append("AND    t.deleted_at IS NULL ");
        Object[][] resultSet = SQL.select(varname1.toString(), new NVPair("projectId", projectId));

        map.put(TEXTS.get("Created"), TypeCastUtility.castValue(resultSet[0][0], Integer.class));
        map.put(TEXTS.get("InProgress"), TypeCastUtility.castValue(resultSet[0][1], Integer.class));
        map.put(TEXTS.get("Answered"), TypeCastUtility.castValue(resultSet[0][2], Integer.class));
        map.put(TEXTS.get("OnHold"), TypeCastUtility.castValue(resultSet[0][3], Integer.class));
        map.put(TEXTS.get("Completed"), TypeCastUtility.castValue(resultSet[0][4], Integer.class));

        return map;
    }

    @Override
    public Map<String, Integer> fetchTicketsByAssignedUser(Integer projectId) {
        Map<String, Integer> map = new TreeMap<>();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT u.id, ");
        varname1.append("       u.first_name ");
        varname1.append("       || ' ' ");
        varname1.append("       || u.last_name, ");
        varname1.append("       Count(t.*) ");
        varname1.append("FROM   users u, ");
        varname1.append("       tickets t ");
        varname1.append("WHERE  t.assigned_user_id = u.id ");
        varname1.append("       AND t.deleted_at IS NULL ");
        varname1.append("       AND u.deleted_at IS NULL ");
        varname1.append("GROUP  BY u.id, ");
        varname1.append("          u.first_name ");
        varname1.append("          || ' ' ");
        varname1.append("          || u.last_name ");
        varname1.append("ORDER  BY u.first_name, ");
        varname1.append("          u.last_name");
        Object[][] resultSet = SQL.select(varname1.toString(), new NVPair("projectId", projectId));

        for (Object[] object : resultSet) {
            String user = TypeCastUtility.castValue(object[1], String.class);
            Integer totalTickets = TypeCastUtility.castValue(object[2], Integer.class);

            map.put(user, totalTickets);
        }

        return map;
    }

    @Override
    public Map<String, Integer> fetchTicketsByMonth(Integer projectId) {
        Map<String, Integer> map = CollectionUtility.emptyHashMap();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT months, ");
        varname1.append("       Count(0) ");
        varname1.append("FROM   Generate_series(1, 12) AS months ");
        varname1.append("       LEFT OUTER JOIN tickets t ");
        varname1.append("                    ON Extract('month' FROM t.created_at) = months ");
        varname1.append("                       AND t.deleted_at IS NULL ");
        varname1.append("                       AND t.project_id = :projectId ");
        varname1.append("GROUP  BY months ");
        varname1.append("ORDER  BY months ASC ");

        Object[][] resultSet = SQL.select(varname1.toString(), new NVPair("projectId", projectId));

        for (Object[] object : resultSet) {
            String month = TypeCastUtility.castValue(object[0], String.class);
            Integer count = TypeCastUtility.castValue(object[1], Integer.class);

            map.put(month, count);
        }

        return new TreeMap<>(map);
    }
}
