package com.velebit.anippe.client.settings;

import com.velebit.anippe.client.settings.clientgroups.ClientGroupsTablePage;
import com.velebit.anippe.client.settings.leads.LeadsNodePage;
import com.velebit.anippe.client.settings.roles.RolesTablePage;
import com.velebit.anippe.client.settings.settings.SettingsNodePage;
import com.velebit.anippe.client.settings.users.UsersTablePage;
import com.velebit.anippe.shared.Icons;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

/**
 * @author lukacavic
 */
@Order(3000)
public class SettingsOutline extends AbstractOutline {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Settings");
    }

    @Override
    protected String getConfiguredIconId() {
        return Icons.Gear;
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        pageList.add(new UsersTablePage());

        pageList.add(new RolesTablePage());
        /*pageList.add(new LeadsNodePage());
        pageList.add(new ClientGroupsTablePage());*/
        pageList.add(new SettingsNodePage());
    }
}
