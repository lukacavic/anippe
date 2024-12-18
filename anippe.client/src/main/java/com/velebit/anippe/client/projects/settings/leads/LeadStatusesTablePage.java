package com.velebit.anippe.client.projects.settings.leads;

import com.velebit.anippe.client.common.columns.AbstractColorColumn;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.projects.settings.leads.LeadStatusesTablePage.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import com.velebit.anippe.shared.projects.settings.leads.ILeadStatusesService;
import com.velebit.anippe.shared.projects.settings.leads.LeadStatusesTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.MouseButton;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(LeadStatusesTablePageData.class)
public class LeadStatusesTablePage extends AbstractPageWithTable<Table> {
    private Project project;

    public LeadStatusesTablePage(Project project) {
        super();
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Lead;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Lead;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(ILeadStatusesService.class).getLeadStatusesTableData(filter, getProject().getId()));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Statuses");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            LeadStatusForm form = new LeadStatusForm();
            form.setProjectId(getProject().getId());
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }
    }

    public class Table extends AbstractTable {
        @Override
        protected void execRowClick(ITableRow row, MouseButton mouseButton) {
            super.execRowClick(row, mouseButton);

            MenuUtility.getMenuByClass(this, DeleteMenu.class).setVisible(getDeleteableColumn().getValue(row));
        }

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
        }

        public DeleteableColumn getDeleteableColumn() {
            return getColumnSet().getColumnByClass(DeleteableColumn.class);
        }

        public SortColumn getSortColumn() {
            return getColumnSet().getColumnByClass(SortColumn.class);
        }

        public StatusIdColumn getStatusIdColumn() {
            return getColumnSet().getColumnByClass(StatusIdColumn.class);
        }

        @Order(1000)
        public class EditMenu extends AbstractEditMenu {

            @Override
            protected void execAction() {
                LeadStatusForm form = new LeadStatusForm();
                form.setLeadStatusId(getStatusIdColumn().getSelectedValue());
                form.startModify();
                form.waitFor();
                if (form.isFormStored()) {
                    NotificationHelper.showSaveSuccessNotification();

                    reloadPage();
                }
            }
        }

        @Order(2000)
        public class DeleteMenu extends AbstractDeleteMenu {

            @Override
            protected void execAction() {
                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                    BEANS.get(ILeadStatusesService.class).delete(getStatusIdColumn().getSelectedValue());

                    NotificationHelper.showDeleteSuccessNotification();

                    reloadPage();
                }

            }
        }

        @Order(1000)
        public class StatusIdColumn extends AbstractIDColumn {

        }

        @Order(1500)
        public class ColorColumn extends AbstractColorColumn {

        }

        @Order(2000)
        public class NameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Name");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(2500)
        public class SortColumn extends AbstractIntegerColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Sort");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(3000)
        public class DeleteableColumn extends AbstractBooleanColumn {
            @Override
            public boolean isDisplayable() {
                return false;
            }
        }
    }
}
