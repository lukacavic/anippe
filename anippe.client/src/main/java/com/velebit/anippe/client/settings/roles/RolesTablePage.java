package com.velebit.anippe.client.settings.roles;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.settings.roles.RolesTablePage.Table;
import com.velebit.anippe.client.settings.users.UserForm;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.roles.IRolesService;
import com.velebit.anippe.shared.settings.roles.Role;
import com.velebit.anippe.shared.settings.roles.RolesTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(RolesTablePageData.class)
public class RolesTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IRolesService.class).getRolesTableData(filter));
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Key;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Key;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Roles");
    }
    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            RoleForm form = new RoleForm();
            form.startNew();
            form.waitFor();
            if(form.isFormStored()) {
                reloadPage();
            }
        }
    }
    public class Table extends AbstractTable {
        @Order(1000)
        public class EditMenu extends AbstractEditMenu {

            @Override
            protected void execAction() {
                RoleForm form = new RoleForm();
                form.setRoleId(getRoleColumn().getSelectedValue().getId());
                form.startModify();
                form.waitFor();
                if(form.isFormStored()) {
                    reloadPage();
                }
            }
        }

        @Order(1000)
        public class DeleteMenu extends AbstractDeleteMenu {

            @Override
            protected void execAction() {
                if(MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                    //Delete implementation

                    NotificationHelper.showDeleteSuccessNotification();

                    reloadPage();
                }
            }
        }
        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        public CreatedAtColumn getCreatedAtColumn() {
            return getColumnSet().getColumnByClass(CreatedAtColumn.class);
        }

        public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
        }

        public RoleColumn getRoleColumn() {
            return getColumnSet().getColumnByClass(RoleColumn.class);
        }

        public TotalUsersColumn getTotalUsersColumn() {
            return getColumnSet().getColumnByClass(TotalUsersColumn.class);
        }

        @Order(1000)
        public class RoleColumn extends AbstractColumn<Role> {
            @Override
            protected boolean getConfiguredDisplayable() {
                return false;
            }
        }

        @Order(1500)
        public class CreatedAtColumn extends AbstractDateTimeColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("CreatedAt");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
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

        @Order(3000)
        public class TotalUsersColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("TotalUsers");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }
    }
}
