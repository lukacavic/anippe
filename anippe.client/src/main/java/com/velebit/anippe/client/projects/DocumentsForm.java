package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.projects.DocumentsForm.MainBox.GroupBox;
import com.velebit.anippe.shared.beans.Document;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.DocumentsFormData;
import com.velebit.anippe.shared.projects.IDocumentsService;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.tile.AbstractHtmlTile;
import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Date;
import java.util.Set;

@FormData(value = DocumentsFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class DocumentsForm extends AbstractForm {

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Documents");
    }

    public GroupBox.DocumentsTableField getDocumentsTableField() {
        return getFieldByClass(GroupBox.DocumentsTableField.class);
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
            @Order(0)
            public class ToggleViewModeMenu extends AbstractMenu {
                @Override
                protected String getConfiguredIconId() {
                    return FontIcons.Clone;
                }

                @Override
                protected void execAction() {
                    getDocumentsTableField().getTable().setTileMode(!getDocumentsTableField().getTable().isTileMode());
                }
            }

            @Order(1000)
            public class DocumentsTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<DocumentsTableField.Table> {
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

                @ClassId("bc866a1b-97c3-494a-b04f-2e2e3534465a")
                public class Table extends AbstractTable {
                    @Override
                    protected void execInitTable() {
                        super.execInitTable();

                        ITableRow row = addRow();
                        getCreatedAtColumn().setValue(row, new Date());
                        getUploaderColumn().setValue(row, new User());
                        getNameColumn().setValue(row, "example.txt");
                    }

                    @Order(1000)
                    public class DownloadMenu extends AbstractMenu {
                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("Download");
                        }

                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Download1;
                        }

                        @Override
                        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                            return org.eclipse.scout.rt.platform.util.CollectionUtility.hashSet(org.eclipse.scout.rt.client.ui.action.menu.TableMenuType.SingleSelection, org.eclipse.scout.rt.client.ui.action.menu.TableMenuType.MultiSelection, TableMenuType.EmptySpace);
                        }

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
                                return super.getContent();
                            }

                            @Override
                            public boolean isHtmlEnabled() {
                                return true;
                            }
                        };
                    }

                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
                    }

                    public CreatedAtColumn getCreatedAtColumn() {
                        return getColumnSet().getColumnByClass(CreatedAtColumn.class);
                    }

                    public DocumentColumn getDocumentColumn() {
                        return getColumnSet().getColumnByClass(DocumentColumn.class);
                    }

                    public NameColumn getNameColumn() {
                        return getColumnSet().getColumnByClass(NameColumn.class);
                    }

                    public SizeColumn getSizeColumn() {
                        return getColumnSet().getColumnByClass(SizeColumn.class);
                    }

                    public TypeColumn getTypeColumn() {
                        return getColumnSet().getColumnByClass(TypeColumn.class);
                    }

                    public UploaderColumn getUploaderColumn() {
                        return getColumnSet().getColumnByClass(UploaderColumn.class);
                    }

                    @Order(1000)
                    public class DocumentColumn extends AbstractColumn<Document> {
                        @Override
                        protected boolean getConfiguredDisplayable() {
                            return false;
                        }
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

                    @Order(2500)
                    public class TypeColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Type");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(3000)
                    public class SizeColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Size");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(3500)
                    public class UploaderColumn extends AbstractColumn<User> {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("UploadedBy");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(4000)
                    public class CreatedAtColumn extends AbstractDateTimeColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("CreatedAt");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }
                }
            }
        }
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            DocumentsFormData formData = new DocumentsFormData();
            exportFormData(formData);
            formData = BEANS.get(IDocumentsService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            DocumentsFormData formData = new DocumentsFormData();
            exportFormData(formData);
            formData = BEANS.get(IDocumentsService.class).create(formData);
            importFormData(formData);
        }
    }

}
