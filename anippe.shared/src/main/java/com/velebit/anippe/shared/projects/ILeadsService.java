package com.velebit.anippe.shared.projects;

import com.velebit.anippe.shared.projects.LeadsFormData.LeadsTable.LeadsTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ILeadsService extends IService {
   List<LeadsTableRowData> fetchLeads(Integer projectId);
}
