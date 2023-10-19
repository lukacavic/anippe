package com.velebit.anippe.client.leads;

import com.velebit.anippe.client.attachments.AbstractAttachmentsBox;
import com.velebit.anippe.client.common.fields.AbstractEmailField;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.common.menus.*;
import com.velebit.anippe.client.email.EmailForm;
import com.velebit.anippe.client.interaction.FormNotificationHelper;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.leads.LeadForm.MainBox.*;
import com.velebit.anippe.client.leads.LeadForm.MainBox.ActionsMenu.MarkAsLostMenu;
import com.velebit.anippe.client.leads.LeadForm.MainBox.ActionsMenu.MarkAsNotLost;
import com.velebit.anippe.client.leads.LeadForm.MainBox.MainTabBox.AttachmentsBox;
import com.velebit.anippe.client.leads.LeadForm.MainBox.MainTabBox.MainInformationsBox;
import com.velebit.anippe.client.leads.LeadForm.MainBox.MainTabBox.MainInformationsBox.*;
import com.velebit.anippe.client.notes.AbstractSidebarNotesGroupBox;
import com.velebit.anippe.client.notes.AbstractSidebarNotesGroupBox.NotesTableField.Table.AddMenu;
import com.velebit.anippe.client.reminders.AbstractRemindersGroupBox;
import com.velebit.anippe.client.tasks.AbstractTasksGroupBox;
import com.velebit.anippe.shared.constants.Constants.Related;
import com.velebit.anippe.shared.country.CountryLookupCall;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.leads.*;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tasks.AbstractTasksGroupBoxData.TasksTable.TasksTableRowData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.TableEvent;
import org.eclipse.scout.rt.client.ui.basic.table.TableListener;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSaveButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.form.fields.tagfield.AbstractTagField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.notification.INotification;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.List;

