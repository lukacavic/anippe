package com.velebit.anippe.shared.projects.settings.leads;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ILeadStatusesService extends IService {
    LeadStatusesTablePageData getLeadStatusesTableData(SearchFilter filter, Integer projectId);

    void delete(Integer statusId);
}
