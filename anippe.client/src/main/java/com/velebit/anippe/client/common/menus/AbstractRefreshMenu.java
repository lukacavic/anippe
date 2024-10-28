package com.velebit.anippe.client.common.menus;

import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

import java.util.Set;

public class AbstractRefreshMenu extends AbstractMenu {
    public AbstractRefreshMenu() {
        this(true);
    }

    public AbstractRefreshMenu(boolean callInitializer) {
        super(callInitializer);
    }

    @Override
    protected byte getConfiguredHorizontalAlignment() {
        return 1;
    }

    @Override
    protected String getConfiguredText() {
        return UserAgentUtility.isMobileDevice() ? null : TEXTS.get("Refresh0");
    }

    @Override
    protected boolean getConfiguredStackable() {
        return true;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Spinner1;
    }

    @Override
    protected String getConfiguredKeyStroke() {
        return "R";
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace);
    }
}
