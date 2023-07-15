package com.velebit.anippe.client.documents;

import com.velebit.anippe.client.documents.UploadForm.MainBox.CancelButton;
import com.velebit.anippe.client.documents.UploadForm.MainBox.GroupBox;
import com.velebit.anippe.client.documents.UploadForm.MainBox.OkButton;
import com.velebit.anippe.shared.documents.CreateUploadPermission;
import com.velebit.anippe.shared.documents.IUploadService;
import com.velebit.anippe.shared.documents.UpdateUploadPermission;
import com.velebit.anippe.shared.documents.UploadFormData;
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

@FormData(value = UploadFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class UploadForm extends AbstractForm {
    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Upload");
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

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Paperclip;
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 600;
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
            UploadFormData formData = new UploadFormData();
            exportFormData(formData);
            formData = BEANS.get(IUploadService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            UploadFormData formData = new UploadFormData();
            exportFormData(formData);
            formData = BEANS.get(IUploadService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            UploadFormData formData = new UploadFormData();
            exportFormData(formData);
            formData = BEANS.get(IUploadService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            UploadFormData formData = new UploadFormData();
            exportFormData(formData);
            formData = BEANS.get(IUploadService.class).store(formData);
            importFormData(formData);
        }
    }
}
