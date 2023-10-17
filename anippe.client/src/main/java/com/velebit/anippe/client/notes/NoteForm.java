package com.velebit.anippe.client.notes;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.attachments.AbstractAttachmentsBox;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.notes.NoteForm.MainBox.CancelButton;
import com.velebit.anippe.client.notes.NoteForm.MainBox.MainTabBox;
import com.velebit.anippe.client.notes.NoteForm.MainBox.MainTabBox.AttachmentsBox;
import com.velebit.anippe.client.notes.NoteForm.MainBox.MainTabBox.GroupBox.*;
import com.velebit.anippe.client.notes.NoteForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.notes.INoteService;
import com.velebit.anippe.shared.notes.NoteFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.Date;

@FormData(value = NoteFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class NoteForm extends AbstractForm {
    protected Integer noteId;
    protected Integer relatedType;
    protected Integer relatedId;

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.LibreOffice;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("CreateNewNote");
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("NewNote");
    }

    @Override
    protected void execStored() {
        NotificationHelper.showSaveSuccessNotification();
    }

    @FormData
    public Integer getNoteId() {
        return noteId;
    }

    @FormData
    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
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

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public ContentField getContentField() {
        return getFieldByClass(ContentField.class);
    }

    public CreatedAtField getCreatedAtField() {
        return getFieldByClass(CreatedAtField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public MainTabBox getMainTabBox() {
        return getFieldByClass(MainTabBox.class);
    }

    public AttachmentsBox getAttachmentsBox() {
        return getFieldByClass(AttachmentsBox.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    public PriorityField getPriorityField() {
        return getFieldByClass(PriorityField.class);
    }

    public TitleField getTitleField() {
        return getFieldByClass(TitleField.class);
    }

    public UserField getUserField() {
        return getFieldByClass(UserField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 700;
        }

        @Order(0)
        public class MainTabBox extends AbstractTabBox {
            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Order(1000)
            public class GroupBox extends AbstractGroupBox {
                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 2;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("MainInformations");
                }

                @Order(0)
                public class CreatedAtField extends AbstractDateTimeField {

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("CreatedAt");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 120;
                    }

                    @Override
                    protected void execInitField() {
                        setValue(new Date());
                    }

                    @Override
                    public boolean isEnabled() {
                        return false;
                    }
                }

                @Order(500)
                public class UserField extends AbstractStringField {

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("User");
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
                        setValue(ClientSession.get().getCurrentUser().getFullName());
                    }

                    @Override
                    public boolean isEnabled() {
                        return false;
                    }
                }

                @Order(1000)
                public class TitleField extends AbstractStringField {

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Title");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 120;
                    }

                    @Override
                    protected boolean getConfiguredMandatory() {
                        return true;
                    }
                }

                @Order(1500)
                public class PriorityField extends AbstractBooleanField {

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("PriorityNote");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 120;
                    }
                }

                @Order(2000)
                public class ContentField extends AbstractTextAreaField {

                    @Override
                    protected int getConfiguredGridH() {
                        return 3;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Content");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 120;
                    }

                    @Override
                    public boolean isMandatory() {
                        return true;
                    }
                }

            }

            @Order(2000)
            public class AttachmentsBox extends AbstractAttachmentsBox {

            }

        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {

        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {

        }
    }

    public class ModifyHandler extends AbstractFormHandler {

        @Override
        protected void execLoad() {
            INoteService service = BEANS.get(INoteService.class);
            NoteFormData formData = new NoteFormData();
            exportFormData(formData);
            formData = service.load(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            setTitle(getTitleField().getValue());
            setSubTitle(TEXTS.get("ViewEntry"));

            Integer attachmentsCount = getAttachmentsBox().getAttachmentsTableField().getTable().getRowCount();
            getAttachmentsBox().setLabel(TEXTS.get("Attachments") + " (" + attachmentsCount + ")");
        }

        @Override
        protected void execStore() {
            INoteService service = BEANS.get(INoteService.class);
            NoteFormData formData = new NoteFormData();
            exportFormData(formData);
            service.store(formData);
        }
    }

    public class NewHandler extends AbstractFormHandler {

        @Override
        protected void execLoad() {
            INoteService service = BEANS.get(INoteService.class);
            NoteFormData formData = new NoteFormData();
            exportFormData(formData);
            formData = service.prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            INoteService service = BEANS.get(INoteService.class);
            NoteFormData formData = new NoteFormData();
            exportFormData(formData);
            importFormData(service.create(formData));
        }
    }
}
