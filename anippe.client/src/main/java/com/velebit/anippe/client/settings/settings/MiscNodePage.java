package com.velebit.anippe.client.settings.settings;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class MiscNodePage extends AbstractPageWithNodes {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Misc");
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Gear;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Gear;
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }
}
