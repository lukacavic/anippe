package com.velebit.anippe.shared.settings.sharedgroups;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IClientGroupsService extends IService {
    ClientGroupsTablePageData getClientGroupsTableData(SearchFilter filter);

    void delete(Integer clientGroupId);
}
