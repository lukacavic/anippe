package com.velebit.anippe.client.knowledgebase;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.knowledgebase.CategoriesForm.MainBox.CancelButton;
import com.velebit.anippe.client.knowledgebase.CategoriesForm.MainBox.GroupBox;
import com.velebit.anippe.client.knowledgebase.CategoriesForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.knowledgebase.CategoriesFormData;
import com.velebit.anippe.shared.knowledgebase.CategoryFormData;
import com.velebit.anippe.shared.knowledgebase.ICategoriesService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

@FormData(value = CategoriesFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CategoriesForm extends AbstractForm {

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
        return TEXTS.get("Categories");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.FolderOpen;
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

    public GroupBox.CategoriesTableField getCategoriesTableField() {
        return getFieldByClass(GroupBox.CategoriesTableField.class);
    }

    public void fetchCategories() {
        List<CategoriesFormData.CategoriesTable.CategoriesTableRowData> rows = BEANS.get(ICategoriesService.class).fetchCategories(getProjectId());
        getCategoriesTableField().getTable().importFromTableRowBeanData(rows, CategoriesFormData.CategoriesTable.CategoriesTableRowData.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 1000;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Order(1000)
            public class AddMenu extends AbstractAddMenu {

                @Override
                protected void execAction() {
                    CategoryForm form = new CategoryForm();
                    form.setProjectId(getProjectId());
                    form.startNew();
                    form.waitFor();
                }
            }

            @Order(1000)
            public class CategoriesTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<CategoriesTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @ClassId("feb3b83f-bde9-497c-b675-aba03a993b76")
                public class Table extends AbstractTable {

                    @Order(1000)
                    public class EditMenu extends AbstractEditMenu {

                        @Override
                        protected void execAction() {

                        }
                    }

                    @Order(2000)
                    public class DeleteMenu extends AbstractDeleteMenu {

                        @Override
                        protected void execAction() {

                        }
                    }

                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
                    }

                    public NameColumn getNameColumn() {
                        return getColumnSet().getColumnByClass(NameColumn.class);
                    }

                    @Order(1000)
                    public class CategoryIdColumn extends AbstractIDColumn {

                    }

                    @Order(2000)
                    public class NameColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Name");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
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
            CategoriesFormData formData = new CategoriesFormData();
            exportFormData(formData);
            formData = BEANS.get(ICategoriesService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            CategoriesFormData formData = new CategoriesFormData();
            exportFormData(formData);
            formData = BEANS.get(ICategoriesService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            CategoriesFormData formData = new CategoriesFormData();
            exportFormData(formData);
            formData = BEANS.get(ICategoriesService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            CategoriesFormData formData = new CategoriesFormData();
            exportFormData(formData);
            formData = BEANS.get(ICategoriesService.class).store(formData);
            importFormData(formData);
        }
    }
}
