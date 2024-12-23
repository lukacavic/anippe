package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.fields.texteditor.AbstractTextEditorField;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.lookups.RelatedLookupCall;
import com.velebit.anippe.client.tasks.TaskForm.MainBox.AttachmentsBox;
import com.velebit.anippe.client.tasks.TaskForm.MainBox.AttachmentsBox.AttachmentsTableField;
import com.velebit.anippe.client.tasks.TaskForm.MainBox.AttachmentsBox.ToolbarSequenceBox;
import com.velebit.anippe.client.tasks.TaskForm.MainBox.CancelButton;
import com.velebit.anippe.client.tasks.TaskForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.TaskForm.MainBox.OkButton;
import com.velebit.anippe.shared.clients.ClientLookupCall;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.constants.Constants.Priority;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.ProjectLookupCall;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tasks.ITaskService;
import com.velebit.anippe.shared.tasks.TaskFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.internal.HorizontalGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.mode.AbstractMode;
import org.eclipse.scout.rt.client.ui.form.fields.modeselector.AbstractModeSelectorField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.form.fields.tagfield.AbstractTagField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Date;
import java.util.List;

@FormData(value = TaskFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TaskForm extends AbstractForm {

    private Integer taskId;

    private Integer projectId;
    private Long relatedId;
    private Integer relatedType;
    private Integer statusId;

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    protected void execStored() {
        super.execStored();

        NotificationHelper.showSaveSuccessNotification();
    }

    @FormData
    public Integer getStatusId() {
        return statusId;
    }

    @FormData
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @FormData
    public Integer getTaskId() {
        return taskId;
    }

    @FormData
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @FormData
    public Long getRelatedId() {
        return relatedId;
    }

    @FormData
    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;

        try {
            getProjectField().setValueChangeTriggerEnabled(false);
            getProjectField().setValue(relatedId);
        } finally {
            getProjectField().setValueChangeTriggerEnabled(true);
        }
    }

    @FormData
    public Integer getRelatedType() {
        return relatedType;
    }

    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;

        if (getRelatedField().isFieldChanging()) return;

        try {
            getRelatedField().setValueChangeTriggerEnabled(false);
            getRelatedField().setValue(relatedType);
        } finally {
            getRelatedField().setValueChangeTriggerEnabled(true);
        }

        switchRelatedLookupCall(relatedType);
    }

    public void switchRelatedLookupCall(Integer relatedType) {
        getProjectField().setVisible(relatedType != null);

        if (relatedType != null) {
            if (relatedType.equals(Constants.Related.CLIENT)) {
                ClientLookupCall call = new ClientLookupCall();
                getProjectField().setLookupCall(call);
                getProjectField().setLabel(TEXTS.get("Client"));
            } else if (relatedType.equals(Constants.Related.PROJECT)) {
                ProjectLookupCall call = new ProjectLookupCall();
                getProjectField().setLookupCall(call);
                getProjectField().setLabel(TEXTS.get("Project"));
            }
        }
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Tasks;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Task");
    }

    public GroupBox.AssignedUsersBox getAssignedUsersBox() {
        return getFieldByClass(GroupBox.AssignedUsersBox.class);
    }

    public MainBox.AttachmentsBox getAttachmentsBox() {
        return getFieldByClass(AttachmentsBox.class);
    }

    public AttachmentsTableField getAttachmentsTableField() {
        return getFieldByClass(AttachmentsTableField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public GroupBox.DeadlineAtField getDeadlineAtField() {
        return getFieldByClass(GroupBox.DeadlineAtField.class);
    }

    public GroupBox.DescriptionBox getDescriptionBox() {
        return getFieldByClass(GroupBox.DescriptionBox.class);
    }

    public GroupBox.DescriptionBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DescriptionBox.DescriptionField.class);
    }

    public ToolbarSequenceBox.FileField getFileField() {
        return getFieldByClass(ToolbarSequenceBox.FileField.class);
    }

    public GroupBox.FollowersField getFollowersField() {
        return getFieldByClass(GroupBox.FollowersField.class);
    }

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    public GroupBox.PriorityField getPriorityField() {
        return getFieldByClass(GroupBox.PriorityField.class);
    }

    public GroupBox.StartAtField getStartAtField() {
        return getFieldByClass(GroupBox.StartAtField.class);
    }

    public GroupBox.RelatedBox.ProjectField getProjectField() {
        return getFieldByClass(GroupBox.RelatedBox.ProjectField.class);
    }

    public GroupBox.RelatedBox getRelatedBox() {
        return getFieldByClass(GroupBox.RelatedBox.class);
    }

    public GroupBox.RelatedBox.RelatedField getRelatedField() {
        return getFieldByClass(GroupBox.RelatedBox.RelatedField.class);
    }

    public GroupBox.TagsField getTagsField() {
        return getFieldByClass(GroupBox.TagsField.class);
    }

    public ToolbarSequenceBox getToolbarSequenceBox() {
        return getFieldByClass(ToolbarSequenceBox.class);
    }

    public ToolbarSequenceBox.UploadButton getUploadButton() {
        return getFieldByClass(ToolbarSequenceBox.UploadButton.class);
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startView() {
        startInternalExclusive(new ViewHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Override
        protected int getConfiguredWidthInPixel() {
            return 700;
        }

        @Order(1000)
        public class ToggleAttachmentsMenu extends AbstractMenu {
            @Override
            protected int getConfiguredActionStyle() {
                return ACTION_STYLE_BUTTON;
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Paperclip;
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Override
            protected boolean getConfiguredToggleAction() {
                return true;
            }

            @Override
            protected void execSelectionChanged(boolean selection) {
                super.execSelectionChanged(selection);

                getAttachmentsBox().setVisible(selection);
            }
        }

        @Order(900)
        public class AttachmentsBox extends org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox {
            @Override
            public boolean isLabelVisible() {
                return false;
            }

            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }

            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Order(1000)
            public class ToolbarSequenceBox extends org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                protected boolean getConfiguredAutoCheckFromTo() {
                    return false;
                }

                @Order(-1000)
                public class FileField extends AbstractFileChooserField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("AttachFiles");
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }
                }

                @Order(98765432123457984d)
                public class UploadButton extends AbstractButton {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Upload");
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Paperclip;
                    }

                    @Override
                    protected Boolean getConfiguredDefaultButton() {
                        return true;
                    }

                    @Override
                    public boolean isProcessButton() {
                        return false;
                    }

                    @Override
                    protected void execClickAction() {
                        FileChooser chooser = new FileChooser(true);
                        List<BinaryResource> files = chooser.startChooser();

                        if (CollectionUtility.isEmpty(files)) return;

                        for (BinaryResource resource : files) {

                        }
                    }
                }
            }


            @Order(2000)
            public class AttachmentsTableField extends AbstractTableField<AttachmentsTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 3;
                }

                @ClassId("ffbdda6c-2a1e-4725-8817-cc265916281d")
                public class Table extends AbstractTable {
                    @Override
                    protected boolean getConfiguredHeaderVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredHeaderEnabled() {
                        return false;
                    }

                    @Order(1000)
                    public class DeleteMenu extends AbstractDeleteMenu {

                        @Override
                        protected void execAction() {

                        }
                    }
                }
            }
        }


        @Order(1000)
        public class GroupBox extends AbstractGroupBox {


            @Override
            protected void execInitField() {
                super.execInitField();

                getFields().forEach(f -> f.setLabelPosition(LABEL_POSITION_TOP));
                getFields().forEach(f -> f.setFieldStyle(FIELD_STYLE_CLASSIC));
                getFields().forEach(f -> f.setStatusVisible(false));

            }

            @Override
            protected int getConfiguredGridColumnCount() {
                return 2;
            }

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Override
            protected Class<? extends IGroupBoxBodyGrid> getConfiguredBodyGrid() {
                return HorizontalGroupBoxBodyGrid.class;
            }

            @Order(1000)
            public class NameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Name");
                }

                @Override
                protected void execInitField() {
                    super.execInitField();

                    requestFocus();
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(1500)
            public class PriorityField extends AbstractModeSelectorField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Priority");
                }

                @Override
                protected String getConfiguredFieldStyle() {
                    return FIELD_STYLE_CLASSIC;
                }

                @Override
                protected void execInitField() {
                    setValue(Constants.Priority.NORMAL.longValue());
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Order(10)
                public class LowMode extends AbstractMode<Long> {
                    @Override
                    protected String getConfiguredText() {
                        return TEXTS.get("Low");
                    }

                    @Override
                    protected Long getConfiguredRef() {
                        return Priority.LOW.longValue();
                    }
                }

                @Order(20)
                public class NormalMode extends AbstractMode<Long> {
                    @Override
                    protected String getConfiguredText() {
                        return TEXTS.get("Normal");
                    }

                    @Override
                    protected Long getConfiguredRef() {
                        return Priority.NORMAL.longValue();
                    }
                }

                @Order(30)
                public class HighMode extends AbstractMode<Long> {
                    @Override
                    protected String getConfiguredText() {
                        return TEXTS.get("High");
                    }

                    @Override
                    protected Long getConfiguredRef() {
                        return Priority.HIGH.longValue();
                    }
                }

                @Order(40)
                public class UrgentMode extends AbstractMode<Long> {
                    @Override
                    protected String getConfiguredText() {
                        return TEXTS.get("Urgent");
                    }

                    @Override
                    protected Long getConfiguredRef() {
                        return Priority.URGENT.longValue();
                    }
                }
            }

            @Order(1750)
            public class StartAtField extends AbstractDateField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("StartAt");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected Date execValidateValue(Date rawValue) {
                    if (rawValue == null) return new Date();

                    if (getDeadlineAtField().getValue() == null) return rawValue;

                    if (rawValue.after(getDeadlineAtField().getValue())) {
                        throw new VetoException(TEXTS.get("StartDateAfterDeadline"));
                    }

                    return rawValue;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }
            }

            @Order(1875)
            public class DeadlineAtField extends AbstractDateField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("DeadlineAt");
                }

                @Override
                protected Date execValidateValue(Date rawValue) {
                    if (rawValue == null) return null;

                    if (getStartAtField().getValue() == null) return rawValue;

                    if (rawValue.before(getStartAtField().getValue())) {
                        throw new VetoException(TEXTS.get("DeadlineBeforeStartDate"));
                    }

                    return rawValue;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }
            }

            @Order(1906)
            public class RelatedBox extends AbstractGroupBox {
                @Override
                protected int getConfiguredGridColumnCount() {
                    return 2;
                }

                @Override
                protected boolean getConfiguredVisible() {
                    return false;
                }

                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                public boolean isBorderVisible() {
                    return false;
                }

                @Order(1000)
                public class RelatedField extends AbstractSmartField<Integer> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Related");
                    }

                    @Override
                    protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                        return RelatedLookupCall.class;
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 90;
                    }

                    @Override
                    protected void execChangedValue() {
                        super.execChangedValue();

                        setRelatedType(getValue());
                    }
                }

                @Order(2000)
                public class ProjectField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Project");
                    }

                    @Override
                    protected boolean getConfiguredVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredMasterRequired() {
                        return true;
                    }

                    @Override
                    protected Class<? extends IValueField> getConfiguredMasterField() {
                        return RelatedField.class;
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 90;
                    }

                    @Override
                    protected void execChangedValue() {
                        super.execChangedValue();

                        setRelatedId(getValue());
                    }
                }
            }

            @Order(1937)
            public class AssignedUsersBox extends AbstractListBox<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Assigned");
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                protected void execInitField() {
                    super.execInitField();

                    setValue(CollectionUtility.hashSet(ClientSession.get().getCurrentUser().getId().longValue()));
                }

                @Override
                protected int getConfiguredGridH() {
                    return 3;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    if (getProjectId() != null) {
                        UserLookupCall c = (UserLookupCall) call;
                        c.setProjectId(getProjectId());
                    }
                }
            }

            @Order(1960)
            public class FollowersField extends AbstractListBox<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Followers");
                }

                @Override
                protected int getConfiguredGridH() {
                    return 3;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    if (getProjectId() != null) {
                        UserLookupCall c = (UserLookupCall) call;
                        c.setProjectId(getProjectId());
                    }
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }
            }

            @Order(1984)
            public class TagsField extends AbstractTagField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Tags");
                }

                @Override
                public boolean isVisible() {
                    return false;
                }

                @Override
                public String getFieldStyle() {
                    return FIELD_STYLE_CLASSIC;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }
            }

            @Order(1992)
            public class DescriptionBox extends AbstractSequenceBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Description");
                }

                @Override
                protected boolean getConfiguredAutoCheckFromTo() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 5;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Order(2000)
                public class DescriptionField extends AbstractTextEditorField {
                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 5;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                }
            }
        }


        @Order(2000)
        public class OkButton extends AbstractOkButton {
            @Override
            protected int getConfiguredHorizontalAlignment() {
                return 1;
            }
        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {
            @Override
            protected int getConfiguredHorizontalAlignment() {
                return 1;
            }

        }
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TaskFormData formData = new TaskFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            getRelatedField().setValue(getRelatedType());
        }

        @Override
        protected void execStore() {
            TaskFormData formData = new TaskFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TaskFormData formData = new TaskFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TaskFormData formData = new TaskFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskService.class).store(formData);
            importFormData(formData);
        }
    }

    public class ViewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TaskFormData formData = new TaskFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskService.class).load(formData);
            importFormData(formData);
        }

    }
}
