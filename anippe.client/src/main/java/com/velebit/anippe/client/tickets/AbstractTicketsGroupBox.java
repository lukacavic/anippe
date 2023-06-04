package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.shared.tickets.AbstractTicketsGroupBoxData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.DefaultSubtypeSdkCommand;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = AbstractTicketsGroupBoxData.class, sdkCommand = SdkCommand.CREATE, defaultSubtypeSdkCommand = DefaultSubtypeSdkCommand.CREATE)
public class AbstractTicketsGroupBox extends AbstractGroupBox {

    public TicketsTableField getTicketsTableField() {
        return getFieldByClass(TicketsTableField.class);
    }

    @Override
    protected boolean getConfiguredStatusVisible() {
        return false;
    }

    @Override
    protected void execInitField() {
        super.execInitField();

        AbstractTicketsGroupBox.this.setLabel();
    }

    @Override
    protected String getConfiguredLabel() {
        return TEXTS.get("Tickets");
    }

    public void setLabel() {
        Integer ticketsCount = getTicketsTableField().getTable().getRowCount();
        AbstractTicketsGroupBox.this.setLabel(AbstractTicketsGroupBox.this.getConfiguredLabel() + " (" + ticketsCount + ")");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewTicket");
        }

        @Override
        protected void execAction() {

        }
    }

    @Order(1000)
    public class TicketsTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<TicketsTableField.Table> {
        @Override
        public boolean isLabelVisible() {
            return false;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
            return false;
        }

        @Override
        protected int getConfiguredGridH() {
            return 6;
        }

        @ClassId("bf6cbe24-d448-48f5-9812-e3c54af5ac88")
        public class Table extends AbstractTicketsTable {
            @Override
            protected void execContentChanged() {
                super.execContentChanged();

                AbstractTicketsGroupBox.this.setLabel();
            }

            @Override
            public void reloadData() {

            }
        }
    }
}
