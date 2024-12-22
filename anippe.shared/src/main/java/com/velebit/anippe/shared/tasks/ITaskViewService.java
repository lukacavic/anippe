package com.velebit.anippe.shared.tasks;

import com.velebit.anippe.shared.AbstractCheckListGroupBoxData.SubTasksTable.SubTasksTableRowData;
import com.velebit.anippe.shared.tasks.TaskViewFormData.ActivityLogTable.ActivityLogTableRowData;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ITaskViewService extends IService {
    TaskViewFormData prepareCreate(TaskViewFormData formData);

    TaskViewFormData create(TaskViewFormData formData);

    Integer activeTimeId(Integer taskId);

    TaskViewFormData load(TaskViewFormData formData);

    TaskViewFormData store(TaskViewFormData formData);

    void markAsCompleted(Integer taskId, boolean completed);

    Task find(Integer taskId);

    void addComment(Integer taskId, String comment);

    Integer toggleTimer(Integer taskId, Integer activeTimerId);

    void deleteTaskCheckListItem(Integer childTaskId);

    Integer updateTaskCheckListItem(Integer taskId, Integer childTaskId, String content, Long userId);

    void updateTaskCheckListItemAsCompleted(Integer childTaskId, Boolean completed);

    List<ActivityLogTableRowData> fetchTaskActivityLog(Integer taskId, boolean withSystemLog);

    void updateActivityLog(Integer value, String value1);

    void deleteActivityLog(Integer activityLogId);

    void deleteTask(Integer taskId);

    void archiveTask(Integer taskId, boolean b);

    void assignToMe(Integer taskId);

    void addAttachments(Integer taskId, List<BinaryResource> items);

    void changeStatus(Integer taskId, Integer statusId);

    void deleteCheckList(Integer checkListId);

    List<SubTasksTableRowData> fetchCheckListItems(Integer checkListId);

    void followTask(Integer taskId, boolean follow);
}
