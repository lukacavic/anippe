package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tasks.TasksTablePage.Table;
import com.velebit.anippe.client.tickets.TicketForm;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tasks.ITasksService;
import com.velebit.anippe.shared.tasks.TasksTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(TasksTablePageData.class)
public class TasksTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Check;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Check;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(ITasksService.class).getTasksTableData(filter));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Tasks");
    }
    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            TaskForm form = new TaskForm();
            form.startNew();
            form.waitFor();
            if(form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }
    }
    public class Table extends AbstractTasksTable {

        @Override
        public void reloadData() {
            reloadPage();
        }
    }
}
