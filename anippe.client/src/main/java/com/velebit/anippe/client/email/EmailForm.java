package com.velebit.anippe.client.email;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.fields.texteditor.AbstractTextEditorField;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.email.EmailForm.MainBox.CancelButton;
import com.velebit.anippe.client.email.EmailForm.MainBox.OkButton;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.email.EmailFormData;
import com.velebit.anippe.shared.email.IEmailService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.apache.commons.io.FileUtils;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.form.fields.tagfield.AbstractTagField;
import org.eclipse.scout.rt.client.ui.notification.Notification;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.resource.MimeType;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;
import java.util.Set;

@FormData(value = EmailFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class EmailForm extends AbstractForm {

    private List<BinaryResource> attachments = CollectionUtility.emptyArrayList();
    private Integer clientId;
    private Integer emailId;

    @FormData
    public Integer getEmailId() {
        return emailId;
    }

    @FormData
    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    @FormData
    public List<BinaryResource> getAttachments() {
        return attachments;
    }

    @FormData
    public void setAttachments(List<BinaryResource> attachments) {
        this.attachments = attachments;
    }

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Email;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("EnterRecipientsToSendEmail");
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("NewEmail");
    }

    @Override
    protected void execStored() {
        NotificationHelper.showNotification(TEXTS.get("EmailAddedToSendQueue"));
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public void startPreview() {
        startInternalExclusive(new PreviewHandler());
    }

    public MainBox.MainTabBox.AttachmentsBox getAttachmentsBox() {
        return getFieldByClass(MainBox.MainTabBox.AttachmentsBox.class);
    }

    public MainBox.MainTabBox.AttachmentsBox.AttachmentsTableField getAttachmentsTableField() {
        return getFieldByClass(MainBox.MainTabBox.AttachmentsBox.AttachmentsTableField.class);
    }

    public MainBox.MainTabBox.MainGroupBox.BCCField getBCCField() {
        return getFieldByClass(MainBox.MainTabBox.MainGroupBox.BCCField.class);
    }

    public MainBox.MainTabBox.MainGroupBox.CCField getCCField() {
        return getFieldByClass(MainBox.MainTabBox.MainGroupBox.CCField.class);
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public MainBox.MainTabBox.MainGroupBox getMainGroupBox() {
        return getFieldByClass(MainBox.MainTabBox.MainGroupBox.class);
    }

    public MainBox.MainTabBox getMainTabBox() {
        return getFieldByClass(MainBox.MainTabBox.class);
    }

    public MainBox.MainTabBox.MainGroupBox.MessageBox.MessageField getMessageField() {
        return getFieldByClass(MainBox.MainTabBox.MainGroupBox.MessageBox.MessageField.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    public MainBox.MainTabBox.MainGroupBox.ReceiverField getReceiverField() {
        return getFieldByClass(MainBox.MainTabBox.MainGroupBox.ReceiverField.class);
    }

    public MainBox.MainTabBox.MainGroupBox.SenderField getSenderField() {
        return getFieldByClass(MainBox.MainTabBox.MainGroupBox.SenderField.class);
    }

    public MainBox.MainTabBox.MainGroupBox.SubjectField getSubjectField() {
        return getFieldByClass(MainBox.MainTabBox.MainGroupBox.SubjectField.class);
    }

    public void setLabels() {
        getAttachmentsBox().setLabel(getAttachmentsBox().getConfiguredLabel() + " (" + getAttachmentsTableField().getTable().getRowCount() + ")");
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(-2000)
        public class MainTabBox extends AbstractTabBox {
            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Order(-1000)
            public class MainGroupBox extends AbstractGroupBox {

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 1;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("EmailInformations");
                }

                private Set<String> validateEmails(Set<String> rawValue) {
                    if (CollectionUtility.isEmpty(rawValue))
                        return rawValue;

                    boolean isValid = BEANS.get(IEmailService.class).isEmailValid(rawValue);

                    if (!isValid) {
                        throw new VetoException(TEXTS.get("EmailIsInvalid"));
                    }

                    return rawValue;
                }

                @Order(0)
                public class SenderField extends AbstractStringField {

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Sender");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 70;
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }

                    @Override
                    protected void execInitField() {
                        String businessEmail = ClientSession.get().getCurrentUser().getEmail();
                        String organisationEmail = ClientSession.get().getCurrentOrganisation().getOrganisationSettings().getEmailSettings().getSmtpEmail();

                        setValue(businessEmail != null ? businessEmail : organisationEmail);
                    }

                    @Override
                    public boolean isEnabled() {
                        return false;
                    }

                    @Override
                    public boolean isMandatory() {
                        return true;
                    }
                }

                @Order(500)
                public class ReceiverField extends AbstractTagField {

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Receiver");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 70;
                    }

                    @Override
                    protected Set<String> execValidateValue(Set<String> rawValue) {
                        return validateEmails(rawValue);
                    }

                    @Override
                    public boolean isMandatory() {
                        return true;
                    }

                    @Order(2000)
                    public class RemoveAllMenu extends AbstractDeleteMenu {

                        @Override
                        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                            return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
                        }

                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("RemoveAll");
                        }

                        @Override
                        protected void execAction() {
                            getReceiverField().setValue(null);
                        }
                    }
                }

                @Order(750)
                public class CCField extends AbstractTagField {

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("CC");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 70;
                    }

                    @Override
                    protected Set<String> execValidateValue(Set<String> rawValue) {
                        return validateEmails(rawValue);
                    }

                    @Order(2000)
                    public class RemoveAllMenu extends AbstractDeleteMenu {

                        @Override
                        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                            return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
                        }

                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("RemoveAll");
                        }

                        @Override
                        protected void execAction() {
                            getCCField().setValue(null);
                        }
                    }
                }

                @Order(875)
                public class BCCField extends AbstractTagField {

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("BCC");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 70;
                    }

                    @Override
                    protected Set<String> execValidateValue(Set<String> rawValue) {
                        return validateEmails(rawValue);
                    }

                    @Order(2000)
                    public class RemoveAllMenu extends AbstractDeleteMenu {

                        @Override
                        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                            return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
                        }

                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("RemoveAll");
                        }

                        @Override
                        protected void execAction() {
                            getBCCField().setValue(null);
                        }
                    }
                }

                @Order(1000)
                public class SubjectField extends AbstractStringField {

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("EmailSubject");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 70;
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }

                    @Override
                    public boolean isMandatory() {
                        return true;
                    }
                }

                @Order(1500)
                public class MessageBox extends AbstractSequenceBox {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Message");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 70;
                    }

                    @Override
                    protected boolean getConfiguredAutoCheckFromTo() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredHeightInPixel() {
                        return 300;
                    }

                    @Override
                    protected double getConfiguredGridWeightY() {
                        return 0;
                    }

                    @Order(2000)
                    public class MessageField extends AbstractTextEditorField {
                        @Override
                        protected int getConfiguredLabelWidthInPixel() {
                            return 80;
                        }

                        @Override
                        protected double getConfiguredGridWeightY() {
                            return -1;
                        }

                        @Override
                        protected int getConfiguredHeightInPixel() {
                            return 300;
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 4;
                        }

                        @Override
                        protected boolean getConfiguredLabelVisible() {
                            return false;
                        }

                        @Override
                        public boolean isMandatory() {
                            return true;
                        }

                    }
                }

            }

            @Order(0)
            public class AttachmentsBox extends AbstractGroupBox {

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Attachments");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(1000)
                public class AddMenu extends AbstractAddMenu {

                    @Override
                    protected void execAction() {
                        FileChooser fc = new FileChooser(true);
                        List<BinaryResource> files = fc.startChooser();
                        if (files != null && files.size() > 0) {
                            for (BinaryResource file : files) {
                                ITableRow row = getAttachmentsTableField().getTable().createRow();
                                getAttachmentsTableField().getTable().getNameColumn().setValue(row, file.getFilename());
                                getAttachmentsTableField().getTable().getExtensionColumn().setValue(row, file.getContentType());
                                getAttachmentsTableField().getTable().getAttachmentColumn().setValue(row, file);
                                getAttachmentsTableField().getTable().getSizeColumn().setValue(row, file.getContentLength());
                                getAttachmentsTableField().getTable().addRow(row);
                            }
                        }

                        setLabels();
                    }
                }

                @Order(3000)
                public class AttachmentsTableField extends AbstractTableField<AttachmentsTableField.Table> {

                    @Override
                    protected int getConfiguredGridH() {
                        return 6;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    public class Table extends AbstractTable {
                        @Override
                        public boolean isAutoResizeColumns() {
                            return true;
                        }

                        @Override
                        protected boolean getConfiguredHeaderEnabled() {
                            return false;
                        }

                        public AttachmentColumn getAttachmentColumn() {
                            return getColumnSet().getColumnByClass(AttachmentColumn.class);
                        }

                        public ExtensionColumn getExtensionColumn() {
                            return getColumnSet().getColumnByClass(ExtensionColumn.class);
                        }

                        public SizeColumn getSizeColumn() {
                            return getColumnSet().getColumnByClass(SizeColumn.class);
                        }

                        public NameColumn getNameColumn() {
                            return getColumnSet().getColumnByClass(NameColumn.class);
                        }

                        @Order(2000)
                        public class AttachmentColumn extends AbstractColumn<BinaryResource> {

                            @Override
                            public boolean isDisplayable() {
                                return false;
                            }
                        }

                        @Order(3000)
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

                        @Order(4000)
                        public class ExtensionColumn extends AbstractStringColumn {

                            @Override
                            protected String getConfiguredHeaderText() {
                                return TEXTS.get("Format");
                            }

                            @Override
                            protected int getConfiguredWidth() {
                                return 100;
                            }

                            @Override
                            protected void execDecorateCell(Cell cell, ITableRow row) {
                                BinaryResource br = (BinaryResource) getAttachmentColumn().getValue(row);
                                String ext = MimeType.convertToMimeType(br.getContentType()).getFileExtension();

                                cell.setText(ext);
                            }
                        }

                        @Order(5000)
                        public class SizeColumn extends AbstractIntegerColumn {
                            @Override
                            protected String getConfiguredHeaderText() {
                                return TEXTS.get("Size");
                            }

                            @Override
                            protected void execDecorateCell(Cell cell, ITableRow row) {
                                cell.setText(FileUtils.byteCountToDisplaySize(getValue(row)));
                            }

                            @Override
                            protected int getConfiguredWidth() {
                                return 100;
                            }
                        }

                        @Order(0)
                        public class ViewMenu extends AbstractMenu {
                            @Override
                            protected String getConfiguredText() {
                                return TEXTS.get("Open");
                            }

                            @Override
                            protected String getConfiguredIconId() {
                                return FontIcons.FileO;
                            }

                            @Override
                            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                return CollectionUtility.hashSet(TableMenuType.SingleSelection);
                            }

                            @Override
                            protected void execAction() {
                                IDesktop desktop = IDesktop.CURRENT.get();
                                if (desktop != null) {
                                    BinaryResource br = (BinaryResource) getAttachmentColumn().getSelectedValue();
                                    desktop.openUri(br);
                                }
                            }

                        }

                        @Order(2000)
                        public class DeleteMenu extends AbstractDeleteMenu {

                            @Override
                            protected void execAction() {
                                getAttachmentsTableField().getTable().getSelectedRows().forEach(r -> r.delete());
                                setLabels();
                            }
                        }
                    }
                }
            }
        }

        @Order(100000)
        public class OkButton extends AbstractOkButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("SendEmail");
            }
        }

        @Order(101000)
        public class CancelButton extends AbstractCancelButton {
        }
    }

    public class NewHandler extends AbstractFormHandler {

        @Override
        protected void execLoad() {
            IEmailService service = BEANS.get(IEmailService.class);
            EmailFormData formData = new EmailFormData();
            exportFormData(formData);
            formData = service.prepareCreate(formData);
            importFormData(formData);

            getSubjectField().touch();
            setLabels();
        }

        @Override
        protected void execPostLoad() {
            touch();

            // Do not check SMTP valid if dev mode.
            if (CONFIG.getPropertyValue(PlatformConfigProperties.PlatformDevModeProperty.class))
                return;

            boolean smtpSettingsValid = ClientSession.get().getCurrentOrganisation().getOrganisationSettings().getEmailSettings().isEmailSettingsValid();
            boolean hasSender = getSenderField().getValue() != null;

            if (!smtpSettingsValid || !hasSender) {
                getMainGroupBox().setNotification(new Notification(new Status(TEXTS.get("EmailSettingsAreInvalid"), IStatus.WARNING)));
                getOkButton().setEnabled(false);
            }
        }

        @Override
        protected void execStore() {
            IEmailService service = BEANS.get(IEmailService.class);
            EmailFormData formData = new EmailFormData();
            exportFormData(formData);
            service.create(formData);
        }
    }

    public class PreviewHandler extends AbstractFormHandler {

        @Override
        protected void execLoad() {
            IEmailService service = BEANS.get(IEmailService.class);
            EmailFormData formData = new EmailFormData();
            exportFormData(formData);
            //formData = service.load(formData);
            importFormData(formData);

            getMainGroupBox().setEnabled(false);
            getCancelButton().setEnabled(true);
            setLabels();
            setTitle(getSubjectField().getValue());
            setSubTitle(TEXTS.get("ViewEmail"));

            getOkButton().setVisible(false);
            getAttachmentsBox().getMenuByClass(MainBox.MainTabBox.AttachmentsBox.AddMenu.class).setVisible(false);
            getAttachmentsTableField().getTable().getMenuByClass(MainBox.MainTabBox.AttachmentsBox.AttachmentsTableField.Table.DeleteMenu.class).setVisible(false);
            getMainGroupBox().getFields().forEach(f -> f.setDisabledStyle(IFormField.DISABLED_STYLE_READ_ONLY));
        }
    }
}
