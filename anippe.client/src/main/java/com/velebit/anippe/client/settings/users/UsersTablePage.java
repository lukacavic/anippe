package com.velebit.anippe.client.settings.users;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.settings.users.UsersTablePage.Table;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.users.IUsersService;
import com.velebit.anippe.shared.settings.users.UsersTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Set;

@Data(UsersTablePageData.class)
public class UsersTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Users1;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IUsersService.class).getUsersTableData(filter));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Users");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Users1;
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            UserForm form = new UserForm();
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
                UserForm form = new UserForm();
                form.setUserId(getUserColumn().getSelectedValue().getId());
                form.startModify();
                form.waitFor();
                if(form.isFormStored()) {
                    reloadPage();
                }
            }
        }
        @Order(2000)
        public class DeleteMenu extends AbstractDeleteMenu {

            @Override
            protected void execAction() {
                if(MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                    BEANS.get(IUsersService.class).delete(getUserColumn().getSelectedValue().getId());

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

        public UserColumn getUserColumn() {
            return getColumnSet().getColumnByClass(UserColumn.class);
        }

        public CreatedAtColumn getCreatedAtColumn() {
            return getColumnSet().getColumnByClass(CreatedAtColumn.class);
        }

        public EmailColumn getEmailColumn() {
            return getColumnSet().getColumnByClass(EmailColumn.class);
        }

        public FirstNameColumn getFirstNameColumn() {
            return getColumnSet().getColumnByClass(FirstNameColumn.class);
        }

        public LastLoginColumn getLastLoginColumn() {
            return getColumnSet().getColumnByClass(LastLoginColumn.class);
        }

        public LastNameColumn getLastNameColumn() {
            return getColumnSet().getColumnByClass(LastNameColumn.class);
        }

        public RolesColumn getRolesColumn() {
            return getColumnSet().getColumnByClass(RolesColumn.class);
        }

        public UsernameColumn getUsernameColumn() {
            return getColumnSet().getColumnByClass(UsernameColumn.class);
        }


        @Order(1000)
        public class UserColumn extends AbstractColumn<User> {
            @Override
            protected boolean getConfiguredDisplayable() {
                return false;
            }
        }

        @Order(2000)
        public class FirstNameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("FirstName");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(3000)
        public class LastNameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("LastName");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(4000)
        public class UsernameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Username");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(4250)
        public class EmailColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Email");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }
        @Order(4500)
        public class ActiveColumn extends AbstractBooleanColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("ActiveStates");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }

            @Override
            protected void execCompleteEdit(ITableRow row, IFormField editingField) {
                super.execCompleteEdit(row, editingField);

                BEANS.get(IUsersService.class).toggleActivated(getUserColumn().getSelectedValue().getId(), getValue(row));

                NotificationHelper.showSaveSuccessNotification();
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) {
                Integer userId = ClientSession.get().getCurrentUser().getId();

                cell.setEditable(!userId.equals(getUserColumn().getValue(row).getId()));
            }
        }
        @Order(5000)
        public class RolesColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Roles");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(6000)
        public class LastLoginColumn extends AbstractDateTimeColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("LastLogin");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(7000)
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
    }
}
