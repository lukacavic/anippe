package com.velebit.anippe.client.documents;

import com.velebit.anippe.client.ICustomCssClasses;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.*;
import com.velebit.anippe.client.documents.DocumentsForm.MainBox.GroupBox;
import com.velebit.anippe.client.documents.DocumentsForm.MainBox.GroupBox.DocumentsTableField.Table;
import com.velebit.anippe.client.email.EmailForm;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.documents.DocumentsFormData;
import com.velebit.anippe.shared.documents.DocumentsFormData.DocumentsTable.DocumentsTableRowData;
import com.velebit.anippe.shared.documents.IDocumentsService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.apache.commons.io.FileUtils;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.*;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Arrays;
import java.util.List;

@FormData(value = DocumentsFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class DocumentsForm extends AbstractForm {

    private Integer relatedId;
    private Integer relatedType;

    private List<Integer> temporaryDocumentIds = CollectionUtility.emptyArrayList();

    @FormData
    public List<Integer> getTemporaryDocumentIds() {
        return temporaryDocumentIds;
    }

    @FormData
    public void setTemporaryDocumentIds(List<Integer> temporaryDocumentIds) {
        this.temporaryDocumentIds = temporaryDocumentIds;
    }

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

    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
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

    @Override
    protected void execInitForm() {
        super.execInitForm();

        fetchDocuments();
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(-1000)
        public class ToggleViewMenu extends AbstractMenu {
            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Clone;
            }

            @Override
            protected boolean getConfiguredToggleAction() {
                return true;
            }

            @Override
            protected int getConfiguredActionStyle() {
                return ACTION_STYLE_BUTTON;
            }

            @Override
            protected void execSelectionChanged(boolean selection) {
                super.execSelectionChanged(selection);

                getDocumentsTableField().getTable().setTileMode(selection);
            }
        }

        @Order(997)
        public class UploadMenu extends AbstractAddMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("Upload");
            }

            @Override
            protected int getConfiguredActionStyle() {
                return ACTION_STYLE_BUTTON;
            }

            @Override
            protected String getConfiguredCssClass() {
                return "green-button";
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Paperclip;
            }

            @Override
            protected void execAction() {
                UploadForm form = new UploadForm();
                form.setRelatedId(getRelatedId());
                form.setRelatedType(getRelatedType());
                form.startNew();
                form.waitFor();
                if (form.isFormStored()) {

                    if (getRelatedId() == null) {
                        setTemporaryDocumentIds(form.getDocumentIds());
                    }

                    NotificationHelper.showSaveSuccessNotification();

                    fetchDocuments();
                }
            }
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {


            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            public DocumentsTableField getDocumentsTableField() {
                return getFieldByClass(DocumentsTableField.class);
            }

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Order(1000)
            public class DocumentsTableField extends AbstractTableField<Table> {

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 6;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                public class Table extends AbstractTable {

                    @Order(2100)
                    public class DownloadMenu extends AbstractDownloadMenu {

                        @Override
                        protected void execAction() {
                            IDesktop desktop = IDesktop.CURRENT.get();
                            BinaryResource binaryResource = BEANS.get(IDocumentsService.class).download(getDocumentIdColumn().getSelectedValue());

                            if (desktop != null && binaryResource != null) {
                                desktop.openUri(binaryResource, OpenUriAction.DOWNLOAD);
                            }
                        }
                    }

                    @Order(2150)
                    public class SendEmailMenu extends AbstractMenu {

                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Email;
                        }

                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("SendEmail");
                        }

                        @Override
                        protected void execAction() {
                            BinaryResource binaryResource = BEANS.get(IDocumentsService.class).download(getDocumentIdColumn().getSelectedValue());

                            if (binaryResource == null)
                                return;

                            EmailForm form = new EmailForm();
                            form.setAttachments(Arrays.asList(binaryResource));
                            form.startNew();
                            form.waitFor();
                        }
                    }

                    @Order(5000)
                    public class DeleteMenu extends AbstractDeleteMenu {

                        @Override
                        protected void execAction() {
                            if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                                BEANS.get(IDocumentsService.class).delete(getDocumentIdColumn().getSelectedValues());

                                NotificationHelper.showDeleteSuccessNotification();

                                fetchDocuments();
                            }
                        }
                    }
                    @Override
                    protected ITile execCreateTile(ITableRow row) {
                        return new DocumentTile();
                    }

                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
                    }

                    public CreatedAtColumn getCreatedAtColumn() {
                        return getColumnSet().getColumnByClass(CreatedAtColumn.class);
                    }

                    public DocumentIdColumn getDocumentIdColumn() {
                        return getColumnSet().getColumnByClass(DocumentIdColumn.class);
                    }

                    @Override
                    protected void execDecorateRow(ITableRow row) {
                        super.execDecorateRow(row);

                        row.setCssClass("vertical-align-middle");
                    }

                    public DocumentColumn getDocumentColumn() {
                        return getColumnSet().getColumnByClass(DocumentColumn.class);
                    }

                    public FileIconColumn getFileIconColumn() {
                        return getColumnSet().getColumnByClass(FileIconColumn.class);
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

                    public UpdatedAtColumn getUpdatedAtColumn() {
                        return getColumnSet().getColumnByClass(UpdatedAtColumn.class);
                    }

                    public UserColumn getUserColumn() {
                        return getColumnSet().getColumnByClass(UserColumn.class);
                    }

                    @Order(1000)
                    public class DocumentIdColumn extends AbstractIDColumn {

                    }

                    @Order(1500)
                    public class DocumentColumn extends AbstractObjectColumn {
                        @Override
                        protected boolean getConfiguredDisplayable() {
                            return false;
                        }
                    }

                    @Order(1750)
                    public class FileIconColumn extends AbstractIconColumn {
                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            super.execDecorateCell(cell, row);

                            cell.setIconId(FontIcons.Paperclip);
                        }

                        @Override
                        public boolean isFixedWidth() {
                            return true;
                        }

                        @Override
                        public boolean isFixedPosition() {
                            return true;
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

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            super.execDecorateCell(cell, row);

                            String content = HTML.fragment(
                                    HTML.span(getValue(row)).cssClass(ICustomCssClasses.TABLE_HTML_CELL_HEADING),
                                    HTML.br(),
                                    HTML.span(ObjectUtility.nvl(getUserColumn().getValue(row), "-")).cssClass(ICustomCssClasses.TABLE_HTML_CELL_SUB_HEADING)
                            ).toHtml();

                            cell.setText(content);
                        }

                        @Override
                        protected boolean getConfiguredHtmlEnabled() {
                            return true;
                        }
                    }

                    @Order(3000)
                    public class UserColumn extends AbstractStringColumn {
                        @Override
                        protected boolean getConfiguredDisplayable() {
                            return false;
                        }

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
                            return 200;
                        }

                        @Override
                        public boolean isFixedWidth() {
                            return true;
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            super.execDecorateCell(cell, row);

                            if (getValue(row) != null) {
                                cell.setText(new PrettyTime().format(getValue(row)));
                            }
                        }
                    }

                    @Order(4500)
                    public class UpdatedAtColumn extends AbstractDateTimeColumn {
                        @Override
                        protected boolean getConfiguredDisplayable() {
                            return false;
                        }

                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("UpdatedAt");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(5000)
                    public class TypeColumn extends AbstractStringColumn {
                        @Override
                        protected boolean getConfiguredVisible() {
                            return false;
                        }

                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Type");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(6000)
                    public class SizeColumn extends AbstractIntegerColumn {
                        @Override
                        protected boolean getConfiguredVisible() {
                            return false;
                        }

                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Size");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            cell.setText(FileUtils.byteCountToDisplaySize(getValue(row)));
                        }
                    }

                    @Order(3000)
                    public class ViewMenu extends AbstractOpenMenu {

                        @Override
                        protected void execAction() {
                            IDesktop desktop = IDesktop.CURRENT.get();
                            BinaryResource binaryResource = BEANS.get(IDocumentsService.class).download(getDocumentIdColumn().getSelectedValue());

                            if (desktop != null && binaryResource != null) {
                                desktop.openUri(binaryResource, OpenUriAction.OPEN);
                            }
                        }
                    }

                    @Order(3050)
                    public class ActionsMenu extends AbstractActionsMenu {

                        @Order(2100)
                        public class DownloadMenu extends AbstractDownloadMenu {

                            @Override
                            protected void execAction() {
                                IDesktop desktop = IDesktop.CURRENT.get();
                                BinaryResource binaryResource = BEANS.get(IDocumentsService.class).download(getDocumentIdColumn().getSelectedValue());

                                if (desktop != null && binaryResource != null) {
                                    desktop.openUri(binaryResource, OpenUriAction.DOWNLOAD);
                                }
                            }
                        }

                        @Order(2150)
                        public class SendEmailMenu extends AbstractMenu {

                            @Override
                            protected String getConfiguredIconId() {
                                return FontIcons.Email;
                            }

                            @Override
                            protected String getConfiguredText() {
                                return TEXTS.get("SendEmail");
                            }

                            @Override
                            protected void execAction() {
                                BinaryResource binaryResource = BEANS.get(IDocumentsService.class).download(getDocumentIdColumn().getSelectedValue());

                                if (binaryResource == null)
                                    return;

                                EmailForm form = new EmailForm();
                                form.setAttachments(Arrays.asList(binaryResource));
                                form.startNew();
                                form.waitFor();
                            }
                        }

                        @Order(4000)
                        public class DeleteMenu extends AbstractDeleteMenu {

                            @Override
                            protected void execAction() {
                                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                                    BEANS.get(IDocumentsService.class).delete(getDocumentIdColumn().getSelectedValues());
                                    NotificationHelper.showSaveSuccessNotification();

                                    fetchDocuments();
                                }
                            }
                        }
                    }
                }

                @Override
                protected boolean getConfiguredLabelVisible() {
                    return false;
                }
            }
        }
    }

    public void fetchDocuments() {
        if ((getRelatedId() == null && getRelatedType() == null) && getTemporaryDocumentIds().isEmpty())
            return;

        List<DocumentsTableRowData> rowData = BEANS.get(IDocumentsService.class).fetchDocuments(relatedId, relatedType);
        getDocumentsTableField().getTable().importFromTableRowBeanData(rowData, DocumentsTableRowData.class);
    }

}
