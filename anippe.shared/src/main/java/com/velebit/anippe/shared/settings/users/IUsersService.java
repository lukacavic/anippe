package com.velebit.anippe.shared.settings.users;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IUsersService extends IService {
    UsersTablePageData getUsersTableData(SearchFilter filter);

    void toggleActivated(Integer userId, Boolean value);

    void delete(Integer userId);
}
