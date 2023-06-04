package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.clients.AbstractClientCardMenu;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.constants.ColorConstants;
import com.velebit.anippe.shared.constants.ColorConstants.Blue;
import com.velebit.anippe.shared.constants.ColorConstants.Green;
import com.velebit.anippe.shared.constants.ColorConstants.Orange;
import com.velebit.anippe.shared.constants.ColorConstants.Red;
import com.velebit.anippe.shared.constants.Constants.ProjectStatus;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.IProjectService;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.MouseButton;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

public abstract class AbstractProjectsTable extends AbstractTable {
    public abstract void reloadData();

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
        return true;
    }

    public ClientColumn getClientColumn() {
        return getColumnSet().getColumnByClass(ClientColumn.class);
    }

    public ClientIdColumn getClientIdColumn() {
        return getColumnSet().getColumnByClass(ClientIdColumn.class);
    }

    public DeadlineAtColumn getDeadlineAtColumn() {
        return getColumnSet().getColumnByClass(DeadlineAtColumn.class);
    }

    public MembersColumn getMembersColumn() {
        return getColumnSet().getColumnByClass(MembersColumn.class);
    }

    public NameColumn getNameColumn() {
        return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public StartAtColumn getStartAtColumn() {
        return getColumnSet().getColumnByClass(StartAtColumn.class);
    }

    public ProjectColumn getProjectColumn() {
        return getColumnSet().getColumnByClass(ProjectColumn.class);
    }

    public ProjectIdColumn getProjectIdColumn() {
        return getColumnSet().getColumnByClass(ProjectIdColumn.class);
    }

    public StatusColumn getStatusColumn() {
        return getColumnSet().getColumnByClass(StatusColumn.class);
    }

    @Override
    protected void execRowClick(ITableRow row, MouseButton mouseButton) {
        super.execRowClick(row, mouseButton);

        MenuUtility.getMenuByClass(this, ClientCardMenu.class).setVisible(getClientIdColumn().getValue(row) != null);
    }

    @Order(1000)
    public class EditMenu extends AbstractEditMenu {
        @Override
        protected void execAction() {
            ProjectForm form = new ProjectForm();
            form.setProjectId(getProjectIdColumn().getSelectedValue());
            form.startModify();
            form.waitFor();
            if (form.isFormStored()) {
                reloadData();
            }
        }
    }

    @Order(2000)
    public class DeleteMenu extends AbstractDeleteMenu {
        @Override
        protected void execAction() {
            if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                BEANS.get(IProjectService.class).delete(getProjectIdColumn().getSelectedValue());

                NotificationHelper.showDeleteSuccessNotification();

                reloadData();
            }
        }
    }

    @Order(3000)
    public class ClientCardMenu extends AbstractClientCardMenu {

        @Override
        protected void execAction() {
            if (getClientIdColumn().getSelectedValue() == null) return;

            super.setClientId(getClientIdColumn().getSelectedValue());
            super.execAction();
        }
    }

    @Order(0)
    public class ProjectIdColumn extends AbstractIDColumn {

    }

    @Order(1000)
    public class ProjectColumn extends AbstractColumn<Project> {
        @Override
        protected boolean getConfiguredDisplayable() {
            return false;
        }
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
    public class TypeColumn extends AbstractSmartColumn<Integer> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Type");
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
            return ProjectTypeLookupCall.class;
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Override
    protected void execRowAction(ITableRow row) {
        super.execRowAction(row);

        ProjectCardForm form = new ProjectCardForm();
        form.setProjectId(getProjectIdColumn().getValue(row));
        form.startNew();
    }

    @Order(3000)
    public class ClientColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Client");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(3500)
    public class ClientIdColumn extends AbstractIDColumn {

    }

    @Order(4000)
    public class StartAtColumn extends AbstractDateColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("StartAt");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(5000)
    public class DeadlineAtColumn extends AbstractDateColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("DeadlineAt");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(5500)
    public class StatusColumn extends AbstractSmartColumn<Integer> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Status");
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            if (getStatusColumn().getValue(row) == null) return;

            if (getStatusColumn().getValue(row).equals(ProjectStatus.COMPLETED)) {
                cell.setBackgroundColor(Green.Green1);
                cell.setIconId(FontIcons.Check);
            } else if (getStatusColumn().getValue(row).equals(ProjectStatus.HOLD)) {
                cell.setBackgroundColor(Orange.Orange1);
                cell.setIconId(FontIcons.Pause);
            }else if (getStatusColumn().getValue(row).equals(ProjectStatus.CANCELED)) {
                cell.setBackgroundColor(Red.Red1);
                cell.setIconId(FontIcons.UserMinus);
            }else if (getStatusColumn().getValue(row).equals(ProjectStatus.OPEN)) {
                cell.setBackgroundColor(Blue.Blue1);
            }
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
            return ProjectStatusLookupCall.class;
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(6000)
    public class MembersColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Members");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }
}
