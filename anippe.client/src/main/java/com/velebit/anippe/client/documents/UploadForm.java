package com.velebit.anippe.client.documents;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.documents.UploadForm.MainBox.CancelButton;
import com.velebit.anippe.client.documents.UploadForm.MainBox.GroupBox;
import com.velebit.anippe.client.documents.UploadForm.MainBox.GroupBox.*;
import com.velebit.anippe.client.documents.UploadForm.MainBox.GroupBox.UploadedFilesTableField.Table;
import com.velebit.anippe.client.documents.UploadForm.MainBox.OkButton;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.documents.IUploadService;
import com.velebit.anippe.shared.documents.UploadFormData;
import com.velebit.anippe.shared.icons.FontIcons;
import org.apache.commons.io.FileUtils;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.notification.Notification;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;

import java.util.Date;
import java.util.List;
import java.util.Set;

@FormData(value = UploadFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class UploadForm extends AbstractForm {

    protected Integer relatedId;
    protected Integer relatedType;
    private List<Integer> documentIds;

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

    @FormData
    public List<Integer> getDocumentIds() {
        return documentIds;
    }

    @FormData
    public void setDocumentIds(List<Integer> documentIds) {
        this.documentIds = documentIds;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Upload");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("SelectDocumentsToUpload");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Paperclip;
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public DescriptionField getDescriptionField() {
        return getFieldByClass(DescriptionField.class);
    }

    public DocumentField getDocumentField() {
        return getFieldByClass(DocumentField.class);
    }

    public CreatedAtField getCreatedAtField() {
        return getFieldByClass(CreatedAtField.class);
    }

    public UserField getUserField() {
        return getFieldByClass(UserField.class);
    }

    @Override
    protected void execInitForm() {
        int maxUploadSize = NumberUtility.longToInt(Constants.MAX_UPLOAD_SIZE.longValue() / (1024 * 1024));
        String message = TEXTS.get("MaxUploadSizeIs0MB", Integer.toString(maxUploadSize));
        getGroupBox().setNotification(new Notification(new Status(message, IStatus.WARNING, FontIcons.ExclamationMarkBold)));
    }

    public NameField getNameField() {
        return getFieldByClass(NameField.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public GroupBox.UploadedFilesTableField getUploadedFilesTableField() {
        return getFieldByClass(GroupBox.UploadedFilesTableField.class);
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 700;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected int getConfiguredGridColumnCount() {
                return 2;
            }

            @Order(0)
            public class CreatedAtField extends AbstractDateTimeField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("CreatedAt");
                }

                @Override
                protected void execInitField() {
                    setValue(new Date());
                }

                @Override
                public boolean isEnabled() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 130;
                }
            }

            @Order(500)
            public class UserField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("User");
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                public boolean isEnabled() {
                    return false;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 70;
                }

                @Override
                protected void execInitField() {
                    setValue(ClientSession.get().getCurrentUser().getFullName());
                }
            }

            @Order(1000)
            public class NameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Name");
                }

                @Override
                public boolean isVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 130;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(2000)
            public class DescriptionField extends AbstractTextAreaField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Description");
                }

                @Override
                public boolean isVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 130;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(3000)
            public class DocumentField extends AbstractFileChooserField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Document");
                }

                @Override
                public boolean isVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 130;
                }

                @Override
                protected long getConfiguredMaximumUploadSize() {
                    return Constants.MAX_UPLOAD_SIZE;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

            }

            @Order(4000)
            public class UploadedFilesTableField extends AbstractTableField<Table> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("UploadedFiles");
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @ClassId("0be42c2f-c7d4-4497-b675-9649ba9ffb99")
                public class Table extends AbstractTable {

                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
                    }

                    public DescriptionColumn getDescriptionColumn() {
                        return getColumnSet().getColumnByClass(DescriptionColumn.class);
                    }

                    public DocumentIdColumn getDocumentIdColumn() {
                        return getColumnSet().getColumnByClass(DocumentIdColumn.class);
                    }

                    public FileSizeColumn getFileSizeColumn() {
                        return getColumnSet().getColumnByClass(FileSizeColumn.class);
                    }

                    public FileTypeColumn getFileTypeColumn() {
                        return getColumnSet().getColumnByClass(FileTypeColumn.class);
                    }

                    public NameColumn getNameColumn() {
                        return getColumnSet().getColumnByClass(NameColumn.class);
                    }

                    public FileColumn getFileColumn() {
                        return getColumnSet().getColumnByClass(FileColumn.class);
                    }

                    @Order(1000)
                    public class UploadMenu extends AbstractMenu {
                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("Upload");
                        }

                        @Override
                        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                            return CollectionUtility.hashSet(TableMenuType.EmptySpace);
                        }

                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Paperclip;
                        }

                        @Override
                        protected void execAction() {
                            List<BinaryResource> files = new FileChooser(true).startChooser();

                            if (CollectionUtility.isEmpty(files)) return;

                            for (BinaryResource file : files) {
                                ITableRow row = addRow();
                                getFileColumn().setValue(row, file);
                            }
                        }
                    }

                    @Order(2000)
                    public class DeleteMenu extends AbstractDeleteMenu {


                        @Override
                        protected void execAction() {
                            getSelectedRows().forEach(ITableRow::delete);
                        }
                    }

                    @Order(1000)
                    public class DocumentIdColumn extends AbstractIDColumn {

                    }

                    @Order(1500)
                    public class FileColumn extends AbstractColumn<BinaryResource> {
                        @Override
                        public boolean isDisplayable() {
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
                        protected boolean getConfiguredEditable() {
                            return true;
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(3000)
                    public class DescriptionColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Description");
                        }

                        @Override
                        protected boolean getConfiguredEditable() {
                            return true;
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(4000)
                    public class FileTypeColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("FilesOfType");
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            if (getFileColumn().getValue(row) != null) {
                                String extension = IOUtility.getFileExtension(getFileColumn().getValue(row).getFilename());

                                cell.setText(extension);
                            }
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(5000)
                    public class FileSizeColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Size");
                        }

                        @Override
                        protected void execDecorateCell(Cell cell, ITableRow row) {
                            if (getFileColumn().getValue(row) != null) {
                                String size = FileUtils.byteCountToDisplaySize(getFileColumn().getValue(row).getContentLength());

                                cell.setText(size);
                            }
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

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            IUploadService service = BEANS.get(IUploadService.class);
            UploadFormData formData = new UploadFormData();
            exportFormData(formData);
            formData = service.prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            IUploadService service = BEANS.get(IUploadService.class);
            UploadFormData formData = new UploadFormData();
            exportFormData(formData);
            importFormData(service.create(formData));
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            IUploadService service = BEANS.get(IUploadService.class);
            UploadFormData formData = new UploadFormData();
            exportFormData(formData);
            formData = service.load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            IUploadService service = BEANS.get(IUploadService.class);
            UploadFormData formData = new UploadFormData();
            exportFormData(formData);
            service.store(formData);
        }
    }
}

