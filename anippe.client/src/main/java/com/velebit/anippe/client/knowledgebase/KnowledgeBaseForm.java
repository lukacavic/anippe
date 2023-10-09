package com.velebit.anippe.client.knowledgebase;

import com.velebit.anippe.client.knowledgebase.KnowledgeBaseForm.MainBox.GroupBox;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.knowledgebase.IKnowledgeBaseService;
import com.velebit.anippe.shared.knowledgebase.KnowledgeBaseFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = KnowledgeBaseFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class KnowledgeBaseForm extends AbstractForm {

    private Integer relatedId;
    private Integer relatedType;

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

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Book;
    }

    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("KnowledgeBase");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

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
            KnowledgeBaseFormData formData = new KnowledgeBaseFormData();
            exportFormData(formData);
            formData = BEANS.get(IKnowledgeBaseService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            KnowledgeBaseFormData formData = new KnowledgeBaseFormData();
            exportFormData(formData);
            formData = BEANS.get(IKnowledgeBaseService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            KnowledgeBaseFormData formData = new KnowledgeBaseFormData();
            exportFormData(formData);
            formData = BEANS.get(IKnowledgeBaseService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            KnowledgeBaseFormData formData = new KnowledgeBaseFormData();
            exportFormData(formData);
            formData = BEANS.get(IKnowledgeBaseService.class).store(formData);
            importFormData(formData);
        }
    }
}
