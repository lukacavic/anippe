package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.projects.SupportForm.MainBox.GroupBox;
import com.velebit.anippe.shared.projects.ISupportService;
import com.velebit.anippe.shared.projects.Project;
import com.velebit.anippe.shared.projects.SupportFormData;
import com.velebit.anippe.shared.tickets.Ticket;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = SupportFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SupportForm extends AbstractForm {

    private Project project;
    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Support");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.TicketsTableField getTicketsTableField() {
        return getFieldByClass(GroupBox.TicketsTableField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Order(1000)
            public class AddMenu extends AbstractAddMenu {

                @Override
                protected void execAction() {

                }
            }

            @Order(1000)
            public class TicketsTableField extends AbstractTableField<TicketsTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @ClassId("f2852610-de4f-43d6-95ef-9867ba98b85d")
                public class Table extends AbstractTable {
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

                    public LastReplyAtColumn getLastReplyAtColumn() {
                        return getColumnSet().getColumnByClass(LastReplyAtColumn.class);
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
                            return 120;
                        }
                    }

                    @Order(3000)
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

                    @Order(5000)
                    public class PriorityColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Priority");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(6000)
                    public class LastReplyAtColumn extends AbstractDateTimeColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("LastReply");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(7000)
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
            }
        }

    }

    public void startClient() {
        startInternal(new ClientHandler());
    }

    public class ClientHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            SupportFormData formData = new SupportFormData();
            exportFormData(formData);
            formData = BEANS.get(ISupportService.class).loadClients(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            //Hide show fields...
        }
    }

}
