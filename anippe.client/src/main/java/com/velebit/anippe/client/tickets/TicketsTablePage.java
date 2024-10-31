package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tickets.TicketsTablePage.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tickets.ITicketsService;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketsTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.datachange.DataChangeEvent;
import org.eclipse.scout.rt.client.ui.desktop.datachange.IDataChangeListener;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(TicketsTablePageData.class)
public class TicketsTablePage extends AbstractPageWithTable<Table> {
    protected final IDataChangeListener m_dataChangeListener = this::dataChanged;

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execDisposePage() {
        super.execDisposePage();

        IDesktop desktop = IDesktop.CURRENT.get();
        if (desktop != null) {
            desktop.removeDataChangeListener(m_dataChangeListener);
        }
    }

    protected void dataChanged(DataChangeEvent event) {
        if (event.getSource().getClass().getName().equals(Ticket.class.getName())) {
            Cell cell = (Cell) getCell();
            cell.setText(setLabelForAssignedTickets());
        }
    }

    @Override
    protected void execInitPage() {
        super.execInitPage();

        IDesktop desktop = IDesktop.CURRENT.get();
        if (desktop != null) {
            desktop.addDataChangeListener(m_dataChangeListener);
        }
    }

    private Integer getAssignedTicketsCount() {
        return BEANS.get(ITicketsService.class).findAssignedTicketsCount();
    }

    private String setLabelForAssignedTickets() {
        return TEXTS.get("Tickets") + " (" + getAssignedTicketsCount() + ")";
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
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(ITicketsService.class).getTicketsTableData(filter));
    }

    @Override
    protected String getConfiguredTitle() {
        return setLabelForAssignedTickets();
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            TicketViewForm form = new TicketViewForm();
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }
    }

    public class Table extends AbstractTicketsTable {

        @Override
        public void reloadData() {
            reloadPage();
        }
    }
}
