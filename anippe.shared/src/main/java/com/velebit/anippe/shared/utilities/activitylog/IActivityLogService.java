package com.velebit.anippe.shared.utilities.activitylog;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IActivityLogService extends IService {
    ActivityLogTablePageData getActivityLogTableData(SearchFilter filter);
}
