package com.velebit.anippe.server.utilities.activitylog;

import com.velebit.anippe.shared.utilities.activitylog.ActivityLogTablePageData;
import com.velebit.anippe.shared.utilities.activitylog.IActivityLogService;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class ActivityLogService implements IActivityLogService {
    @Override
    public ActivityLogTablePageData getActivityLogTableData(SearchFilter filter) {
        ActivityLogTablePageData pageData = new ActivityLogTablePageData();
// TODO [lukacavic] fill pageData.
        return pageData;
    }
}
