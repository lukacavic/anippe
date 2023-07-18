package com.velebit.anippe.client.clients;

import com.velebit.anippe.client.clients.ClientsTablePage.Table;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.clients.Client;
import com.velebit.anippe.shared.clients.ClientsTablePageData;
import com.velebit.anippe.shared.clients.IClientsService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.desktop.datachange.DataChangeEvent;
import org.eclipse.scout.rt.client.ui.desktop.datachange.IDataChangeListener;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Set;

@Data(ClientsTablePageData.class)
public class ClientsTablePage extends AbstractPageWithTable<Table> {

    protected final IDataChangeListener m_dataChangeListener = this::onDataChanged;

    private void onDataChanged(DataChangeEvent dataChangeEvent) {
        if (dataChangeEvent.getSource().getClass().getName().equals(Client.class.getName())) {
            reloadPage();
        }
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execDisposePage() {
        super.execDisposePage();

        unregisterDataChangeListener(m_dataChangeListener, Client.class);
    }

    @Override
    protected void execInitPage() {
        super.execInitPage();

        registerDataChangeListener(m_dataChangeListener, Client.class);
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Users1;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Users1;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IClientsService.class).getClientsTableData(filter));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Clients");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewClient");
        }

        @Override
        protected void execAction() {
            ClientForm form = new ClientForm();
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }
    }

    public class Table extends AbstractClientsTable {

        @Override
        public void reloadData() {
            reloadPage();
        }
    }
}
