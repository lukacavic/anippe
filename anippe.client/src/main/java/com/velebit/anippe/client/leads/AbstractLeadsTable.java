package com.velebit.anippe.client.leads;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.ICustomCssClasses;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.email.EmailForm;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.leads.ILeadsService;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadSourceLookupCall;
import com.velebit.anippe.shared.leads.LeadStatusLookupCall;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.client.ui.MouseButton;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.ocpsoft.prettytime.PrettyTime;

public abstract class AbstractLeadsTable extends AbstractTable {

    private boolean useHierarchy;

    public abstract void reloadData();

    public boolean isUseHierarchy() {
        return useHierarchy;
    }

    public void setUseHierarchy(boolean useHierarchy) {
        this.useHierarchy = useHierarchy;
    }

    @Override
    protected void execDecorateRow(ITableRow row) {
        super.execDecorateRow(row);

        row.setExpanded(true);
        row.setCssClass("vertical-align-middle");
    }

    @Override
    protected void execRowAction(ITableRow row) {
        super.execRowAction(row);

        LeadViewForm form = new LeadViewForm();
        form.setLeadId(getLeadColumn().getSelectedValue().getId());
        form.startModify();
    }

    public AssignedColumn getAssignedColumn() {
        return getColumnSet().getColumnByClass(AssignedColumn.class);
    }

    public CompanyColumn getCompanyColumn() {
        return getColumnSet().getColumnByClass(CompanyColumn.class);
    }

    public CreatedAtColumn getCreatedAtColumn() {
        return getColumnSet().getColumnByClass(CreatedAtColumn.class);
    }

    public EmailColumn getEmailColumn() {
        return getColumnSet().getColumnByClass(EmailColumn.class);
    }

    public LastContactColumn getLastContactColumn() {
        return getColumnSet().getColumnByClass(LastContactColumn.class);
    }

    public NameColumn getNameColumn() {
        return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public PhoneColumn getPhoneColumn() {
        return getColumnSet().getColumnByClass(PhoneColumn.class);
    }

    public ParentIDColumn getParentIDColumn() {
        return getColumnSet().getColumnByClass(ParentIDColumn.class);
    }

    public PrimaryIDColumn getPrimaryIDColumn() {
        return getColumnSet().getColumnByClass(PrimaryIDColumn.class);
    }

    public SourceColumn getSourceColumn() {
        return getColumnSet().getColumnByClass(SourceColumn.class);
    }

    public StatusColumn getStatusColumn() {
        return getColumnSet().getColumnByClass(StatusColumn.class);
    }

    public LeadColumn getLeadColumn() {
        return getColumnSet().getColumnByClass(LeadColumn.class);
    }

    public ProjectColumn getProjectColumn() {
        return getColumnSet().getColumnByClass(ProjectColumn.class);
    }

    @Override
    protected void execRowClick(ITableRow row, MouseButton mouseButton) {
        super.execRowClick(row, mouseButton);

        if (isUseHierarchy()) {
            getMenuByClass(EditMenu.class).setVisible(row.getParentRow() != null);
            getMenuByClass(DeleteMenu.class).setVisible(row.getParentRow() != null);
        }

        // Send email if column is clicked
        if (getContextColumn().equals(getEmailColumn()) && getEmailColumn().getValue(row) != null) {
            EmailForm form = new EmailForm();
            form.getReceiverField().setValue(CollectionUtility.hashSet(getEmailColumn().getValue(row)));
            form.startNew();
            form.waitFor();
        }
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
        return true;
    }

    @Order(1000)
    public class EditMenu extends AbstractEditMenu {

        @Override
        protected void execAction() {
            LeadForm form = new LeadForm();
            form.setLeadId(getLeadColumn().getSelectedValue().getId());
            form.startModify();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadData();
            }
        }
    }

    @Order(2000)
    public class DeleteMenu extends AbstractDeleteMenu {

