package com.velebit.anippe.shared.tasks;

import com.velebit.anippe.shared.tasks.TaskTimersFormData.TaskTimersTable.TaskTimersTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.Date;
import java.util.List;

@TunnelToServer
public interface ITaskTimersService extends IService {
    TaskTimersFormData prepareCreate(TaskTimersFormData formData);

    TaskTimersFormData create(TaskTimersFormData formData);

    TaskTimersFormData load(TaskTimersFormData formData);

    TaskTimersFormData store(TaskTimersFormData formData);

    List<TaskTimersTableRowData> fetchTimers(Integer taskId);

    void delete(List<Integer> timerIds);

    void updateStartTime(Integer timerId, Date value);

    void updateEndTime(Integer timerId, Date value);

    void addManualEntry(TaskTimersFormData formData);
}
