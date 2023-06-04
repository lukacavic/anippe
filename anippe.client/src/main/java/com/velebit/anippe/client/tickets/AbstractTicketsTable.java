package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.client.settings.users.UserForm;
import com.velebit.anippe.shared.tickets.ITicketsService;
import com.velebit.anippe.shared.tickets.Ticket;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

public abstract class AbstractTicketsTable extends AbstractTable {

	public abstract void reloadData();

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
			if(form.isFormStored()) {
				reloadData();
			}
		}
	}
	@Order(2000)
	public class DeleteMenu extends AbstractDeleteMenu {

		@Override
		protected void execAction() {
			if(MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
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

    public ContactColumn getContactColumn() {
        return getColumnSet().getColumnByClass(ContactColumn.class);
    }

    public CreatedAtColumn getCreatedAtColumn() {
        return getColumnSet().getColumnByClass(CreatedAtColumn.class);
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

    @Order(2000)
    public class SubjectColumn extends AbstractStringColumn {
        @Override
        protected String getConfiguredHeaderText() {
            return TEXTS.get("Subject");
        }

        @Override
        protected int getConfiguredWidth() {
            return 100;
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
    }
}
