package com.velebit.anippe.client.email;

import com.velebit.anippe.client.email.EmailForm.MainBox.CancelButton;
import com.velebit.anippe.client.email.EmailForm.MainBox.GroupBox;
import com.velebit.anippe.client.email.EmailForm.MainBox.OkButton;
import com.velebit.anippe.shared.email.CreateEmailPermission;
import com.velebit.anippe.shared.email.EmailFormData;
import com.velebit.anippe.shared.email.IEmailService;
import com.velebit.anippe.shared.email.UpdateEmailPermission;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = EmailFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class EmailForm extends AbstractForm {
    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Email");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Email;
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

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 700;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {

        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {

        }
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            EmailFormData formData = new EmailFormData();
            exportFormData(formData);
            formData = BEANS.get(IEmailService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            EmailFormData formData = new EmailFormData();
            exportFormData(formData);
            formData = BEANS.get(IEmailService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            EmailFormData formData = new EmailFormData();
            exportFormData(formData);
            formData = BEANS.get(IEmailService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            EmailFormData formData = new EmailFormData();
            exportFormData(formData);
            formData = BEANS.get(IEmailService.class).store(formData);
            importFormData(formData);
        }
    }
}
