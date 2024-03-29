package com.velebit.anippe.shared.leads;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ILeadsService extends IService {
    LeadsTablePageData getLeadsTableData(SearchFilter filter);

    void delete(Integer leadId);

    void changeStatus(Integer leadId, Long statusId);

    void changeSource(Integer leadId, Long sourceId);
}
