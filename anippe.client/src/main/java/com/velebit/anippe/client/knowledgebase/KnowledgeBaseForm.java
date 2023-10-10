package com.velebit.anippe.client.knowledgebase;

import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.knowledgebase.KnowledgeBaseForm.MainBox.GroupBox;
import com.velebit.anippe.shared.beans.Article;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.knowledgebase.IKnowledgeBaseService;
import com.velebit.anippe.shared.knowledgebase.KnowledgeBaseFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.ITableTileGridMediator;
import org.eclipse.scout.rt.client.ui.basic.table.ITableTileGridMediatorProvider;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.mode.AbstractMode;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.tile.AbstractHtmlTile;
import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

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

    public GroupBox.ArticlesTableField getArticlesTableField() {
        return getFieldByClass(GroupBox.ArticlesTableField.class);
    }

    public GroupBox.ToolbarSequenceBox.CategoriesButton getCategoriesButton() {
        return getFieldByClass(GroupBox.ToolbarSequenceBox.CategoriesButton.class);
    }

    public MainBox.GroupBox.FilterModeSelectorField getFilterModeSelectorField() {
        return getFieldByClass(MainBox.GroupBox.FilterModeSelectorField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.SearchField getSearchField() {
        return getFieldByClass(GroupBox.SearchField.class);
    }

    public GroupBox.ToolbarSequenceBox getToolbarSequenceBox() {
        return getFieldByClass(GroupBox.ToolbarSequenceBox.class);
    }

    public void fetchArticles() {
        List<KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData> rows = BEANS.get(IKnowledgeBaseService.class).fetchArticles();
        getArticlesTableField().getTable().importFromTableRowBeanData(rows, KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(-1000)
            public class ToolbarSequenceBox extends AbstractSequenceBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredAutoCheckFromTo() {
                    return false;
                }

                @Order(0)
                public class AddArticleButton extends AbstractButton {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("AddArticle");
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    public boolean isProcessButton() {
                        return false;
                    }

                    @Override
                    protected Boolean getConfiguredDefaultButton() {
                        return true;
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Plus;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected void execClickAction() {
                        ArticleForm form = new ArticleForm();
                        form.setProjectId(getRelatedId());
                        form.startNew();
                        form.waitFor();
                        if (form.isFormStored()) {
                            fetchArticles();
                        }
                    }
                }

                @Order(1000)
                public class CategoriesButton extends AbstractButton {
                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.FolderOpen;
                    }

                    @Override
                    protected void execClickAction() {
                        CategoriesForm form = new CategoriesForm();
                        form.setProjectId(getRelatedId());
                        form.startNew();
                        form.waitFor();
                    }
                }
            }

            @Order(1)
            public class FilterModeSelectorField extends org.eclipse.scout.rt.client.ui.form.fields.modeselector.AbstractModeSelectorField<java.lang.Long> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected void execChangedValue() {
                    super.execChangedValue();

                    fetchArticles();
                }

                @Override
                protected void execInitField() {
                    super.execInitField();

                    setValue(1L);
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Order(1000)
                @ClassId("dfcab0df-7d6b-41ab-a6b3-ffc098adfbeb")
                public class AllArticles extends AbstractMode<java.lang.Long> {
                    @Override
                    protected String getConfiguredText() {
                        return TEXTS.get("AllArticles");
                    }

                    @Override
                    protected Long getConfiguredRef() {
                        return 1L;
                    }
                }

                @Order(2000)
                @ClassId("6f8964b2-4f76-433a-8e2d-dc934021e797")
                public class MyArticles extends AbstractMode<java.lang.Long> {
                    @Override
                    protected String getConfiguredText() {
                        return TEXTS.get("MyArticles");
                    }

                    @Override
                    protected Long getConfiguredRef() {
                        return 2L;
                    }
                }
            }

            @Order(500)
            public class SearchField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Search");
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_ON_FIELD;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Override
            protected int getConfiguredGridColumnCount() {
                return 4;
            }

            @Order(1000)
            public class ArticlesTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<ArticlesTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 4;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @ClassId("8107389c-6a12-4973-bacd-48d5c22bec80")
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
                    protected ITile execCreateTile(ITableRow row) {
                        return new AbstractHtmlTile() {
                            @Override
                            public String getContent() {
                                return getArticleColumn().getValue(row).getTitle();
                            }
                        };
                    }

                    @Override
                    protected ITableTileGridMediator createTableTileGridMediator() {
                        ITableTileGridMediator mediator = BEANS.get(ITableTileGridMediatorProvider.class).createTableTileGridMediator(this);
                        mediator.setGridColumnCount(5);
                        mediator.setWithPlaceholders(true);

                        return mediator;
                    }

                    public ArticleColumn getArticleColumn() {
                        return getColumnSet().getColumnByClass(ArticleColumn.class);
                    }

                    @Override
                    protected boolean getConfiguredTileMode() {
                        return true;
                    }

                    @Order(1000)
                    public class ArticleColumn extends AbstractColumn<Article> {
                        @Override
                        protected boolean getConfiguredDisplayable() {
                            return false;
                        }
                    }
                }
            }
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
