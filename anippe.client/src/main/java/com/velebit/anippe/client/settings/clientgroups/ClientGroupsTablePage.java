package com.velebit.anippe.client.settings.clientgroups;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.settings.clientgroups.ClientGroupsTablePage.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.sharedgroups.ClientGroupsTablePageData;
import com.velebit.anippe.shared.settings.sharedgroups.IClientGroupsService;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(ClientGroupsTablePageData.class)
public class ClientGroupsTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IClientGroupsService.class).getClientGroupsTableData(filter));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ClientGroups");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Users1;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Users1;
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            ClientGroupForm form = new ClientGroupForm();
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();
                reloadPage();
            }
        }
    }

    public class Table extends AbstractTable {

        public ClientGroupIdColumn getClientGroupIdColumn() {
            return getColumnSet().getColumnByClass(ClientGroupIdColumn.class);
        }

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        @Order(1000)
        public class EditMenu extends AbstractEditMenu {
            @Override
            protected void execAction() {
                ClientGroupForm form = new ClientGroupForm();
                form.setClientGroupId(getClientGroupIdColumn().getSelectedValue());
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
                    BEANS.get(IClientGroupsService.class).delete(getClientGroupIdColumn().getSelectedValue());

                    NotificationHelper.showDeleteSuccessNotification();

                    reloadPage();
                }
            }
        }

        @Order(1000)
        public class ClientGroupIdColumn extends AbstractIDColumn {

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
    }
}
