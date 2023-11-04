package com.velebit.anippe.client.knowledgebase;

import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.common.menus.AbstractSendEmailMenu;
import com.velebit.anippe.client.email.EmailForm;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.knowledgebase.KnowledgeBaseForm.MainBox.GroupBox;
import com.velebit.anippe.client.knowledgebase.KnowledgeBaseForm.MainBox.GroupBox.ContainerBox.AccordionField;
import com.velebit.anippe.client.knowledgebase.KnowledgeBaseForm.MainBox.GroupBox.ContainerBox.AccordionField.ArticlesAccordion;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.knowledgebase.Article;
import com.velebit.anippe.shared.knowledgebase.IArticleService;
import com.velebit.anippe.shared.knowledgebase.IKnowledgeBaseService;
import com.velebit.anippe.shared.knowledgebase.KnowledgeBaseFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.accordionfield.AbstractAccordionField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.internal.HorizontalGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.mode.AbstractMode;
import org.eclipse.scout.rt.client.ui.form.fields.modeselector.AbstractModeSelectorField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.AbstractSplitBox;
import org.eclipse.scout.rt.client.ui.group.AbstractGroup;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileAccordion;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileGrid;
import org.eclipse.scout.rt.client.ui.tile.IHtmlTile;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.data.colorscheme.ColorScheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@FormData(value = KnowledgeBaseFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class KnowledgeBaseForm extends AbstractForm {
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
    protected String getConfiguredIconId() {
        return FontIcons.Book;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("KnowledgeBase");
    }

    public GroupBox.ToolbarSequenceBox.CategoriesButton getCategoriesButton() {
        return getFieldByClass(GroupBox.ToolbarSequenceBox.CategoriesButton.class);
    }

    public AccordionField getAccordionField() {
        return getFieldByClass(AccordionField.class);
    }

    public GroupBox.ContainerBox getContainerBox() {
        return getFieldByClass(GroupBox.ContainerBox.class);
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

    public GroupBox.ContainerBox.PreviewField getPreviewField() {
        return getFieldByClass(GroupBox.ContainerBox.PreviewField.class);
    }

    public GroupBox.SearchField getSearchField() {
        return getFieldByClass(GroupBox.SearchField.class);
    }

    public GroupBox.ToolbarSequenceBox getToolbarSequenceBox() {
        return getFieldByClass(GroupBox.ToolbarSequenceBox.class);
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        fetchArticles();
    }

    public void fetchArticles() {
        Long typeId = ObjectUtility.nvl(getFilterModeSelectorField().getValue(), 1L);

        List<Article> articles = BEANS.get(IKnowledgeBaseService.class).fetchArticles(getProjectId(), typeId);

        int tileCount = 0;
        List<ArticleTile> tiles = new ArrayList<>();
        for (Article article : articles) {
            ArticleTile tile = new ArticleTile(article);
            tile.setArticle(article);
            tile.setColorScheme(ColorScheme.DEFAULT);
            tiles.add(tile);
        }

        getAccordionField().getAccordion().setTiles(tiles);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Override
            protected Class<? extends IGroupBoxBodyGrid> getConfiguredBodyGrid() {
                return HorizontalGroupBoxBodyGrid.class;
            }

            @Order(-1000)
            public class ToolbarSequenceBox extends AbstractSequenceBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
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
                    protected void execClickAction() {
                        ArticleForm form = new ArticleForm();
                        form.setProjectId(getProjectId());
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
                        form.setProjectId(getProjectId());
                        form.startNew();
                        form.waitFor();
                    }
                }
            }

            @Order(1)
            public class FilterModeSelectorField extends AbstractModeSelectorField<Long> {
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
                    return 1;
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
            public class SearchField extends AbstractPlaceholderField {

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

            }

            @Override
            protected int getConfiguredGridColumnCount() {
                return 4;
            }


            @Order(750)
            public class ContainerBox extends AbstractSplitBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 4;
                }

                @Override
                protected double getConfiguredSplitterPosition() {
                    return 0.6;
                }

                @Override
                public boolean isStatusVisible() {
                    return false;
                }

                @Order(1000)
                @ClassId("37999d63-7407-44f5-b776-bb1f97344bcd")
                public class AccordionField extends AbstractAccordionField<ArticlesAccordion> {

                    @Override
                    protected boolean getConfiguredLabelVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 3;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        getAccordion().addGroupManager(new ArticleTileGroupManager());
                        getAccordion().activateGroupManager(ArticleTileGroupManager.ID);
                    }


                    @ClassId("f59eaed0-afeb-48f8-a99d-cc4a15aa4253")
                    public class ArticlesAccordion extends AbstractTileAccordion<ArticleTile> {
                        @Override
                        protected boolean getConfiguredExclusiveExpand() {
                            return true;
                        }

                        @Override
                        protected String getConfiguredCssClass() {
                            return "has-custom-tiles";
                        }

                        @Override
                        protected boolean getConfiguredTextFilterEnabled() {
                            return true;
                        }

                        @Override
                        public boolean isSelectable() {
                            return true;
                        }

                        @ClassId("0663a479-ff8f-4a72-b337-ff9759643955")
                        public class TileGroup extends AbstractGroup {

                            @Override
                            public TileGrid getBody() {
                                return (TileGrid) super.getBody();
                            }

                            @ClassId("3d7e78f9-75b1-48f5-aefe-2fb2118b7577")
                            public class TileGrid extends AbstractTileGrid<IHtmlTile> {
                                @Override
                                protected boolean getConfiguredWithPlaceholders() {
                                    return true;
                                }

                                @Order(1000)
                                public class EditMenu extends AbstractEditMenu {
                                    @Override
                                    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                        return CollectionUtility.hashSet(org.eclipse.scout.rt.client.ui.action.menu.TileGridMenuType.SingleSelection);
                                    }

                                    @Override
                                    protected void execAction() {
                                        ArticleTile articleTile = (ArticleTile) getSelectedTile();

                                        ArticleForm form = new ArticleForm();
                                        form.setArticleId(articleTile.getArticle().getId());
                                        form.startModify();
                                        form.waitFor();
                                        if (form.isFormStored()) {
                                            NotificationHelper.showSaveSuccessNotification();

                                            fetchArticles();
                                        }
                                    }
                                }

                                @Order(1500)
                                public class SendEmailMenu extends AbstractSendEmailMenu {

                                    @Override
                                    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                        return org.eclipse.scout.rt.platform.util.CollectionUtility.hashSet(org.eclipse.scout.rt.client.ui.action.menu.TileGridMenuType.SingleSelection, org.eclipse.scout.rt.client.ui.action.menu.TileGridMenuType.MultiSelection);
                                    }

                                    @Override
                                    protected void execAction() {
                                        ArticleTile articleTile = (ArticleTile) getSelectedTile();
                                        Article article = articleTile.getArticle();

                                        EmailForm form = new EmailForm();
                                        form.getMessageField().setValue(article.getContent());
                                        form.startNew();
                                    }
                                }

                                @Order(2000)
                                public class DeleteMenu extends AbstractDeleteMenu {

                                    @Override
                                    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                        return CollectionUtility.hashSet(org.eclipse.scout.rt.client.ui.action.menu.TileGridMenuType.SingleSelection);
                                    }

                                    @Override
                                    protected void execAction() {
                                        if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {

                                            ArticleTile articleTile = (ArticleTile) getSelectedTile();

                                            BEANS.get(IArticleService.class).delete(articleTile.getArticle().getId());

                                            NotificationHelper.showDeleteSuccessNotification();

                                            fetchArticles();
                                        }
                                    }
                                }

                                @Override
                                protected int getConfiguredGridColumnCount() {
                                    return 4;
                                }

                                @Override
                                protected void execTileAction(IHtmlTile tile) {
                                    super.execTileAction(tile);

                                    ArticleTile articleTile = (ArticleTile) getSelectedTile();

                                    String content = BEANS.get(IKnowledgeBaseService.class).fetchArticleContent(articleTile.getArticle().getId());

                                    getPreviewField().setValue(content);
                                }

                                @Override
                                protected boolean getConfiguredScrollable() {
                                    return false;
                                }
                            }
                        }
                    }
                }

                @Order(2000)
                public class PreviewField extends AbstractHtmlField {
                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        IHtmlContent content = HTML.fragment(
                                HTML.p(TEXTS.get("ClickOnArticleToPreviewContent")).style("font-size:13px;color:#8c8c8c;margin-top:250px;text-align:center;")
                        );

                        setValue(content.toHtml());
                    }

                }
            }


        }
    }

}
