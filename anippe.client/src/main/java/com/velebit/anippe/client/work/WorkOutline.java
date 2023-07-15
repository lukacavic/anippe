package com.velebit.anippe.client.work;

import java.util.List;

import com.velebit.anippe.client.calendar.CalendarNodePage;
import com.velebit.anippe.client.clients.ClientsTablePage;
import com.velebit.anippe.client.leads.LeadsTablePage;
import com.velebit.anippe.client.projects.ProjectsTablePage;
import com.velebit.anippe.client.tasks.TasksTablePage;
import com.velebit.anippe.client.tickets.TicketsTablePage;
import com.velebit.anippe.client.utilities.UtilitiesNodePage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import com.velebit.anippe.shared.Icons;

/**
 * @author lukacavic
 */
@Order(1000)
public class WorkOutline extends AbstractOutline {

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);
        //pageList.add(new ClientsTablePage());
        //pageList.add(new LeadsTablePage());
        //pageList.add(new CalendarNodePage());
        pageList.add(new ProjectsTablePage());
        pageList.add(new TasksTablePage());
        //pageList.add(new TicketsTablePage());
        //pageList.add(new UtilitiesNodePage());
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Work");
    }

    @Override
    protected String getConfiguredIconId() {
        return Icons.Pencil;
    }
}
