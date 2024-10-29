package com.velebit.anippe.client.leads;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.*;
import com.velebit.anippe.client.email.EmailForm;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tasks.AbstractTasksTable;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.leads.ILeadService;
import com.velebit.anippe.shared.leads.ILeadViewService;
import com.velebit.anippe.shared.leads.ILeadsService;
import com.velebit.anippe.shared.leads.LeadViewFormData;
import com.velebit.anippe.shared.leads.LeadViewFormData.ActivityTable.ActivityTableRowData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

@FormData(value = LeadViewFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LeadViewForm extends AbstractForm {

    private Integer leadId;

    @FormData
    public Integer getLeadId() {
        return leadId;
    }

    @FormData
    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("LeadCard");
    }

    public MainBox.SidebarTabBox.ActivityBox getActivityBox() {
        return getFieldByClass(MainBox.SidebarTabBox.ActivityBox.class);
    }

    public MainBox.SidebarTabBox.ActivityBox.ActivityTableField getActivityTableField() {
        return getFieldByClass(MainBox.SidebarTabBox.ActivityBox.ActivityTableField.class);
    }

    @Override
    protected int getConfiguredDisplayHint() {
        return DISPLAY_HINT_VIEW;
    }

    public MainBox.MainTabBox.DocumentsBox getDocumentsBox() {
        return getFieldByClass(MainBox.MainTabBox.DocumentsBox.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public MainBox.MainTabBox getMainTabBox() {
        return getFieldByClass(MainBox.MainTabBox.class);
    }

    public MainBox.MainTabBox.OverviewBox getOverviewBox() {
        return getFieldByClass(MainBox.MainTabBox.OverviewBox.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("ViewEntry");
    }

    public MainBox.SidebarTabBox getSidebarTabBox() {
        return getFieldByClass(MainBox.SidebarTabBox.class);
    }

    public MainBox.MainTabBox.TasksBox getTasksBox() {
        return getFieldByClass(MainBox.MainTabBox.TasksBox.class);
    }

    public MainBox.MainTabBox.TasksBox.TasksField getTasksField() {
        return getFieldByClass(MainBox.MainTabBox.TasksBox.TasksField.class);
    }

    @Override
    protected boolean getConfiguredClosable() {
        return true;
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void fetchActivityLogs() {
        List<ActivityTableRowData> rows = BEANS.get(ILeadViewService.class).fetchActivityLog(getLeadId());
        getActivityTableField().getTable().importFromTableRowBeanData(rows, ActivityTableRowData.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Override
        protected int getConfiguredGridColumnCount() {
            return 4;
        }

        @Order(1000)
        public class ActionsMenu extends AbstractActionsMenu {
            @Override
            public boolean isVisible() {
                return true;
            }

            @Order(0)
            public class EditMenu extends AbstractEditMenu {
                @Override
                protected void execAction() {
                    LeadForm form = new LeadForm();
                    form.setLeadId(getLeadId());
                    form.startModify();
                    form.waitFor();
                    if (form.isFormStored()) {
                        NotificationHelper.showSaveSuccessNotification();
                    }
                }
            }

            @Order(3000)
            public class DeleteMenu extends AbstractDeleteMenu {

                @Override
                protected void execAction() {
                    if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                        BEANS.get(ILeadsService.class).delete(getLeadId());

                        NotificationHelper.showDeleteSuccessNotification();

                        getForm().doClose();
                    }
                }
            }
        }

        @Order(2000)
        public class SendEmailMenu extends AbstractSendEmailMenu {

            @Override
            protected void execAction() {
                EmailForm form = new EmailForm();
                form.startNew();
            }
        }

        @Order(3000)
        public class AddActivityMenu extends AbstractAddMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("AddActivity");
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return -1;
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.History;
            }

            @Override
            protected void execAction() {
                ActivityLogForm form = new ActivityLogForm();
                form.setLeadId(getLeadId());
                form.startNew();
                form.waitFor();
                if (form.isFormStored()) {
                    NotificationHelper.showSaveSuccessNotification();
                }

            }
        }

        @Order(4000)
        public class AddNoteMenu extends AbstractAddMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("AddNote");
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return -1;
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Note;
            }

            @Override
            protected void execAction() {
            }
        }

        @Order(1000)
        public class MainTabBox extends AbstractTabBox {
            @Override
            protected int getConfiguredGridW() {
                return 3;
            }

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Order(1000)
            public class OverviewBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Overview");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }
            }

            @Order(2000)
            public class TasksBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Tasks");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(1000)
                public class TasksField extends AbstractTableField<TasksField.Table> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("MyNlsKey");
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 6;
                    }

                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @ClassId("e8a92659-ed8d-470c-b55d-d9c5c84736f2")
                    public class Table extends AbstractTasksTable {

                    }
                }
            }

            @Order(3000)
            public class DocumentsBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Documents");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(1000)
                public class DocumentsTableField extends AbstractTableField<DocumentsTableField.Table> {
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

                    @ClassId("2aadb19f-e6b2-4135-9fe0-119fabdc5f5e")
                    public class Table extends AbstractTable {

                    }
                }
            }
        }

        @Order(2000)
        public class SidebarTabBox extends AbstractTabBox {
            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Override
            protected String getConfiguredTabAreaStyle() {
                return TAB_AREA_STYLE_SPREAD_EVEN;
            }

            @Override
            protected int getConfiguredGridW() {
                return 1;
            }

            @Order(1000)
            public class ActivityBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("ActivityLog");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(1000)
                public class ActivityTableField extends AbstractTableField<ActivityTableField.Table> {
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

                    @ClassId("1e40017a-e574-41d9-9a03-a030a7dd8828")
                    public class Table extends AbstractTable {
                        @Override
                        protected boolean getConfiguredAutoResizeColumns() {
                            return true;
                        }

                        public ActivityLogIdColumn getActivityLogIdColumn() {
                            return getColumnSet().getColumnByClass(ActivityLogIdColumn.class);
                        }

                        public ContentColumn getContentColumn() {
                            return getColumnSet().getColumnByClass(ContentColumn.class);
                        }

                        public CreatedAtColumn getCreatedAtColumn() {
                            return getColumnSet().getColumnByClass(CreatedAtColumn.class);
                        }

                        public UserColumn getUserColumn() {
                            return getColumnSet().getColumnByClass(UserColumn.class);
                        }

                        @Override
                        protected boolean getConfiguredHeaderVisible() {
                            return false;
                        }

                        @Order(1000)
                        public class EditMenu extends AbstractEditMenu {

                            @Override
                            protected void execAction() {
                                ActivityLogForm form = new ActivityLogForm();
                                form.setActivityId(getActivityLogIdColumn().getSelectedValue());
                                form.startModify();
                                form.waitFor();
                                if (form.isFormStored()) {
                                    NotificationHelper.showSaveSuccessNotification();
                                    fetchActivityLogs();
                                }
                            }
                        }

                        @Order(2000)
                        public class DeleteMenu extends AbstractDeleteMenu {

                            @Override
                            protected void execAction() {
                                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                                    BEANS.get(ILeadService.class).deleteActivityLog(getActivityLogIdColumn().getSelectedValue());

                                    NotificationHelper.showDeleteSuccessNotification();

                                    fetchActivityLogs();
                                }
                            }
                        }

                        @Order(1000)
                        public class ActivityLogIdColumn extends AbstractIDColumn {

                        }

                        @Order(2000)
                        public class ContentColumn extends AbstractStringColumn {
                            @Override
                            protected void execDecorateCell(Cell cell, ITableRow row) {
                                super.execDecorateCell(cell, row);

                                String createdAt = new PrettyTime().format(getCreatedAtColumn().getValue(row));
                                String user = getUserColumn().getValue(row);

                                IHtmlContent content = HTML.fragment(
                                        HTML.span(createdAt).style("font-size:11px;font-weight:bold;"),
                                        HTML.p(getValue(row)),
                                        HTML.span(user).style("font-size:11px;font-style:italic;")
                                );

                                cell.setText(content.toHtml());
                            }

                            @Override
                            protected boolean getConfiguredTextWrap() {
                                return true;
                            }

                            @Override
                            protected boolean getConfiguredHtmlEnabled() {
                                return true;
                            }
                        }

                        @Order(3000)
                        public class CreatedAtColumn extends AbstractDateTimeColumn {
                            @Override
                            public boolean isDisplayable() {
                                return false;
                            }
                        }

                        @Order(4000)
                        public class UserColumn extends AbstractStringColumn {
                            @Override
                            public boolean isDisplayable() {
                                return false;
                            }
                        }
                    }
                }
            }
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            LeadViewFormData formData = new LeadViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadViewService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            LeadViewFormData formData = new LeadViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadViewService.class).store(formData);
            importFormData(formData);
        }
    }
}
