package com.velebit.anippe.shared.settings.roles;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IRolesService extends IService {
    RolesTablePageData getRolesTableData(SearchFilter filter);
}
