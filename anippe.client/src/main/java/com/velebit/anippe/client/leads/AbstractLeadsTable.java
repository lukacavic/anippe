package com.velebit.anippe.client.leads;

import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.settings.users.UserForm;
import com.velebit.anippe.shared.leads.ILeadsService;
import com.velebit.anippe.shared.leads.Lead;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

public abstract class AbstractLeadsTable extends AbstractTable {

    public abstract void reloadData();

    @Order(1000)
    public class EditMenu extends AbstractEditMenu {

        @Override
        protected void execAction() {
            LeadForm form = new LeadForm();
            form.setLeadId(getLeadColumn().getSelectedValue().getId());
            form.startModify();
            form.waitFor();
            if(form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadData();
            }
        }
    }
    @Order(2000)
    public class DeleteMenu extends AbstractDeleteMenu {

        @Override
        protected void execAction() {
            if(MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                BEANS.get(ILeadsService.class).delete(getLeadColumn().getSelectedValue().getId());

                NotificationHelper.showDeleteSuccessNotification();

                reloadData();
            }
        }
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

    public SourceColumn getSourceColumn() {
        return getColumnSet().getColumnByClass(SourceColumn.class);
    }

    public StatusColumn getStatusColumn() {
        return getColumnSet().getColumnByClass(StatusColumn.class);
    }

    public LeadColumn getLeadColumn() {
        return getColumnSet().getColumnByClass(LeadColumn.class);
    }


    @Override
    protected boolean getConfiguredAutoResizeColumns() {
        return true;
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
        protected int getConfiguredWidth() {
            return 100;
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

    @Order(6000)
    public class AssignedColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Assigned");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(7000)
    public class StatusColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Status");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
        }
    }

    @Order(8000)
    public class SourceColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Source0");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
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
    }
}
