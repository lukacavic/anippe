package com.velebit.anippe.client.attachments;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractDownloadMenu;
import com.velebit.anippe.client.common.menus.AbstractOpenMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.attachments.AbstractAttachmentsBoxData;
import com.velebit.anippe.shared.attachments.IAttachmentService;
import org.apache.commons.io.FileUtils;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.DefaultSubtypeSdkCommand;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractObjectColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

@FormData(value = AbstractAttachmentsBoxData.class, sdkCommand = SdkCommand.CREATE, defaultSubtypeSdkCommand = DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractAttachmentsBox extends AbstractGroupBox {
    @Override
    protected int getConfiguredGridColumnCount() {
        return 1;
    }

    @Override
    protected String getConfiguredLabel() {
        return TEXTS.get("Attachments");
    }

    @Override
    protected boolean getConfiguredStatusVisible() {
        return false;
    }

    public AttachmentsTableField getAttachmentsTableField() {
        return getFieldByClass(AttachmentsTableField.class);
    }

    @Order(1000)
    public class AttachmentsTableField extends AbstractTableField<AttachmentsTableField.Table> {
        @Override
        protected boolean getConfiguredLabelVisible() {
            return false;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
            return false;
        }

        @Override
        protected void execInitField() {
            //AbstractAttachmentsBox.this.setLabel(AbstractAttachmentsBox.this.getConfiguredLabel() + " (" + getAttachmentsTableField().getTable().getRowCount() + ")");
        }

        @Override
        protected int getConfiguredGridH() {
            return 6;
        }

        public class Table extends AbstractTable {

            @Override
            protected void execContentChanged() {
                AbstractAttachmentsBox.this.setLabel(AbstractAttachmentsBox.this.getConfiguredLabel() + " (" + getRowCount() + ")");
            }

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
                return true;
            }

            public BinaryResource findBinaryResourceToManage() {
                BinaryResource binaryResource = null;

                if (getBinaryResourceColumn().getSelectedValue() != null) {
                    binaryResource = new BinaryResource(getNameColumn().getSelectedValue(), (byte[]) getBinaryResourceColumn().getSelectedValue());
                } else {
                    binaryResource = BEANS.get(IAttachmentService.class).download(getAttachmentIdColumn().getSelectedValue());
                }

                if (binaryResource == null) {
                    NotificationHelper.showErrorNotification(TEXTS.get("ErrorReadingFile"));
                    return null;
                }
                return binaryResource;
            }

            @Override
            protected boolean getConfiguredHeaderEnabled() {
                return false;
            }

            @Override
            protected boolean getConfiguredHeaderMenusEnabled() {
                return false;
            }

            public FormatColumn getFormatColumn() {
                return getColumnSet().getColumnByClass(FormatColumn.class);
            }

            public SizeColumn getSizeColumn() {
                return getColumnSet().getColumnByClass(SizeColumn.class);
            }

            public AttachmentColumn getAttachmentColumn() {
                return getColumnSet().getColumnByClass(AttachmentColumn.class);
            }

            public BinaryResourceColumn getBinaryResourceColumn() {
                return getColumnSet().getColumnByClass(BinaryResourceColumn.class);
            }

            public NameColumn getNameColumn() {
                return getColumnSet().getColumnByClass(NameColumn.class);
            }

            public AttachmentIdColumn getAttachmentIdColumn() {
                return getColumnSet().getColumnByClass(AttachmentIdColumn.class);
            }

            @Order(1000)
            public class AddMenu extends AbstractAddMenu {

                @Override
                protected void execAction() {
                    FileChooser chooser = new FileChooser(true);
                    List<BinaryResource> items = chooser.startChooser();
                    if (!CollectionUtility.isEmpty(items)) {
                        for (BinaryResource attachment : items) {
                            ITableRow row = createRow();
                            getAttachmentColumn().setValue(row, attachment.getContent());
                            getBinaryResourceColumn().setValue(row, attachment);
                            getNameColumn().setValue(row, attachment.getFilename());
                            getFormatColumn().setValue(row, attachment.getContentType());
                            getSizeColumn().setValue(row, attachment.getContentLength());
                            addRow(row, true);
                        }
                    }
                }
            }

            @Order(2000)
            public class DeleteMenu extends AbstractDeleteMenu {

                @Override
                protected void execAction() {
                    for (ITableRow row : getSelectedRows()) {
                        row.setStatusDeleted();
                        row.delete();
                    }
                }
            }

            @Order(3000)
            public class ViewMenu extends AbstractOpenMenu {

                @Override
                protected void execAction() {
                    IDesktop desktop = IDesktop.CURRENT.get();
                    if (desktop != null) {
                        BinaryResource binaryResource = findBinaryResourceToManage();

                        if(binaryResource != null) {
                            desktop.openUri(binaryResource, OpenUriAction.OPEN);
                        }

                    }
                }

            }

            @Order(3100)
            public class DownloadMenu extends AbstractDownloadMenu {

                @Override
                protected void execAction() {
                    IDesktop desktop = IDesktop.CURRENT.get();
                    if (desktop != null) {
                        BinaryResource binaryResource = findBinaryResourceToManage();

                        if(binaryResource != null) {
                            desktop.openUri(binaryResource, OpenUriAction.DOWNLOAD);
                        }

                    }
                }
            }

            @Order(1000)
            public class AttachmentIdColumn extends AbstractIDColumn {

            }

            @Order(1250)
            public class BinaryResourceColumn extends AbstractObjectColumn {
                @Override
                protected boolean getConfiguredDisplayable() {
                    return false;
                }
            }

            @Order(1500)
            public class AttachmentColumn extends AbstractObjectColumn {
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
                    return 150;
                }
            }

            @Order(3000)
            public class FormatColumn extends AbstractStringColumn {
                @Override
                protected String getConfiguredHeaderText() {
                    return TEXTS.get("Format");
                }

                @Override
                protected int getConfiguredWidth() {
                    return 70;
                }

            }

            @Order(4000)
            public class SizeColumn extends AbstractIntegerColumn {
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
        }
    }

}
