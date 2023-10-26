package com.velebit.anippe.client.knowledgebase;

import com.velebit.anippe.client.common.fields.texteditor.AbstractTextEditorField;
import com.velebit.anippe.client.knowledgebase.ArticleForm.MainBox.CancelButton;
import com.velebit.anippe.client.knowledgebase.ArticleForm.MainBox.GroupBox;
import com.velebit.anippe.client.knowledgebase.ArticleForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.knowledgebase.ArticleFormData;
import com.velebit.anippe.shared.knowledgebase.CategoryLookupCall;
import com.velebit.anippe.shared.knowledgebase.IArticleService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = ArticleFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ArticleForm extends AbstractForm {

    private Integer articleId;
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
    public Integer getArticleId() {
        return articleId;
    }

    @FormData
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }


    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Article");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Book;
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

    public GroupBox.CategoryField getCategoryField() {
        return getFieldByClass(GroupBox.CategoryField.class);
    }

    public GroupBox.ContentSequenceBox.ContentField getContentField() {
        return getFieldByClass(GroupBox.ContentSequenceBox.ContentField.class);
    }

    public GroupBox.ContentSequenceBox getContentSequenceBox() {
        return getFieldByClass(GroupBox.ContentSequenceBox.class);
    }

    public GroupBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DescriptionField.class);
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

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(1250)
            public class DescriptionField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Description");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }


            @Order(1500)
            public class CategoryField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Category");
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return CategoryLookupCall.class;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    CategoryLookupCall c = (CategoryLookupCall) call;
                    if(getProjectId() != null) {
                        c.setProjectId(getProjectId());
                    }

                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }
            }

            @Order(1750)
            public class ContentSequenceBox extends org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Content");
                }

                @Override
                protected boolean getConfiguredAutoCheckFromTo() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 15;
                }

                @Order(2000)
                public class ContentField extends AbstractTextEditorField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Content");
                    }

                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredMandatory() {
                        return true;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 5;
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
            ArticleFormData formData = new ArticleFormData();
            exportFormData(formData);
            formData = BEANS.get(IArticleService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ArticleFormData formData = new ArticleFormData();
            exportFormData(formData);
            formData = BEANS.get(IArticleService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ArticleFormData formData = new ArticleFormData();
            exportFormData(formData);
            formData = BEANS.get(IArticleService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ArticleFormData formData = new ArticleFormData();
            exportFormData(formData);
            formData = BEANS.get(IArticleService.class).store(formData);
            importFormData(formData);
        }
    }
}
