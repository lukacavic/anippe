package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.ICustomCssClasses;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.shared.tickets.ITicketsService;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketDepartmentLookupCall;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.ocpsoft.prettytime.PrettyTime;

public abstract class AbstractTicketsTable extends AbstractTable {

	public abstract void reloadData();

    @Override
    protected void execDecorateRow(ITableRow row) {
        super.execDecorateRow(row);

        row.setCssClass("vertical-align-middle");
    }

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
        return EditMenu.class;
    }

    @Order(1000)
	public class EditMenu extends AbstractEditMenu {

		@Override
        protected void execAction() {
            TicketForm form = new TicketForm();
            form.setTicketId(getTicketColumn().getSelectedValue().getId());
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
                BEANS.get(ITicketsService.class).delete(getTicketColumn().getSelectedValue().getId());

                NotificationHelper.showDeleteSuccessNotification();

                reloadData();
            }
        }
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
        return true;
    }

    public AssignedUserColumn getAssignedUserColumn() {
        return getColumnSet().getColumnByClass(AssignedUserColumn.class);
    }

    public CodeColumn getCodeColumn() {
        return getColumnSet().getColumnByClass(CodeColumn.class);
    }

    public ContactColumn getContactColumn() {
        return getColumnSet().getColumnByClass(ContactColumn.class);
    }

    public CreatedAtColumn getCreatedAtColumn() {
        return getColumnSet().getColumnByClass(CreatedAtColumn.class);
    }

    public DepartmentColumn getDepartmentColumn() {
        return getColumnSet().getColumnByClass(DepartmentColumn.class);
    }

    public LastReplyColumn getLastReplyColumn() {
        return getColumnSet().getColumnByClass(LastReplyColumn.class);
    }

    public PriorityColumn getPriorityColumn() {
        return getColumnSet().getColumnByClass(PriorityColumn.class);
    }

    public StatusColumn getStatusColumn() {
        return getColumnSet().getColumnByClass(StatusColumn.class);
    }

    public SubjectColumn getSubjectColumn() {
        return getColumnSet().getColumnByClass(SubjectColumn.class);
    }

	public TicketColumn getTicketColumn() {
		return getColumnSet().getColumnByClass(TicketColumn.class);
    }

    @Order(1000)
    public class TicketColumn extends AbstractColumn<Ticket> {
        @Override
        protected boolean getConfiguredDisplayable() {
            return false;
        }
    }

    @Order(1500)
    public class CodeColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Code");
        }

        @Override
        protected boolean getConfiguredDisplayable() {
            return false;
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(2000)
    public class SubjectColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Subject");
        }

        @Override
        protected boolean getConfiguredHtmlEnabled() {
            return true;
        }

        @Override
        protected int getConfiguredWidth() {
            return 150;
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            String content = HTML.fragment(
                    HTML.span(getValue(row)).cssClass(ICustomCssClasses.TABLE_HTML_CELL_HEADING),
                    HTML.br(),
                    HTML.span(ObjectUtility.nvl(getCodeColumn().getValue(row), "-")).cssClass(ICustomCssClasses.TABLE_HTML_CELL_SUB_HEADING)
            ).toHtml();

            cell.setText(content);
        }
    }

    @Order(3000)
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

    @Order(3250)
    public class DepartmentColumn extends AbstractSmartColumn<Long> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Department");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return TicketDepartmentLookupCall.class;
        }

        @Override
        protected int getConfiguredWidth() {
            return 130;
        }
    }

    @Order(3500)
    public class ContactColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Contact");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(4000)
    public class StatusColumn extends AbstractSmartColumn<Integer> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Status");
        }
        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
            return TicketStatusLookupCall.class;
        }
        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(5000)
    public class PriorityColumn extends AbstractSmartColumn<Integer> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Priority");
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
            return PriorityLookupCall.class;
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(5500)
    public class AssignedUserColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Assigned");
        }

        @Override
        protected int getConfiguredWidth() {
            return 130;
        }
    }

    @Order(6000)
    public class LastReplyColumn extends AbstractDateTimeColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("LastReply");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            if (getValue(row) != null) {
                String lastContactAt = new PrettyTime(ClientSession.get().getLocale()).format(getValue(row));
                cell.setText(lastContactAt);
            }
        }
    }
}
