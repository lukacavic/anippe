package com.velebit.anippe.client.utilities;

import com.velebit.anippe.client.email.EmailQueueTablePage;
import com.velebit.anippe.client.utilities.activitylog.ActivityLogTablePage;
import com.velebit.anippe.client.utilities.announcements.AnnouncementsTablePage;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

public class UtilitiesNodePage extends AbstractPageWithNodes {

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        pageList.add(new AnnouncementsTablePage());
        pageList.add(new ActivityLogTablePage());
        pageList.add(new EmailQueueTablePage());
    }

    @Override
    protected boolean getConfiguredShowTileOverview() {
        return true;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Utilities");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Wrench;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Wrench;
    }
}
