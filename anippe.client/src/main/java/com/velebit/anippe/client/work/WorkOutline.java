package com.velebit.anippe.client.work;

import com.velebit.anippe.client.calendar.CalendarNodePage;
import com.velebit.anippe.client.clients.ClientsTablePage;
import com.velebit.anippe.client.contacts.ContactsTablePage;
import com.velebit.anippe.client.leads.LeadsTablePage;
import com.velebit.anippe.client.projects.ProjectsTablePage;
import com.velebit.anippe.client.tickets.TicketsTablePage;
import com.velebit.anippe.client.utilities.UtilitiesNodePage;
import com.velebit.anippe.shared.Icons;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

/**
 * @author lukacavic
 */
@Order(1000)
public class WorkOutline extends AbstractOutline {

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);
        pageList.add(new ClientsTablePage());
        pageList.add(new ContactsTablePage());
        pageList.add(new LeadsTablePage());
        pageList.add(new CalendarNodePage());
        pageList.add(new TasksNodePage());
        pageList.add(new ProjectsTablePage());
        pageList.add(new TicketsTablePage());
        pageList.add(new UtilitiesNodePage());
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
