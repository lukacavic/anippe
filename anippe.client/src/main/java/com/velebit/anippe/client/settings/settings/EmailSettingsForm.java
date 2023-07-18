package com.velebit.anippe.client.settings.settings;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.fields.AbstractEmailField;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.email.EmailForm;
import com.velebit.anippe.client.email.EmailPredefinedHeaderFooterDoEntity;
import com.velebit.anippe.client.email.EmailPredefinedHeaderFooterDoMapEntity;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.settings.settings.EmailSettingsForm.MainBox.OkButton;
import com.velebit.anippe.shared.Icons;
import com.velebit.anippe.shared.language.ILanguageService;
import com.velebit.anippe.shared.language.Language;
import com.velebit.anippe.shared.settings.settings.EmailSettingsFormData;
import com.velebit.anippe.shared.settings.settings.IEmailSettingsService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.IWidget;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSaveButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.dataobject.IDataObjectMapper;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.platform.util.TypeCastUtility;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;

import java.util.List;

@FormData(value = EmailSettingsFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class EmailSettingsForm extends AbstractForm {

    private String predefinedHeaderFooterJson;

    @FormData
    public String getPredefinedHeaderFooterJson() {
        return predefinedHeaderFooterJson;
    }

    @Override
    protected boolean getConfiguredAskIfNeedSave() {
        return false;
    }

    @Override
    protected boolean execValidate() {
        saveLanguageContent();

        return true;
    }

    @FormData
    public void setPredefinedHeaderFooterJson(String predefinedHeaderFooterJson) {
        this.predefinedHeaderFooterJson = predefinedHeaderFooterJson;
    }

    @Override
    protected String getConfiguredIconId() {
        return Icons.Gear;
    }

    @Override
    protected void execStored() {
        NotificationHelper.showSaveSuccessNotification();
    }

    private void loadLanguageContent() {

        EmailPredefinedHeaderFooterDoMapEntity entityDo = BEANS.get(IDataObjectMapper.class).readValue(getPredefinedHeaderFooterJson(), EmailPredefinedHeaderFooterDoMapEntity.class);

        if (entityDo == null)
            return;

        // Iterate all language groups
        for (IGroupBox ibox : getPredefinedHeaderFooterBox().getGroupBoxes()) {

            LanguageBox box = TypeCastUtility.castValue(ibox, LanguageBox.class);

            // Iterate all widgets in group
            for (IWidget widget : box.getChildren()) {

                IFormField field = (IFormField) widget;

                EmailPredefinedHeaderFooterDoEntity languageContent = entityDo.get(box.getLanguage(), EmailPredefinedHeaderFooterDoEntity.class);

                if (languageContent == null)
                    continue;

                if (field.getFieldId().equalsIgnoreCase("HeaderField")) {
                    String fieldContent = languageContent.header().get();

                    if (fieldContent != null) {

                        @SuppressWarnings("unchecked")
                        IValueField<String> valueField = (IValueField<String>) field;
                        valueField.setValue(fieldContent);
                    }
                }

                if (field.getFieldId().equalsIgnoreCase("FooterField")) {
                    String fieldContent = languageContent.footer().get();

                    if (fieldContent != null) {
                        @SuppressWarnings("unchecked")
                        IValueField<String> valueField = (IValueField<String>) field;
                        valueField.setValue(fieldContent);
                    }
                }

            }
        }

    }

    private void saveLanguageContent() {
        EmailPredefinedHeaderFooterDoMapEntity doEntity = BEANS.get(EmailPredefinedHeaderFooterDoMapEntity.class);

        for (IGroupBox ibox : getPredefinedHeaderFooterBox().getGroupBoxes()) {

            LanguageBox box = TypeCastUtility.castValue(ibox, LanguageBox.class);

            EmailPredefinedHeaderFooterDoEntity languageContent = BEANS.get(EmailPredefinedHeaderFooterDoEntity.class);

            for (IWidget field : box.getChildren()) {

                IFormField f = (IFormField) field;
                String fieldId = f.getFieldId();

                if ((f instanceof AbstractGroupBox))
                    continue;

                @SuppressWarnings("unchecked")
                String fieldContent = (String) ((IValueField<String>) f).getValue();

                if (!StringUtility.isNullOrEmpty(fieldContent)) {
                    if (fieldId.equals("HeaderField")) {
                        languageContent.header().set(fieldContent);
                    }

                    if (fieldId.equals("FooterField")) {
                        languageContent.footer().set(fieldContent);
                    }
                }
            }

            if (!languageContent.isEmpty()) {
                doEntity.put(box.getLanguage(), languageContent);
            }
        }

        setPredefinedHeaderFooterJson(BEANS.get(IDataObjectMapper.class).writeValue(doEntity));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Settings");
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public MainBox.MainGroupBox.BCCAllEmailsField getBCCAllEmailsField() {
        return getFieldByClass(MainBox.MainGroupBox.BCCAllEmailsField.class);
    }

    public MainBox.MainGroupBox.TestEmailBox.EmailSendSequenceBox getEmailSendSequenceBox() {
        return getFieldByClass(MainBox.MainGroupBox.TestEmailBox.EmailSendSequenceBox.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public MainBox.MainGroupBox.PredefinedBox getPredefinedBox() {
        return getFieldByClass(MainBox.MainGroupBox.PredefinedBox.class);
    }

    public MainBox.MainGroupBox.PredefinedBox.PredefinedHeaderFooterBox getPredefinedHeaderFooterBox() {
        return getFieldByClass(MainBox.MainGroupBox.PredefinedBox.PredefinedHeaderFooterBox.class);
    }

    public EmailForm.MainBox.MainTabBox.MainGroupBox getMainGroupBox() {
        return getFieldByClass(EmailForm.MainBox.MainTabBox.MainGroupBox.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    public MainBox.MainGroupBox.TestEmailBox.EmailSendSequenceBox.RecipientField getRecipientField() {
        return getFieldByClass(MainBox.MainGroupBox.TestEmailBox.EmailSendSequenceBox.RecipientField.class);
    }

    public MainBox.MainGroupBox.TestEmailBox.EmailSendSequenceBox.SendButton getSendButton() {
        return getFieldByClass(MainBox.MainGroupBox.TestEmailBox.EmailSendSequenceBox.SendButton.class);
    }

    public MainBox.MainGroupBox.SmtpEmailField getSmtpEmailField() {
        return getFieldByClass(MainBox.MainGroupBox.SmtpEmailField.class);
    }

    public MainBox.MainGroupBox.SmtpPasswordField getSmtpPasswordField() {
        return getFieldByClass(MainBox.MainGroupBox.SmtpPasswordField.class);
    }

    public MainBox.MainGroupBox.SmtpPortField getSmtpPortField() {
        return getFieldByClass(MainBox.MainGroupBox.SmtpPortField.class);
    }

    public MainBox.MainGroupBox.SmtpProtocolRadioBox getSmtpProtocolRadioBox() {
        return getFieldByClass(MainBox.MainGroupBox.SmtpProtocolRadioBox.class);
    }

    public MainBox.MainGroupBox.SmtpServerField getSmtpServerField() {
        return getFieldByClass(MainBox.MainGroupBox.SmtpServerField.class);
    }

    public MainBox.MainGroupBox.SmtpUsernameField getSmtpUsernameField() {
        return getFieldByClass(MainBox.MainGroupBox.SmtpUsernameField.class);
    }

    public MainBox.MainGroupBox.TestEmailBox getTestEmailBox() {
        return getFieldByClass(MainBox.MainGroupBox.TestEmailBox.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
            return TEXTS.get("EmailSettings");
        }

        @Order(1000)
        public class MainGroupBox extends AbstractGroupBox {

            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Override
            public boolean isLabelVisible() {
                return false;
            }

            @Order(1000)
            public class SmtpServerField extends AbstractStringField {

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("SMTPServer");
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 220;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(2000)
            public class SmtpPortField extends AbstractIntegerField {
                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 220;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("SMTPPort");
                }

            }

            @Order(3000)
            public class SmtpUsernameField extends AbstractStringField {
                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 220;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("SMTPUsername");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(4000)
            public class SmtpPasswordField extends AbstractStringField {
                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 220;
                }

                @Override
                protected boolean getConfiguredInputMasked() {
                    return true;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("SMTPPassword");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(5000)
            public class SmtpEmailField extends AbstractStringField {
                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 220;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("SMTPEmail");
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }

                @Override
                protected String execValidateValue(String rawValue) {
                    if (rawValue != null) {
                        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                        if (!rawValue.matches(regex)) {
                            throw new VetoException(TEXTS.get("EmailIsInvalid"));
                        }
                    }

                    return rawValue;
                }
            }

            @Order(6000)
            public class SmtpProtocolRadioBox extends AbstractRadioButtonGroup<Integer> {
                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 220;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("SMTPProtocol");
                }

                @Order(1000)
                public class SSLProtocolKeyStroke extends AbstractRadioButton<Integer> {

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("SSL");
                    }

                    @Override
                    protected Integer getConfiguredRadioValue() {
                        return 1;
                    }

                    @Override
                    protected void execInitField() {
                        setSelected(true);
                    }
                }

                @Order(2000)
                public class TLSProtocolButton extends AbstractRadioButton<Integer> {

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("TLS");
                    }

                    @Override
                    protected Integer getConfiguredRadioValue() {
                        return 2;
                    }
                }
            }

            @Order(6500)
            public class BCCAllEmailsField extends AbstractEmailField {
                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 220;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("BCCAllEmailTo");
                }
            }

            @Order(6500)
            public class PredefinedBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("PredefinedHeaderfooter");
                }

                @Override
                public boolean isBorderVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Order(4000)
                public class PredefinedHeaderFooterBox extends AbstractTabBox {
                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    protected void injectFieldsInternal(OrderedCollection<IFormField> fields) {
                        super.injectFieldsInternal(fields);

                        List<Language> languages = BEANS.get(ILanguageService.class).all();
                        for (Language language : languages) {
                            fields.addLast(new LanguageBox(language.getCode()) {

                                @Override
                                protected String getConfiguredLabel() {
                                    return language.getName();
                                }

                                @Override
                                public double getOrder() {
                                    boolean isDefaultlanguage = ClientSession.get().getCurrentOrganisation().getOrganisationSettings().getLocalizationSettings().getLanguageId().intValue() == language.getId();

                                    return isDefaultlanguage ? -1 : 1;
                                }

                                @Override
                                protected int getConfiguredGridH() {
                                    return 6;
                                }

                                @Override
                                protected boolean getConfiguredVisible() {
                                    return true;
                                }

                                @Override
                                protected int getConfiguredGridW() {
                                    return 1;
                                }

                            });
                        }

                    }
                }
            }

            @Order(7000)
            public class TestEmailBox extends AbstractGroupBox {

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 1;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("SendTestEmail");
                }

                @Order(1000)
                public class EmailSendSequenceBox extends AbstractSequenceBox {

                    @Override
                    protected boolean getConfiguredAutoCheckFromTo() {
                        return false;
                    }

                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Order(1000)
                    public class RecipientField extends AbstractStringField {

                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("Email");
                        }

                        @Override
                        protected int getConfiguredMaxLength() {
                            return 128;
                        }
                    }

                    @Order(2000)
                    public class SendButton extends AbstractButton {

                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("SendEmail");
                        }

                        @Override
                        protected void execClickAction() {
                            if (getSmtpServerField().isEmpty() || getSmtpEmailField().isEmpty() || getSmtpPasswordField().isEmpty() || getSmtpPortField().isEmpty() || getSmtpProtocolRadioBox().isEmpty() || getSmtpUsernameField().isEmpty()) {
                                NotificationHelper.showNotification(TEXTS.get("RequiredFieldsAreEmptyOrInvalid"));
                                return;
                            }

                            if (getRecipientField().getValue() == null) {
                                NotificationHelper.showNotification(TEXTS.get("RecipientIsEmpty"));
                                return;
                            }

                            EmailSettingsFormData formData = new EmailSettingsFormData();
                            exportFormData(formData);

                            BEANS.get(IEmailSettingsService.class).sendTestEmail(formData);
                            NotificationHelper.showNotification(TEXTS.get("EmailSent"));
                        }
                    }
                }

            }
        }

        @Order(100000)
        public class OkButton extends AbstractSaveButton {
            @Override
            protected Boolean getConfiguredDefaultButton() {
                return true;
            }
        }
    }

    public class ModifyHandler extends AbstractFormHandler {

        @Override
        protected void execLoad() {
            IEmailSettingsService service = BEANS.get(IEmailSettingsService.class);
            EmailSettingsFormData formData = new EmailSettingsFormData();
            exportFormData(formData);
            formData = service.load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            loadLanguageContent();
        }

        @Override
        protected void execStore() {
            IEmailSettingsService service = BEANS.get(IEmailSettingsService.class);
            EmailSettingsFormData formData = new EmailSettingsFormData();
            exportFormData(formData);
            service.store(formData);
        }
    }

    protected class LanguageBox extends AbstractGroupBox {
        private String language;

        public LanguageBox(String language) {
            this.language = language;
        }

        public LanguageBox() {
            super();
        }

        public LanguageBox(boolean callInitializer) {
            super(callInitializer);
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        @Override
        protected boolean getConfiguredVisible() {
            return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
            return 1;
        }

        @Override
        protected boolean getConfiguredStatusVisible() {
            return false;
        }

        @Order(500)
        public class HeaderField extends AbstractTextAreaField {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("Header");
            }

            @Override
            protected int getConfiguredLabelWidthInPixel() {
                return 220;
            }

            @Override
            protected void execChangedValue() {
                touch();
            }

            @Override
            protected int getConfiguredMaxLength() {
                return 10000;
            }

            @Override
            protected int getConfiguredGridH() {
                return 5;
            }

        }

        @Order(1000)
        public class FooterField extends AbstractTextAreaField {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("Footer");
            }

            @Override
            protected int getConfiguredMaxLength() {
                return 10000;
            }

            @Override
            protected int getConfiguredGridH() {
                return 5;
            }

            @Override
            protected void execChangedValue() {
                touch();
            }

            @Override
            protected int getConfiguredLabelWidthInPixel() {
                return 220;
            }
        }
    }
}
