package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.projects.ManageUsersForm.MainBox.CancelButton;
import com.velebit.anippe.client.projects.ManageUsersForm.MainBox.GroupBox;
import com.velebit.anippe.client.projects.ManageUsersForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.IManageUsersService;
import com.velebit.anippe.shared.projects.ManageUsersFormData;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = ManageUsersFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ManageUsersForm extends AbstractForm {

    private Integer projectId;

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ManageUsers");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Users1;
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

    public GroupBox.UsersBox getUsersBox() {
        return getFieldByClass(GroupBox.UsersBox.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("SelectUsersForProject");
    }

    @Override
    protected boolean getConfiguredAskIfNeedSave() {
        return false;
    }

    @Override
    protected boolean execValidate() {
        if (getUsersBox().getCheckedKeyCount() < 1) {
            NotificationHelper.showErrorNotification(TEXTS.get("AtLeastOneUserHasToBeAssignedForProject"));
            return false;
        }

        return true;
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 500;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class UsersBox extends AbstractListBox<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Users");
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
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

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ManageUsersFormData formData = new ManageUsersFormData();
            exportFormData(formData);
            formData = BEANS.get(IManageUsersService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            getUsersBox().touch();
        }

        @Override
        protected void execStore() {
            ManageUsersFormData formData = new ManageUsersFormData();
            exportFormData(formData);
            formData = BEANS.get(IManageUsersService.class).create(formData);
            importFormData(formData);
        }
    }

}
