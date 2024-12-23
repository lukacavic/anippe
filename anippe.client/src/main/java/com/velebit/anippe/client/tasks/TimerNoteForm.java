package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tasks.TimerNoteForm.MainBox.CancelButton;
import com.velebit.anippe.client.tasks.TimerNoteForm.MainBox.GroupBox;
import com.velebit.anippe.client.tasks.TimerNoteForm.MainBox.OkButton;
import com.velebit.anippe.shared.tasks.ITimerNoteService;
import com.velebit.anippe.shared.tasks.TimerNoteFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = TimerNoteFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TimerNoteForm extends AbstractForm {

    private Integer taskTimerId;

    @FormData
    public Integer getTaskTimerId() {
        return taskTimerId;
    }

    @FormData
    public void setTaskTimerId(Integer taskTimerId) {
        this.taskTimerId = taskTimerId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Note");
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

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    @Override
    protected void execStored() {
        super.execStored();

        NotificationHelper.showSaveSuccessNotification();
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredMenuBarPosition() {
            return MENU_BAR_POSITION_BOTTOM;
        }

        @Override
        protected int getConfiguredWidthInPixel() {
            return 400;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class NoteField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Note");
                }

                @Override
                protected int getConfiguredGridH() {
                    return 4;
                }

                @Override
                protected boolean getConfiguredMultilineText() {
                    return true;
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
                protected boolean getConfiguredWrapText() {
                    return true;
                }

                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
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
            protected int getConfiguredHorizontalAlignment() {
                return 1;
            }
        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {
            @Override
            public boolean isVisible() {
                return false;
            }
        }
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TimerNoteFormData formData = new TimerNoteFormData();
            exportFormData(formData);
            formData = BEANS.get(ITimerNoteService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TimerNoteFormData formData = new TimerNoteFormData();
            exportFormData(formData);
            formData = BEANS.get(ITimerNoteService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TimerNoteFormData formData = new TimerNoteFormData();
            exportFormData(formData);
            formData = BEANS.get(ITimerNoteService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TimerNoteFormData formData = new TimerNoteFormData();
            exportFormData(formData);
            formData = BEANS.get(ITimerNoteService.class).store(formData);
            importFormData(formData);
        }
    }
}
