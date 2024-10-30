package com.velebit.anippe.shared.leads;

import com.velebit.anippe.shared.leads.LeadFormData.ActivityLogTable.ActivityLogTableRowData;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData.TasksTable.TasksTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ILeadService extends IService {
    LeadFormData prepareCreate(LeadFormData formData);

    LeadFormData create(LeadFormData formData);

    LeadFormData load(LeadFormData formData);

    LeadFormData store(LeadFormData formData);

    void markAsLost(Integer leadId, boolean lost);

    List<TasksTableRowData> fetchTasks(Integer leadId);

    Lead find(Integer leadId);

    List<ActivityLogTableRowData> fetchActivityLog(Integer leadId);

    void deleteActivityLog(Integer activityLog);

    boolean isEmailUnique(String email, Integer leadId);

    boolean isPhoneUnique(String rawValue, Integer integer);
}
