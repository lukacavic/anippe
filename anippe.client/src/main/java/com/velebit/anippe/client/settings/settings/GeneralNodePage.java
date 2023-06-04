package com.velebit.anippe.client.settings.settings;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class GeneralNodePage extends AbstractPageWithNodes {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("General");
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Wrench;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Wrench;
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }


}
