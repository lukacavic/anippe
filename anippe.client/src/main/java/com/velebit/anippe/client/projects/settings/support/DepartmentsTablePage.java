package com.velebit.anippe.client.projects.settings.support;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.projects.settings.support.DepartmentsTablePage.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import com.velebit.anippe.shared.projects.settings.support.DepartmentsTablePageData;
import com.velebit.anippe.shared.projects.settings.support.IDepartmentService;
import com.velebit.anippe.shared.projects.settings.support.IDepartmentsService;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(DepartmentsTablePageData.class)
public class DepartmentsTablePage extends AbstractPageWithTable<Table> {

    private Project project;

    public DepartmentsTablePage(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Info;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Info;
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IDepartmentsService.class).getDepartmentsTableData(filter, getProject().getId()));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Departments");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            DepartmentForm form = new DepartmentForm();
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }
    }

    public class Table extends AbstractTable {

        @Order(1000)
        public class EditMenu extends AbstractEditMenu {

            @Override
            protected void execAction() {
                DepartmentForm form = new DepartmentForm();
                form.setDepartmentId(getDepartmentIdColumn().getSelectedValue());
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
                    BEANS.get(IDepartmentService.class).delete(getDepartmentIdColumn().getSelectedValue());
                    NotificationHelper.showDeleteSuccessNotification();

                    reloadPage();
                }
            }
        }

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        public ActiveColumn getActiveColumn() {
            return getColumnSet().getColumnByClass(ActiveColumn.class);
        }

        public DepartmentIdColumn getDepartmentIdColumn() {
            return getColumnSet().getColumnByClass(DepartmentIdColumn.class);
        }

        public EmailImapEnabledColumn getEmailImapEnabledColumn() {
            return getColumnSet().getColumnByClass(EmailImapEnabledColumn.class);
        }

        public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
        }

        @Order(1000)
        public class DepartmentIdColumn extends AbstractIDColumn {

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

        @Order(3000)
        public class ActiveColumn extends AbstractBooleanColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Active");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(4000)
        public class EmailImapEnabledColumn extends AbstractBooleanColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("EmailImapEnabled");
            }

            @Override
            protected int getConfiguredWidth() {
                return 150;
            }
        }
    }
}
