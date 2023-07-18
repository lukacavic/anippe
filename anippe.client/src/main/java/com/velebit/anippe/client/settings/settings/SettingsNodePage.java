package com.velebit.anippe.client.settings.settings;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

public class SettingsNodePage extends AbstractPageWithNodes {
    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        //pageList.add(new GeneralNodePage());
        //pageList.add(new OrganisationNodePage());
        pageList.add(new EmailNodePage());
        //pageList.add(new MiscNodePage());
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Settings");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Wrench;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Wrench;
    }

    @Override
    protected boolean getConfiguredShowTileOverview() {
        return true;
    }
}
