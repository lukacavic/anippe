package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.tickets.QuickNoteForm.MainBox.CancelButton;
import com.velebit.anippe.client.tickets.QuickNoteForm.MainBox.GroupBox;
import com.velebit.anippe.client.tickets.QuickNoteForm.MainBox.OkButton;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tickets.IQuickNoteService;
import com.velebit.anippe.shared.tickets.QuickNoteFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = QuickNoteFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class QuickNoteForm extends AbstractForm {

    private Integer ticketId;

    @FormData
    public Integer getTicketId() {
        return ticketId;
    }

    @FormData
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("QuickNote");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Note;
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

    public GroupBox.NoteField getNoteField() {
        return getFieldByClass(GroupBox.NoteField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 500;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class NoteField extends AbstractTextAreaField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Note");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_ON_FIELD;
                }
            }

        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("Save");
            }

            @Override
            protected void execClickAction() {
                QuickNoteFormData formData = new QuickNoteFormData();
                exportFormData(formData);
                formData = BEANS.get(IQuickNoteService.class).create(formData);
                importFormData(formData);
            }
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
            QuickNoteFormData formData = new QuickNoteFormData();
            exportFormData(formData);
            formData = BEANS.get(IQuickNoteService.class).prepareCreate(formData);
            importFormData(formData);

        }

        @Override
        protected void execStore() {
            QuickNoteFormData formData = new QuickNoteFormData();
            exportFormData(formData);
            formData = BEANS.get(IQuickNoteService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            QuickNoteFormData formData = new QuickNoteFormData();
            exportFormData(formData);
            formData = BEANS.get(IQuickNoteService.class).load(formData);
            importFormData(formData);

        }

        @Override
        protected void execStore() {
            QuickNoteFormData formData = new QuickNoteFormData();
            exportFormData(formData);
            formData = BEANS.get(IQuickNoteService.class).store(formData);
            importFormData(formData);
        }
    }
}
