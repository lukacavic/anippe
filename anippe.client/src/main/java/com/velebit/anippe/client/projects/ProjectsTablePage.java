package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.projects.ProjectsTablePage.Table;
import com.velebit.anippe.client.settings.users.UserForm;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.IProjectsService;
import com.velebit.anippe.shared.projects.ProjectsTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(ProjectsTablePageData.class)
public class ProjectsTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IProjectsService.class).getProjectsTableData(filter));
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.BoxRemove;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.BoxRemove;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Projects");
    }
    @Order(1000)
    public class AddMenu extends AbstractAddMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewProject");
        }

        @Override
        protected void execAction() {
            ProjectForm form = new ProjectForm();
            form.startNew();
            form.waitFor();
            if(form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }
    }
    public class Table extends AbstractProjectsTable {

        @Override
        public void reloadData() {
            reloadPage();
        }
    }
}
