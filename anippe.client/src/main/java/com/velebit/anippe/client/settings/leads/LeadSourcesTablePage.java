package com.velebit.anippe.client.settings.leads;

import com.velebit.anippe.client.common.columns.AbstractColorColumn;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.settings.leads.LeadSourcesTablePage.Table;
import com.velebit.anippe.client.settings.leads.LeadStatusesTablePage.Table.NameColumn;
import com.velebit.anippe.client.settings.leads.LeadStatusesTablePage.Table.StatusIdColumn;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.leads.ILeadSourcesService;
import com.velebit.anippe.shared.settings.leads.ILeadStatusesService;
import com.velebit.anippe.shared.settings.leads.LeadSourcesTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(LeadSourcesTablePageData.class)
public class LeadSourcesTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(ILeadSourcesService.class).getLeadSourcesTableData(filter));
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.GroupPlus;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.GroupPlus;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Sources");
    }
    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            LeadSourceForm form = new LeadSourceForm();
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
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
        }

        public SourceIdColumn getSourceIdColumn() {
            return getColumnSet().getColumnByClass(SourceIdColumn.class);
        }

        @Order(1000)
        public class EditMenu extends AbstractEditMenu {

            @Override
            protected void execAction() {
                LeadSourceForm form = new LeadSourceForm();
                form.setLeadSourceId(getSourceIdColumn().getSelectedValue());
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
                    BEANS.get(ILeadSourcesService.class).delete(getSourceIdColumn().getSelectedValue());

                    NotificationHelper.showDeleteSuccessNotification();

                    reloadPage();
                }

            }
        }

        @Order(1000)
        public class SourceIdColumn extends AbstractIDColumn {

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
