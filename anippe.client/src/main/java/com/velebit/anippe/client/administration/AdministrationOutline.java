package com.velebit.anippe.client.administration;

import com.velebit.anippe.client.administration.organisations.OrganisationsTablePage;
import com.velebit.anippe.client.leads.LeadsTablePage;
import com.velebit.anippe.client.projects.ProjectsTablePage;
import com.velebit.anippe.client.tasks.TasksTablePage;
import com.velebit.anippe.client.tickets.TicketsTablePage;
import com.velebit.anippe.shared.Icons;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

public class AdministrationOutline extends AbstractOutline {

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        pageList.add(new OrganisationsTablePage());

    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Administration");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Key;
    }
}
