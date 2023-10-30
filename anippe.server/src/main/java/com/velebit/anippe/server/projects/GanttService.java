package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.tasks.TaskDao;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.projects.IGanttService;
import com.velebit.anippe.shared.tasks.Task;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Date;
import java.util.List;

public class GanttService extends AbstractService implements IGanttService {

    @Override
    public List<Task> fetchTasks(Integer projectId) {
        BeanArrayHolder<Task> holder = new BeanArrayHolder<>(Task.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT t.id, ");
        varname1.append("       t.NAME, ");
        varname1.append("       t.start_at, ");
        varname1.append("       t.deadline_at ");
        varname1.append("FROM   tasks t ");
        varname1.append("WHERE  t.related_id = :projectId ");
        varname1.append("AND    t.related_type = :relatedProject ");
        varname1.append("AND    t.deleted_at IS NULL ");
        varname1.append("AND    t.start_at IS NOT NULL ");
        varname1.append("AND    t.deadline_at IS NOT NULL ");
        varname1.append("AND    t.organisation_id = :organisationId ");
        varname1.append("into   :{holder.id}, ");
        varname1.append("       :{holder.title}, ");
        varname1.append("       :{holder.startAt}, ");
        varname1.append("       :{holder.deadlineAt}");
        SQL.selectInto(varname1.toString(), new NVPair("holder", holder), new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("projectId", projectId), new NVPair("relatedProject", Related.PROJECT));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void updateTaskDuration(Integer itemId, Date startAt, Date endAt) {
        BEANS.get(TaskDao.class).updateTaskDates(itemId, startAt, endAt);
    }
}
