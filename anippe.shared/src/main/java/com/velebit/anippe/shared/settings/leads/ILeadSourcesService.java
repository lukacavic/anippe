package com.velebit.anippe.shared.settings.leads;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ILeadSourcesService extends IService {
    LeadSourcesTablePageData getLeadSourcesTableData(SearchFilter filter);

    void delete(Integer sourceId);
}
