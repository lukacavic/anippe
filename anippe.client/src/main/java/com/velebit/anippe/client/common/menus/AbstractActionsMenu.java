package com.velebit.anippe.client.common.menus;

import com.velebit.anippe.shared.Icons;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class AbstractActionsMenu extends AbstractMenu {
    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Menu;
    }

    @Override
    protected boolean getConfiguredVisible() {
        return false;
    }

    @Override
    protected String getConfiguredText() {
        return TEXTS.get("Actions");
    }
}
