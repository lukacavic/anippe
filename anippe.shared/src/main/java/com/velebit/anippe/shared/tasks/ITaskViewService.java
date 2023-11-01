package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ITaskViewService extends IService {
    TaskViewFormData prepareCreate(TaskViewFormData formData);

    TaskViewFormData create(TaskViewFormData formData);

    TaskViewFormData load(TaskViewFormData formData);

    TaskViewFormData store(TaskViewFormData formData);

    void markAsCompleted(Integer taskId, boolean completed);

    Task find(Integer taskId);

    void addComment(Integer taskId, String comment);

    Integer toggleTimer(Integer taskId, Integer activeTimerId);

    void deleteChildTask(Integer childTaskId);

    Integer updateChildTask(Integer taskId, Integer childTaskId, String content);

    void updateCompleted(Integer childTaskId, Boolean completed);
}
