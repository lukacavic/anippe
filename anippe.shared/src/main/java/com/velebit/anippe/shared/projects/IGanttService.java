package com.velebit.anippe.shared.projects;

import com.velebit.anippe.shared.tasks.Task;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.Date;
import java.util.List;

@TunnelToServer
public interface IGanttService extends IService {
    List<Task> fetchTasks(Integer projectId);

    void updateTaskDuration(Integer itemId, Date startAt, Date endAt);
}
