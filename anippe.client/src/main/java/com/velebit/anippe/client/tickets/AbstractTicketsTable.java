package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.ICustomCssClasses;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.common.menus.AbstractRefreshMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.shared.constants.Constants.TicketStatus;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tickets.ITicketViewService;
import com.velebit.anippe.shared.tickets.ITicketsService;
import com.velebit.anippe.shared.tickets.Ticket;
import com.velebit.anippe.shared.tickets.TicketDepartment;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.datachange.DataChangeEvent;
import org.eclipse.scout.rt.client.ui.desktop.datachange.IDataChangeListener;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Set;

public abstract class AbstractTicketsTable extends AbstractTable {

    protected final IDataChangeListener m_dataChangeListener = this::dataChanged;

    public abstract void reloadData();

    @Override
    protected void execDisposeTable() {
        super.execDisposeTable();

        IDesktop desktop = IDesktop.CURRENT.get();
        if (desktop != null) {
            desktop.removeDataChangeListener(m_dataChangeListener);
        }
    }

    protected void dataChanged(DataChangeEvent event) {
        if (event.getSource().getClass().getName().equals(Ticket.class.getName())) {
            reloadData();
        }
    }

    @Override
    protected void execInitTable() {
        super.execInitTable();

        IDesktop desktop = IDesktop.CURRENT.get();
        if (desktop != null) {
            desktop.addDataChangeListener(m_dataChangeListener);
        }
    }

    @Override
    protected void execDecorateRow(ITableRow row) {
        super.execDecorateRow(row);

        row.setCssClass("vertical-align-middle");
    }

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
        return EditMenu.class;
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

    @Order(0)
    public class AssignToMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("AssignTo");
        }

        @Override
        protected String getConfiguredIconId() {
            return FontIcons.UserPlus;
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(org.eclipse.scout.rt.client.ui.action.menu.TableMenuType.MultiSelection);
        }

        @Override
        protected void execAction() {

        }
    }

    @Order(500)
    public class MergeMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("Merge");
        }

        @Override
        protected String getConfiguredIconId() {
            return FontIcons.Clone;
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TableMenuType.MultiSelection);
        }

        @Override
        protected void execAction() {

        }
    }

    @Order(1000)
    public class EditMenu extends AbstractEditMenu {

        @Override
        protected void execAction() {
            TicketViewForm form = new TicketViewForm();
            form.setTicketId(getTicketColumn().getSelectedValue().getId());
            form.startModify();
            form.waitFor();
            if (form.isFormStored()) {
                reloadData();
            }
        }
    }

    @Order(1900)
    public class MarkAsSpamMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("Spam");
        }

        @Override
        protected String getConfiguredIconId() {
            return FontIcons.UserMinus;
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TableMenuType.MultiSelection);
        }

        @Override
        protected void execAction() {

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

    @Order(3000)
    public class RefreshMenu extends AbstractRefreshMenu {


        @Override
        protected void execAction() {
            reloadData();
        }
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
    public class DepartmentColumn extends AbstractColumn<TicketDepartment> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Department");
        }

        @Override
        protected int getConfiguredWidth() {
            return 130;
        }

        @Override
        protected boolean getConfiguredHtmlEnabled() {
            return true;
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            String content = HTML.fragment(
                    HTML.span(getValue(row).getName()),
                    HTML.br(),
                    HTML.span(ObjectUtility.nvl(getValue(row).getImapImportEmail(), "")).cssClass(ICustomCssClasses.TABLE_HTML_CELL_SUB_HEADING)
            ).toHtml();

            cell.setText(content);
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

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            //boolean isClosed = getTicketColumn().getValue(row).getStatusId().equals(TicketStatus.CLOSED);

            String color = getColorByTicketStatus(getTicketColumn().getValue(row).getStatusId());
            //cell.setBackgroundColor(color);

            IHtmlContent content = HTML.fragment(
                    HTML.span(cell.getText()).style("background-color:" + color + ";font-size:11px;color:#fff;padding:6px;border-radius:5px;")
            );

            cell.setText(content.toHtml());
            cell.setEditable(false);
        }

        @Override
        protected boolean getConfiguredHtmlEnabled() {
            return true;
        }

        private String getColorByTicketStatus(Integer statusId) {
            String color = null;

            if (statusId == null) return color;

            if (statusId.equals(TicketStatus.CREATED)) {
                color = "#f23646";
            } else if (statusId.equals(TicketStatus.CLOSED)) {
                color = "#989999";
            } else if (statusId.equals(TicketStatus.ANSWERED)) {
                color = "#76b5d3";
            } else if (statusId.equals(TicketStatus.ON_HOLD)) {
                color = "#b1b1b1";
            } else if (statusId.equals(TicketStatus.IN_PROGRESS)) {
                color = "#54a81a";
            }

            return color;
        }

        @Override
        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
            super.execCompleteEdit(row, editingField);
            if (getValue(row) == null) return;

            Integer ticketId = getTicketColumn().getValue(row).getId();
            Integer statusId = getValue(row);

            BEANS.get(ITicketViewService.class).changeStatus(ticketId, statusId);
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
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            boolean isClosed = getTicketColumn().getValue(row).getStatusId().equals(TicketStatus.CLOSED);

            cell.setEditable(!isClosed);
        }

        @Override
        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
            super.execCompleteEdit(row, editingField);
            if (getValue(row) == null) return;

            Integer ticketId = getTicketColumn().getValue(row).getId();
            Integer priorityId = getValue(row);

            BEANS.get(ITicketViewService.class).changePriority(ticketId, priorityId);
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(5500)
    public class AssignedUserColumn extends AbstractSmartColumn<Long> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Assigned");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return UserLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<Long> call, ITableRow row) {
            super.execPrepareLookup(call, row);

            Ticket ticket = getTicketColumn().getValue(row);

            UserLookupCall c = (UserLookupCall) call;
            if (ticket.getProject() != null) {
                c.setProjectId(ticket.getProject().getId());
            }
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            boolean isClosed = getTicketColumn().getValue(row).getStatusId().equals(TicketStatus.CLOSED);

            cell.setEditable(!isClosed);
        }

        @Override
        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
            super.execCompleteEdit(row, editingField);
            if (getValue(row) == null) return;

            Integer ticketId = getTicketColumn().getValue(row).getId();
            Integer assignedUserId = getValue(row).intValue();

            BEANS.get(ITicketViewService.class).changeAssignedUser(ticketId, assignedUserId);
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