        @Override
        protected void execAction() {
            if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                BEANS.get(ILeadsService.class).delete(getLeadColumn().getSelectedValue().getId());

                NotificationHelper.showDeleteSuccessNotification();

                reloadData();
            }
        }
    }

    @Order(0)
    public class PrimaryIDColumn extends AbstractStringColumn {
        @Override
        public boolean isDisplayable() {
            return false;
        }

        @Override
        protected boolean getConfiguredPrimaryKey() {
            return isUseHierarchy();
        }
    }

    @Order(1000)
    public class ParentIDColumn extends AbstractStringColumn {
        @Override
        public boolean isDisplayable() {
            return false;
        }

        @Override
        protected boolean getConfiguredParentKey() {
            return isUseHierarchy();
        }
    }

    @Order(1000)
    public class LeadColumn extends AbstractColumn<Lead> {
        @Override
        protected boolean getConfiguredDisplayable() {
            return false;
        }
    }

    @Order(2000)
    public class NameColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Name");
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

            if (row.getParentRow() != null) {
                String content = HTML.fragment(
                        HTML.span(getValue(row)).cssClass(ICustomCssClasses.TABLE_HTML_CELL_HEADING),
                        HTML.br(),
                        HTML.span(ObjectUtility.nvl(getCompanyColumn().getValue(row), "")).cssClass(ICustomCssClasses.TABLE_HTML_CELL_SUB_HEADING)
                ).toHtml();

                cell.setText(content);
            }
        }
    }

    @Order(3000)
    public class CompanyColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Company");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(4000)
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

    @Order(5000)
    public class PhoneColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Phone");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(5500)
    public class ProjectColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Project");
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

    @Order(6000)
    public class AssignedColumn extends AbstractSmartColumn<Long> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Assigned");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<Long> call, ITableRow row) {
            super.execPrepareLookup(call, row);

            Integer projectId = getLeadColumn().getValue(row).getId() != null ? getLeadColumn().getValue(row).getProject().getId() : null;

            UserLookupCall c = (UserLookupCall) call;
            if (projectId != null) {
                c.setProjectId(projectId);
            }
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return UserLookupCall.class;
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            if (!getLeadColumn().getValue(row).isConverted() && row.getParentRow() != null) {
                cell.setEditable(true);
            }
        }
    }

    @Order(7000)
    public class StatusColumn extends AbstractSmartColumn<Long> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Status");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<Long> call, ITableRow row) {
            super.execPrepareLookup(call, row);

            Integer projectId = getLeadColumn().getValue(row).getId() != null ? getLeadColumn().getValue(row).getProject().getId() : null;

            LeadStatusLookupCall c = (LeadStatusLookupCall) call;
            if (projectId != null) {
                c.setProjectId(projectId);
            }
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return LeadStatusLookupCall.class;
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            if (!getLeadColumn().getValue(row).isConverted() && row.getParentRow() != null) {
                cell.setEditable(true);
            }
        }

        @Override
        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
            super.execCompleteEdit(row, editingField);

            Long value = getValue(row);

            BEANS.get(ILeadsService.class).changeStatus(getLeadColumn().getValue(row).getId(), value);

            reloadData();
        }
    }

    @Order(8000)
    public class SourceColumn extends AbstractSmartColumn<Long> {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Source0");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return LeadSourceLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<Long> call, ITableRow row) {
            super.execPrepareLookup(call, row);

            Integer projectId = getLeadColumn().getValue(row).getId() != null ? getLeadColumn().getValue(row).getProject().getId() : null;

            LeadSourceLookupCall c = (LeadSourceLookupCall) call;
            if (projectId != null) {
                c.setProjectId(projectId);
            }
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            if (!getLeadColumn().getValue(row).isConverted() && row.getParentRow() != null && isUseHierarchy()) {
                cell.setEditable(true);
            }
        }

        @Override
        protected void execCompleteEdit(ITableRow row, IFormField editingField) {
            super.execCompleteEdit(row, editingField);

            Long value = getValue(row);
            BEANS.get(ILeadsService.class).changeSource(getLeadColumn().getValue(row).getId(), value);
        }
    }

    @Order(9000)
    public class LastContactColumn extends AbstractDateTimeColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("LastContact");
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

    @Order(10000)
    public class CreatedAtColumn extends AbstractDateTimeColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("CreatedAt");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }

        @Override
        protected void execDecorateCell(Cell cell, ITableRow row) {
            super.execDecorateCell(cell, row);

            if (getValue(row) != null) {
                String createdAt = new PrettyTime(ClientSession.get().getLocale()).format(getValue(row));
                cell.setText(createdAt);
            }
        }
    }

}
