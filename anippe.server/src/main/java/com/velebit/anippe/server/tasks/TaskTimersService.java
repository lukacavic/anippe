package com.velebit.anippe.server.tasks;

import com.velebit.anippe.shared.tasks.ITaskTimersService;
import com.velebit.anippe.shared.tasks.TaskTimersFormData;
import com.velebit.anippe.shared.tasks.TaskTimersFormData.TaskTimersTable.TaskTimersTableRowData;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Date;
import java.util.List;

public class TaskTimersService implements ITaskTimersService {
    @Override
    public TaskTimersFormData prepareCreate(TaskTimersFormData formData) {
        return formData;
    }

    @Override
    public TaskTimersFormData create(TaskTimersFormData formData) {
        return formData;
    }

    @Override
    public TaskTimersFormData load(TaskTimersFormData formData) {
        return formData;
    }

    @Override
    public TaskTimersFormData store(TaskTimersFormData formData) {
        return formData;
    }

    @Override
    public List<TaskTimersTableRowData> fetchTimers(Integer taskId) {
        BeanArrayHolder<TaskTimersTableRowData> holder = new BeanArrayHolder<>(TaskTimersTableRowData.class);

        StringBuffer  varname1 = new StringBuffer();
        varname1.append("SELECT t.id, ");
        varname1.append("       t.start_at, ");
        varname1.append("       t.end_at, ");
        varname1.append("       u.id, ");
        varname1.append("       t.note ");
        varname1.append("FROM   task_timers t, ");
        varname1.append("       users u ");
        varname1.append("WHERE  t.task_id = :taskId ");
        varname1.append("AND    t.user_id = u.id ");
        varname1.append("AND    t.deleted_at IS NULL ");
        varname1.append("ORDER BY t.created_at DESC ");
        varname1.append("into   :{holder.TimerId}, ");
        varname1.append("       :{holder.StartAt}, ");
        varname1.append("       :{holder.EndAt}, ");
        varname1.append("       :{holder.User}, ");
        varname1.append("       :{holder.Note}");
        SQL.selectInto(varname1.toString(), new NVPair("taskId", taskId), new NVPair("holder", holder));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void delete(List<Integer> timerIds) {
        SQL.update("UPDATE task_timers SET deleted_at = now() WHERE id = :timerIds", new NVPair("timerIds", timerIds));
    }

    @Override
    public void updateStartTime(Integer timerId, Date value) {
        SQL.update("UPDATE task_timers SET start_at = :startAt WHERE id = :timerId", new NVPair("startAt", value), new NVPair("timerId", timerId));
    }

    @Override
    public void updateEndTime(Integer timerId, Date value) {
        SQL.update("UPDATE task_timers SET end_at = :endAt WHERE id = :timerId", new NVPair("endAt", value), new NVPair("timerId", timerId));

    }

    @Override
    public void addManualEntry(TaskTimersFormData formData) {
        StringBuffer  varname1 = new StringBuffer();
        varname1.append("INSERT INTO task_timers ");
        varname1.append("            (task_id, ");
        varname1.append("             start_at, ");
        varname1.append("             end_at, ");
        varname1.append("             note, ");
        varname1.append("             user_id) ");
        varname1.append("VALUES      (:taskId, ");
        varname1.append("             :StartAt, ");
        varname1.append("             :EndAt, ");
        varname1.append("             :Note, ");
        varname1.append("             :User)");
        SQL.insert(varname1.toString(), formData);
    }
}
