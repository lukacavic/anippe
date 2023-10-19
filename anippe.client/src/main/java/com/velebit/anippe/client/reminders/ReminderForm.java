package com.velebit.anippe.client.reminders;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.interaction.FormNotificationHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.CancelButton;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.GroupBox;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.GroupBox.ContentField;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.GroupBox.RemindAtField;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.GroupBox.SendEmailForReminderField;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.GroupBox.TitleField;
import com.velebit.anippe.client.reminders.ReminderForm.MainBox.OkButton;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.reminders.IReminderService;
import com.velebit.anippe.shared.reminders.ReminderFormData;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.notification.Notification;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Date;

@FormData(value = ReminderFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ReminderForm extends AbstractForm {
    private Integer relatedId;
    private Integer relatedType;

    private Integer reminderId;

    @Override
    protected void execInitForm() {
        super.execInitForm();

        addFormListener(new FormListener() {

            @Override
            public void formChanged(FormEvent e) {
                setSubTitle(getReminderId() != null ? TEXTS.get("ViewEntry") : TEXTS.get("NewEntry"));
            }

        });
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

    @FormData
    public Integer getReminderId() {
        return reminderId;
    }

    @FormData
    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("NewReminder");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public TitleField getTitleField() {
        return getFieldByClass(TitleField.class);
    }

    @Override
    protected void execStored() {
        NotificationHelper.showSaveSuccessNotification();
    }

    public ContentField getContentField() {
        return getFieldByClass(ContentField.class);
    }

    public RemindAtField getRemindAtField() {
        return getFieldByClass(RemindAtField.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public MainBox.MarkAsCompletedButton getMarkAsCompletedButton() {
        return getFieldByClass(MainBox.MarkAsCompletedButton.class);
    }

    public SendEmailForReminderField getSendEmailForReminderField() {
        return getFieldByClass(SendEmailForReminderField.class);
    }

    public MainBox.SnoozeButton getSnoozeButton() {
        return getFieldByClass(MainBox.SnoozeButton.class);
    }

    public GroupBox.UserCreatedField getUserCreatedField() {
        return getFieldByClass(GroupBox.UserCreatedField.class);
    }

    public GroupBox.UserField getUserField() {
        return getFieldByClass(GroupBox.UserField.class);
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Clock;
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startView() {
        startInternalExclusive(new ViewHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Override
        public int getLabelWidthInPixel() {
            return 700;
        }

        @Override
        protected int getConfiguredWidthInPixel() {
            return 500;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Override
            protected void execInitField() {
                super.execInitField();

                getFields().forEach(f -> f.setDisabledStyle(DISABLED_STYLE_READ_ONLY));
            }

            @Order(1000)
            public class TitleField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Title");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
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

            @Order(1500)
            public class RemindAtField extends AbstractDateTimeField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("ReminderAt");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected void execInitField() {
                    setValue(DateUtility.addDays(new Date(), 1));
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 130;
                }

                @Override
                protected Date execValidateValue(Date rawValue) {
                    if (rawValue == null)
                        return null;

                    if (getReminderId() == null && rawValue.before(new Date())) {
                        throw new VetoException(TEXTS.get("CannotSetReminderAtThisTime"));
                    }

                    return rawValue;
                }
            }

            @Order(1562)
            public class UserCreatedField extends AbstractSmartField<Long> {
                @Override
                public boolean isVisible() {
                    return false;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }
            }

            @Order(1625)
            public class UserField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("User");
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected void execInitField() {
                    super.execInitField();

                    User user = ClientSession.get().getCurrentUser();
                    if (!user.isAdministrator()) {
                        setValue(user.getId().longValue());
                    }
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 130;
                }
            }

            @Order(1750)
            public class SendEmailForReminderField extends AbstractBooleanField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("SendEmailForReminder");
                }

                @Override
                public boolean isVisibleGranted() {
                    return false;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 130;
                }

            }

            @Order(2000)
            public class ContentField extends AbstractTextAreaField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Content");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 130;
                }

            }

        }

        @Order(1500)
        public class SnoozeButton extends AbstractButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("Snooze");
            }

            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Clock;
            }

            @Override
            protected void execClickAction() {
                BEANS.get(IReminderService.class).snoozeReminder(getReminderId(), DateUtility.addMinutes(new Date(), 15));
                NotificationHelper.showSaveSuccessNotification();

                setFormStored(true);
                doClose();
            }
        }

        @Order(1750)
        public class MarkAsCompletedButton extends AbstractButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("MarkAsCompleted");
            }

            @Override
            protected int getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Override
            protected Boolean getConfiguredDefaultButton() {
                return true;
            }

            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Check;
            }

            @Override
            protected void execClickAction() {
                BEANS.get(IReminderService.class).markAsNotified(getReminderId());
                NotificationHelper.showSaveSuccessNotification();

                setFormStored(true);
                doClose();
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
            ReminderFormData formData = new ReminderFormData();
            exportFormData(formData);
            formData = BEANS.get(IReminderService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ReminderFormData formData = new ReminderFormData();
            exportFormData(formData);
            formData = BEANS.get(IReminderService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ReminderFormData formData = new ReminderFormData();
            exportFormData(formData);
            formData = BEANS.get(IReminderService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            ReminderFormData formData = new ReminderFormData();
            exportFormData(formData);
            formData = BEANS.get(IReminderService.class).store(formData);
            importFormData(formData);
        }
    }

    public class ViewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            ReminderFormData formData = new ReminderFormData();
            exportFormData(formData);
            formData = BEANS.get(IReminderService.class).loadView(formData);
            importFormData(formData);
        }

        @Override
        protected void execPostLoad() {
            super.execPostLoad();

            if (getUserCreatedField().getValue().intValue() != ClientSession.get().getCurrentUser().getId()) {
                String user = BEANS.get(IReminderService.class).findUserById(getUserCreatedField().getValue());
                String content = TEXTS.get("ReminderCreatedByUser", user);

                Notification notification = BEANS.get(FormNotificationHelper.class).createInfoNotification(content);

                getMainBox().setNotification(notification);
            }

            getSnoozeButton().setVisible(true);
            setSubTitle(TEXTS.get("ViewEntry"));
            getGroupBox().setEnabled(false);
            getOkButton().setVisible(false);
            getCancelButton().setVisible(false);
            getMarkAsCompletedButton().setVisible(true);
            getSendEmailForReminderField().setVisible(false);
            setTitle(TEXTS.get("Reminder"));
        }
    }

}
