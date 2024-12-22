package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tasks.SelectUserListBoxForm.MainBox.GroupBox;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tasks.ISelectUserListBoxService;
import com.velebit.anippe.shared.tasks.SelectUserListBoxFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.List;

@FormData(value = SelectUserListBoxFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SelectUserListBoxForm extends AbstractForm {

    private Integer projectId;
    private List<Long> userIds;
    private Integer taskId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @FormData
    public Integer getTaskId() {
        return taskId;
    }

    @FormData
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    protected void execStored() {
        super.execStored();

        NotificationHelper.showSaveSuccessNotification();
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("SelectUserListBox");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public MainBox.OkButton getOkButton() {
        return getFieldByClass(MainBox.OkButton.class);
    }

    public GroupBox.UsersListBox getUsersListBox() {
        return getFieldByClass(GroupBox.UsersListBox.class);
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        getUsersListBox().setValue(CollectionUtility.hashSet(getUserIds()));
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 450;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class UsersListBox extends AbstractListBox<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Users");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    if (projectId != null) {
                        UserLookupCall userLookupCall = (UserLookupCall) call;
                        userLookupCall.setProjectId(projectId);
                    }
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }
            }
        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("Save");
            }

            @Override
            protected int getConfiguredHorizontalAlignment() {
                return 1;
            }
        }


    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            SelectUserListBoxFormData formData = new SelectUserListBoxFormData();
            exportFormData(formData);
            formData = BEANS.get(ISelectUserListBoxService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            getUsersListBox().touch();
        }

        @Override
        protected void execStore() {
            SelectUserListBoxFormData formData = new SelectUserListBoxFormData();
            exportFormData(formData);
            formData = BEANS.get(ISelectUserListBoxService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            SelectUserListBoxFormData formData = new SelectUserListBoxFormData();
            exportFormData(formData);
            formData = BEANS.get(ISelectUserListBoxService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            SelectUserListBoxFormData formData = new SelectUserListBoxFormData();
            exportFormData(formData);
            formData = BEANS.get(ISelectUserListBoxService.class).store(formData);
            importFormData(formData);
        }
    }
}
