package com.velebit.anippe.client.knowledgebase;

import com.velebit.anippe.client.knowledgebase.CategoryForm.MainBox.CancelButton;
import com.velebit.anippe.client.knowledgebase.CategoryForm.MainBox.GroupBox;
import com.velebit.anippe.client.knowledgebase.CategoryForm.MainBox.OkButton;
import com.velebit.anippe.shared.knowledgebase.CategoryFormData;
import com.velebit.anippe.shared.knowledgebase.CreateCategoryPermission;
import com.velebit.anippe.shared.knowledgebase.ICategoryService;
import com.velebit.anippe.shared.knowledgebase.UpdateCategoryPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = CategoryFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CategoryForm extends AbstractForm {

    private Integer categoryId;

    private Integer projectId;

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @FormData
    public Integer getCategoryId() {
        return categoryId;
    }

    @FormData
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("NewCategory");
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
            CategoryFormData formData = new CategoryFormData();
            exportFormData(formData);
            formData = BEANS.get(ICategoryService.class).prepareCreate(formData);
            importFormData(formData);

            setEnabledPermission(new CreateCategoryPermission());
        }

        @Override
        protected void execStore() {
            CategoryFormData formData = new CategoryFormData();
            exportFormData(formData);
            formData = BEANS.get(ICategoryService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            CategoryFormData formData = new CategoryFormData();
            exportFormData(formData);
            formData = BEANS.get(ICategoryService.class).load(formData);
            importFormData(formData);

            setEnabledPermission(new UpdateCategoryPermission());
        }

        @Override
        protected void execStore() {
            CategoryFormData formData = new CategoryFormData();
            exportFormData(formData);
            formData = BEANS.get(ICategoryService.class).store(formData);
            importFormData(formData);
        }
    }
}
