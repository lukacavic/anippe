package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.fields.texteditor.AbstractTextEditorField;
import com.velebit.anippe.client.projects.PredefinedReplyForm.MainBox.CancelButton;
import com.velebit.anippe.client.projects.PredefinedReplyForm.MainBox.GroupBox;
import com.velebit.anippe.client.projects.PredefinedReplyForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.IPredefinedReplyService;
import com.velebit.anippe.shared.projects.PredefinedReplyFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = PredefinedReplyFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class PredefinedReplyForm extends AbstractForm {

    private Integer predefinedReplyId;
    private Integer projectId;

    @FormData
    public Integer getPredefinedReplyId() {
        return predefinedReplyId;
    }

    @FormData
    public void setPredefinedReplyId(Integer predefinedReplyId) {
        this.predefinedReplyId = predefinedReplyId;
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
    protected String getConfiguredTitle() {
        return TEXTS.get("PredefinedReply");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.UserCheck;
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

    public GroupBox.ContentSequenceBox.ContentField getContentField() {
        return getFieldByClass(GroupBox.ContentSequenceBox.ContentField.class);
    }

    public GroupBox.ContentSequenceBox getContentSequenceBox() {
        return getFieldByClass(GroupBox.ContentSequenceBox.class);
    }

    public GroupBox.TitleField getTitleField() {
        return getFieldByClass(GroupBox.TitleField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Order(1000)
            public class TitleField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Title");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }
            }

            @Order(1500)
            public class ContentSequenceBox extends AbstractSequenceBox {

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Content");
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @Order(2000)
                public class ContentField extends AbstractTextEditorField {
                    @Override
                    protected int getConfiguredGridH() {
                        return 6;
                    }

                    @Override
                    protected boolean getConfiguredMandatory() {
                        return true;
                    }
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

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            PredefinedReplyFormData formData = new PredefinedReplyFormData();
            exportFormData(formData);
            formData = BEANS.get(IPredefinedReplyService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            PredefinedReplyFormData formData = new PredefinedReplyFormData();
            exportFormData(formData);
            formData = BEANS.get(IPredefinedReplyService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            PredefinedReplyFormData formData = new PredefinedReplyFormData();
            exportFormData(formData);
            formData = BEANS.get(IPredefinedReplyService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            PredefinedReplyFormData formData = new PredefinedReplyFormData();
            exportFormData(formData);
            formData = BEANS.get(IPredefinedReplyService.class).store(formData);
            importFormData(formData);
        }
    }
}
