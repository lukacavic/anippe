package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.client.lookups.RelatedLookupCall;
import com.velebit.anippe.client.tasks.TaskForm.MainBox.CancelButton;
import com.velebit.anippe.client.tasks.TaskForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.TaskForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tasks.ITaskService;
import com.velebit.anippe.shared.tasks.TaskFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.internal.VerticalSmartGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.form.fields.tagfield.AbstractTagField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Date;

@FormData(value = TaskFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TaskForm extends AbstractForm {

    private Integer taskId;

    private Integer relatedId;
    private Integer relatedType;
    private Integer statusId;

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
    public Integer getRelatedId() {
        return relatedId;
    }

    @FormData
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    @FormData
    public Integer getRelatedType() {
        return relatedType;
    }

    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
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

    public GroupBox.AttachmentsTableField getAttachmentsTableField() {
        return getFieldByClass(GroupBox.AttachmentsTableField.class);
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

    public GroupBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DescriptionField.class);
    }

    public GroupBox.FollowersBox getFollowersBox() {
        return getFieldByClass(GroupBox.FollowersBox.class);
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
            return 900;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
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
                return VerticalSmartGroupBoxBodyGrid.class;
            }

            @Order(1000)
            public class ToggleAttachmentsMenu extends AbstractMenu {
                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Paperclip;
                }

                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("AttachFiles");
                }

                @Override
                protected byte getConfiguredHorizontalAlignment() {
                    return 1;
                }

                @Override
                protected void execAction() {
                    getAttachmentsTableField().setVisible(!getAttachmentsTableField().isVisible());
                }
            }

            @Order(0)
            public class AttachmentsTableField extends AbstractTableField<AttachmentsTableField.Table> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Attachments");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected boolean getConfiguredVisible() {
                    return false;
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
                    protected boolean getConfiguredHeaderEnabled() {
                        return false;
                    }
                }
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
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(1500)
            public class PriorityField extends AbstractSmartField<Integer> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Priority");
                }

                @Override
                protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                    return PriorityLookupCall.class;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }
            }

            @Order(1750)
            public class StartAtField extends AbstractDateTimeField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("StartAt");
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
            public class DeadlineAtField extends AbstractDateTimeField {
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
                }

                @Order(2000)
                public class ProjectField extends AbstractSmartField<Integer> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Project");
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
                    return 2;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 3;
                }
            }

            @Order(1968)
            public class FollowersBox extends AbstractListBox<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Followers");
                }
                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }
                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    UserLookupCall c = (UserLookupCall) call;
                    c.setExcludeIds(CollectionUtility.arrayList(ClientSession.get().getCurrentUser().getId().longValue()));
                }

                @Override
                protected int getConfiguredGridH() {
                    return 3;
                }
            }

            @Order(1984)
            public class TagsField extends AbstractTagField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Tags");
                }

                @Override
                protected String getConfiguredFieldStyle() {
                    return FIELD_STYLE_CLASSIC;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }
            }
            @Order(2000)
            public class DescriptionField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Description");
                }

                @Override
                protected int getConfiguredGridH() {
                    return 3;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected boolean getConfiguredWrapText() {
                    return true;
                }

                @Override
                protected boolean getConfiguredMultilineText() {
                    return true;
                }
            }


        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {

        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {

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