@FormData(value = LeadFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LeadForm extends AbstractForm {
    private Integer leadId;

    private Integer clientId;
    private boolean lost = false; // is lead lost?
    private Integer projectId;

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public Object computeExclusiveKey() {
        return getLeadId();
    }

    @FormData
    public boolean isLost() {
        return lost;
    }

    @FormData
    public void setLost(boolean lost) {
        this.lost = lost;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Lead");
    }

    public MainBox.MainSidebarTabBox.ActivityLogBox getActivityLogBox() {
        return getFieldByClass(MainBox.MainSidebarTabBox.ActivityLogBox.class);
    }

    public MainBox.MainSidebarTabBox.ActivityLogBox.ActivityLogTableField getActivityLogTableField() {
        return getFieldByClass(MainBox.MainSidebarTabBox.ActivityLogBox.ActivityLogTableField.class);
    }

    public AddressField getAddressField() {
        return getFieldByClass(AddressField.class);
    }

    public AssignedUserField getAssignedUserField() {
        return getFieldByClass(AssignedUserField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    @Override
    protected int getConfiguredDisplayHint() {
        return DISPLAY_HINT_VIEW;
    }


    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.UserPlus;
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public CityField getCityField() {
        return getFieldByClass(CityField.class);
    }

    public CompanyField getCompanyField() {
        return getFieldByClass(CompanyField.class);
    }

    public CountryField getCountryField() {
        return getFieldByClass(CountryField.class);
    }

    public DescriptionField getDescriptionField() {
        return getFieldByClass(DescriptionField.class);
    }

    public AttachmentsBox getAttachmentsBox() {
        return getFieldByClass(AttachmentsBox.class);
    }

    public EmailField getEmailField() {
        return getFieldByClass(EmailField.class);
    }

    public LastContactAtField getLastContactAtField() {
        return getFieldByClass(LastContactAtField.class);
    }


    public MainInformationsBox getMainInformationsBox() {
        return getFieldByClass(MainInformationsBox.class);
    }

    public MainBox.MainTabBox getMainTabBox() {
        return getFieldByClass(MainBox.MainTabBox.class);
    }

    public MainInformationsBox.NameField getNameField() {
        return getFieldByClass(MainInformationsBox.NameField.class);
    }


    public PhoneField getPhoneField() {
        return getFieldByClass(PhoneField.class);
    }

    public PositionField getPositionField() {
        return getFieldByClass(PositionField.class);
    }

    public PostalCodeField getPostalCodeField() {
        return getFieldByClass(PostalCodeField.class);
    }

    public MainBox.MainTabBox.TasksBox getTasksBox() {
        return getFieldByClass(MainBox.MainTabBox.TasksBox.class);
    }

    public WebsiteField getWebsiteField() {
        return getFieldByClass(WebsiteField.class);
    }

    @FormData
    public Integer getLeadId() {
        return leadId;
    }

    @FormData
    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public ConvertToCustomerButton getConvertToCustomerButton() {
        return getFieldByClass(ConvertToCustomerButton.class);
    }

    public MainBox.MainSidebarTabBox getMainSidebarTabBox() {
        return getFieldByClass(MainBox.MainSidebarTabBox.class);
    }

    public MainBox.MainSidebarTabBox.NotesBox getNotesBox() {
        return getFieldByClass(MainBox.MainSidebarTabBox.NotesBox.class);
    }

    public MainBox.MainTabBox.RemindersBox getRemindersBox() {
        return getFieldByClass(MainBox.MainTabBox.RemindersBox.class);
    }

    public SourceField getSourceField() {
        return getFieldByClass(SourceField.class);
    }

    public StatusField getStatusField() {
        return getFieldByClass(StatusField.class);
    }

    public TagsField getTagsField() {
        return getFieldByClass(TagsField.class);
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public void setLabels() {
        int attachmentsCount = getAttachmentsBox().getAttachmentsTableField().getTable().getRowCount();
        getAttachmentsBox().setLabel(TEXTS.get("Attachments") + " (" + attachmentsCount + ")");

        int tasksCount = getTasksBox().getTasksTableField().getTable().getRowCount();
        getTasksBox().setLabel(TEXTS.get("Tasks") + " (" + tasksCount + ")");

        int remindersCount = getRemindersBox().getRemindersTableField().getTable().getRowCount();
        getRemindersBox().setLabel(TEXTS.get("Reminders") + " (" + remindersCount + ")");
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
            return 4;
        }

        @Order(1000)
        public class ActionsMenu extends AbstractActionsMenu {

            @Order(0)
            public class EditMenu extends AbstractEditMenu {
                @Override
                protected void execAction() {
                    getMainInformationsBox().getFields().forEach(f -> f.setEnabledGranted(true));
                }
            }

            @Order(1000)
            public class MarkAsLostMenu extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("MarkAsLost");
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.UserMinus;
                }

                @Override
                protected void execAction() {
                    BEANS.get(ILeadService.class).markAsLost(getLeadId(), true);
                    setLost(true);

                    renderForm();
                }
            }

            @Order(2000)
            public class MarkAsNotLost extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("MarkAsNotLost");
                }

                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.UserPlus;
                }

                @Override
                protected void execAction() {
                    BEANS.get(ILeadService.class).markAsLost(getLeadId(), false);
                    setLost(false);

                    renderForm();
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
                if (getEmailField().getValue() == null) {
                    NotificationHelper.showErrorNotification(TEXTS.get("ThisLeadIsWithoutEmailAddress"));
                    return;
                }

                EmailForm form = new EmailForm();
                form.getReceiverField().setValue(CollectionUtility.hashSet(getEmailField().getValue()));
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
            protected boolean getConfiguredVisible() {
                return false;
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

            }
        }

        @Order(4000)
        public class AddNoteMenu extends AbstractAddMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("AddNote");
            }

            @Override
            protected boolean getConfiguredVisible() {
                return false;
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
MenuUtility.getMenuByClass(getNotesBox().getNotesTableField().getTable(), AddMenu.class).doAction();
            }
        }

        @Order(1500)
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
            public class MainInformationsBox extends AbstractGroupBox {
                @Override
                protected void execInitField() {
                    super.execInitField();

                    getFields().forEach(f -> f.setDisabledStyle(DISABLED_STYLE_READ_ONLY));
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("MainInformations");
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 2;
                }

                @Order(1000)
                public class NameField extends AbstractStringField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Name");
                    }

                    @Override
                    protected boolean getConfiguredMandatory() {
                        return true;
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(2000)
                public class PositionField extends AbstractStringField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Position");
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(3000)
                public class EmailField extends AbstractEmailField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Email");
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(4000)
                public class WebsiteField extends AbstractStringField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Website");
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(5000)
                public class PhoneField extends AbstractStringField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Phone");
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(6000)
                public class AddressField extends AbstractStringField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Address");
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(7000)
                public class CityField extends AbstractStringField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("City");
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(8000)
                public class PostalCodeField extends AbstractStringField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("PostalCode");
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(9000)
                public class CountryField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Country");
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return CountryLookupCall.class;
                    }
                }

                @Order(10000)
                public class CompanyField extends AbstractStringField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Company");
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(10500)
                public class LastContactAtField extends AbstractDateTimeField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("LastContact");
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }
                }

                @Order(11000)
                public class DescriptionField extends AbstractTextAreaField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Description");
                    }

                    @Override
                    protected double getConfiguredGridWeightY() {
                        return 0;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                }

                @Order(11500)
                public class SourceField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Source0");
                    }

                    @Override
                    protected boolean getConfiguredMandatory() {
                        return true;
                    }

                    @Override
                    protected void execPrepareLookup(ILookupCall<Long> call) {
                        super.execPrepareLookup(call);

                        LeadSourceLookupCall c = (LeadSourceLookupCall) call;
                        c.setProjectId(getProjectId());
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return LeadSourceLookupCall.class;
                    }
                }

                @Order(11750)
                public class StatusField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Status");
                    }

                    @Override
                    protected boolean getConfiguredMandatory() {
                        return true;
                    }

                    @Override
                    protected void execPrepareLookup(ILookupCall<Long> call) {
                        super.execPrepareLookup(call);

                        LeadStatusLookupCall c = (LeadStatusLookupCall) call;
                        c.setProjectId(getProjectId());
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return LeadStatusLookupCall.class;
                    }
                }

                @Order(12000)
                public class AssignedUserField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Assigned");
                    }

                    @Override
                    protected void execPrepareLookup(ILookupCall<Long> call) {
                        super.execPrepareLookup(call);

                        UserLookupCall c = (UserLookupCall) call;
                        if(getProjectId() != null) {
                            c.setProjectId(getProjectId());
                        }
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return UserLookupCall.class;
                    }
                }

                @Order(13000)
                public class TagsField extends AbstractTagField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Tags");
                    }
                }

            }

            @Order(2000)
            public class TasksBox extends AbstractTasksGroupBox {
                @Override
                public Integer getRelatedId() {
                    return getLeadId();
                }

                @Override
                protected boolean getConfiguredVisible() {
                    return false;
                }

                @Override
                public Integer getRelatedType() {
                    return Related.LEAD;
                }

                @Override
                protected void reloadTasks() {
                    fetchTasks();
                }
            }

            @Order(2500)
            public class RemindersBox extends AbstractRemindersGroupBox {
                @Override
                public Integer getRelatedId() {
                    return LeadForm.this.getLeadId();
                }

                @Override
                protected boolean getConfiguredVisible() {
                    return false;
                }

                @Override
                public Integer getRelatedType() {
                    return Related.LEAD;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Reminders");
                }

                @Override
                protected void execInitField() {
                    getRemindersTableField().getTable().addTableListener(new TableListener() {

                        @Override
                        public void tableChanged(TableEvent e) {
                            setLabels();
                        }
                    });
                }
            }


            @Order(3000)
            public class AttachmentsBox extends AbstractAttachmentsBox {
                @Override
                protected boolean getConfiguredVisible() {
                    return false;
                }
            }


        }

        @Order(1750)
        public class MainSidebarTabBox extends AbstractTabBox {
            @Override
            protected int getConfiguredGridW() {
                return 1;
            }
            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }
            @Override
            protected String getConfiguredTabAreaStyle() {
                return TAB_AREA_STYLE_SPREAD_EVEN;
            }

            @Order(0)
            public class NotesBox extends AbstractSidebarNotesGroupBox {

                @Override
                public Integer getRelatedId() {
                    return getLeadId();
                }

                @Override
                public Integer getRelatedType() {
                    return Related.LEAD;
                }
            }

            @Order(1000)
            public class ActivityLogBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("ActivityLog");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(1000)
                public class ActivityLogTableField extends AbstractTableField<ActivityLogTableField.Table> {
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
                        protected boolean getConfiguredHeaderVisible() {
                            return false;
                        }
                    }
                }
            }
        }
        @Order(2000)
        public class ConvertToCustomerButton extends AbstractButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("ConvertToCustomer");
            }

            @Override
            protected String getConfiguredCssClass() {
                return "greenbutton";
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.UserPlus;
            }

            @Override
            protected boolean getConfiguredVisible() {
                return true;
            }

            @Override
            protected void execClickAction() {
                LeadToClientForm form = new LeadToClientForm();
                form.setLeadId(getLeadId());
                form.startNew();
                form.waitFor();
                if (form.isFormStored()) {
                    setClientId(form.getClientId());

                    NotificationHelper.showSaveSuccessNotification();

                    renderForm();
                }

            }
        }

        @Order(1750)
        public class SaveButton extends AbstractSaveButton {

        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("SaveAndClose");
            }
        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {

        }
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            LeadFormData formData = new LeadFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            LeadFormData formData = new LeadFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadService.class).create(formData);
            importFormData(formData);

            MenuUtility.getMenuByClass(getMainBox(), ActionsMenu.class).setVisible(true);
            getTasksBox().setVisible(true);
            renderForm();
            setLabels();
        }
    }

    public class ModifyHandler extends AbstractFormHandler {

        @Override
        protected boolean getConfiguredOpenExclusive() {
            return true;
        }

        @Override
        protected void execLoad() {
            LeadFormData formData = new LeadFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            getTasksBox().setVisible(true);
            getMainInformationsBox().getFields().forEach(f -> f.setEnabledGranted(false));

            getRemindersBox().setRelatedType(Related.LEAD);
            getRemindersBox().setRelatedId(getLeadId());
            getRemindersBox().fetchReminders();

            MenuUtility.getMenuByClass(getMainBox(), ActionsMenu.class).setVisible(true);
            getNotesBox().fetchNotes();
            renderForm();
            setLabels();
        }

        @Override
        protected void execStore() {
            LeadFormData formData = new LeadFormData();
            exportFormData(formData);
            formData = BEANS.get(ILeadService.class).store(formData);
            importFormData(formData);
        }
    }

    public void renderForm() {
        //Convert to customer button
        getConvertToCustomerButton().setVisible(!isLost() && getLeadId() != null && getClientId() == null);

        MenuUtility.getMenuByClass(getMainBox(), MarkAsLostMenu.class).setVisible(!isLost() && getLeadId() != null);
        MenuUtility.getMenuByClass(getMainBox(), MarkAsNotLost.class).setVisible(isLost() && getLeadId() != null);

        //Lead is lost?
        INotification lostNotification = BEANS.get(FormNotificationHelper.class).createWarningNotification(TEXTS.get("ThisLeadIsMarkedAsLost"));
        getMainInformationsBox().setNotification(getClientId() == null && isLost() && getLeadId() != null ? lostNotification : null);

        //Lead is converted?
        INotification convertedNotification = BEANS.get(FormNotificationHelper.class).createSuccessNotification(TEXTS.get("ThisLeadIsConvertedToClient"));
        getMainInformationsBox().setNotification(getClientId() != null && getLeadId() != null ? convertedNotification : null);

        MenuUtility.getMenuByClass(getMainBox(), SendEmailMenu.class).setVisible(getLeadId() != null);
        MenuUtility.getMenuByClass(getMainBox(), AddNoteMenu.class).setVisible(getLeadId() != null);
        getTasksBox().setVisible(getLeadId() != null);
        getRemindersBox().setVisible(getLeadId() != null);
        getAttachmentsBox().setVisible(getLeadId() != null);

        MenuUtility.getMenuByClass(getNotesBox().getNotesTableField().getTable(), AddMenu.class).setEnabled(getLeadId() != null);

        if (getLeadId() != null) {
            setTitle(getNameField().getValue());
            setSubTitle(getCompanyField().getValue());
        }
    }

    public void fetchTasks() {
        List<TasksTableRowData> rows = BEANS.get(ILeadService.class).fetchTasks(getLeadId());
        getTasksBox().getTasksTableField().getTable().importFromTableRowBeanData(rows, TasksTableRowData.class);
    }

}
