package com.velebit.anippe.client.leads;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.leads.LeadsTablePage.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.leads.ILeadsService;
import com.velebit.anippe.shared.leads.LeadsTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(LeadsTablePageData.class)
public class LeadsTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.UserPlus;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.UserPlus;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(ILeadsService.class).getLeadsTableData(filter));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Leads");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            LeadForm form = new LeadForm();
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }
    }

    public class Table extends AbstractLeadsTable {
        @Override
        protected void execInitTable() {
            super.execInitTable();

            getProjectColumn().setDisplayable(true);
        }

        @Override
        public boolean isUseHierarchy() {
            return false;
        }

        @Override
        public void reloadData() {
            reloadPage();
        }
    }
}
