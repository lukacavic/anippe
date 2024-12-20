package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.tasks.SelectUserListBoxForm.MainBox.GroupBox;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tasks.CreateSelectUserListBoxPermission;
import com.velebit.anippe.shared.tasks.ISelectUserListBoxService;
import com.velebit.anippe.shared.tasks.SelectUserListBoxFormData;
import com.velebit.anippe.shared.tasks.UpdateSelectUserListBoxPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
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

    private List<Long> userIds;

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
                protected int getConfiguredGridH() {
                    return 6;
                }
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
