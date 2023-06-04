package com.velebit.anippe.client.utilities.activitylog;

import com.velebit.anippe.client.utilities.activitylog.ActivityLogTablePage.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.utilities.activitylog.ActivityLogTablePageData;
import com.velebit.anippe.shared.utilities.activitylog.IActivityLogService;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(ActivityLogTablePageData.class)
public class ActivityLogTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IActivityLogService.class).getActivityLogTableData(filter));
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.History;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.History;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ActivityLog");
    }

    public class Table extends AbstractTable {
        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }
    }
}
