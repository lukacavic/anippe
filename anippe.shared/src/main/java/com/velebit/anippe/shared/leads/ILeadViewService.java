package com.velebit.anippe.shared.leads;

import com.velebit.anippe.shared.leads.LeadViewFormData.ActivityTable.ActivityTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ILeadViewService extends IService {

    LeadViewFormData load(LeadViewFormData formData);

    LeadViewFormData store(LeadViewFormData formData);

    List<ActivityTableRowData> fetchActivityLog(Integer leadId);
}
