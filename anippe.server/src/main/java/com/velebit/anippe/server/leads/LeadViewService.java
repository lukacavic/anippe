package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.tasks.TaskDao;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.leads.ILeadViewService;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadViewFormData;
import com.velebit.anippe.shared.leads.LeadViewFormData.ActivityTable.ActivityTableRowData;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData.TasksTable.TasksTableRowData;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.List;

public class LeadViewService extends AbstractService implements ILeadViewService {

    @Override
    public LeadViewFormData load(LeadViewFormData formData) {

       formData.setLead(BEANS.get(LeadDao.class).find(formData.getLeadId()));

        //Load activity log
        List<ActivityTableRowData> activityLof = fetchActivityLog(formData.getLeadId());
        formData.getActivityTable().setRows(activityLof.toArray(new ActivityTableRowData[0]));

        return formData;
    }

    @Override
    public LeadViewFormData store(LeadViewFormData formData) {
        return formData;
    }

    @Override
    public List<ActivityTableRowData> fetchActivityLog(Integer leadId) {
        BeanArrayHolder<ActivityTableRowData> holder = new BeanArrayHolder<>(ActivityTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   al.id, ");
        varname1.append("         al.content, ");
        varname1.append("         u.first_name ");
        varname1.append("                  || ' ' ");
        varname1.append("                  || u.last_name, ");
        varname1.append("         al.created_at ");
        varname1.append("FROM     lead_activity_log al, ");
        varname1.append("         users u ");
        varname1.append("WHERE    al.user_id = u.id ");
        varname1.append("AND      al.deleted_at IS NULL ");
        varname1.append("AND      al.lead_id = :leadId ");
        varname1.append("AND      al.organisation_id = :organisationId ");
        varname1.append("ORDER BY al.created_at DESC LIMIT 30 ");
        varname1.append("into     :{rows.ActivityLogId}, ");
        varname1.append("         :{rows.Content}, ");
        varname1.append("         :{rows.User}, ");
        varname1.append("         :{rows.CreatedAt}");
        SQL.selectInto(varname1.toString(), new NVPair("rows", holder), new NVPair("leadId", leadId), new NVPair("organisationId", getCurrentOrganisationId()));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void markAsLost(Integer leadId, boolean lost) {
        SQL.update("UPDATE leads SET lost = :isLost WHERE id = :leadId", new NVPair("leadId", leadId), new NVPair("isLost", lost));
    }

    @Override
    public Lead find(Integer leadId) {
        return BEANS.get(LeadDao.class).find(leadId);
    }

    @Override
    public void deleteActivityLog(Integer activityLogId) {
        SQL.update("UPDATE lead_activity_log SET deleted_at = now() WHERE id = :activityId", new NVPair("activityId", activityLogId));
    }

    @Override
    public List<TasksTableRowData> fetchTasks(Integer leadId) {
        TaskRequest request = new TaskRequest();
        request.setRelatedId(leadId);
        request.setRelatedType(Related.LEAD);

        List<Task> tasks = BEANS.get(TaskDao.class).get(request);

        List<TasksTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(tasks)) return CollectionUtility.emptyArrayList();

        for (Task task : tasks) {
            TasksTableRowData row = new TasksTableRowData();
            row.setTask(task);
            row.setName(task.getTitle());
            row.setPriority(task.getPriorityId());
            row.setStartAt(task.getStartAt());
            row.setDeadlineAt(task.getDeadlineAt());
            row.setStatus(task.getStatusId());
            rows.add(row);
        }

        return rows;
    }
}
