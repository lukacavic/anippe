package com.velebit.anippe.client.events;

import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.common.fields.colorfield.AbstractColorField;
import com.velebit.anippe.client.events.EventForm.MainBox.CancelButton;
import com.velebit.anippe.client.events.EventForm.MainBox.GroupBox;
import com.velebit.anippe.client.events.EventForm.MainBox.OkButton;
import com.velebit.anippe.shared.events.EventFormData;
import com.velebit.anippe.shared.events.IEventService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = EventFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class EventForm extends AbstractForm {

    private Integer eventId;

    @FormData
    public Integer getEventId() {
        return eventId;
    }

    @FormData
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Event");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Calendar;
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

    public GroupBox.ColorField getColorField() {
        return getFieldByClass(GroupBox.ColorField.class);
    }

    public GroupBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DescriptionField.class);
    }

    public GroupBox.EndAtField getEndAtField() {
        return getFieldByClass(GroupBox.EndAtField.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    public GroupBox.StartAtField getStartAtField() {
        return getFieldByClass(GroupBox.StartAtField.class);
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
                return 1;
            }

            @Order(1000)
            public class NameField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Name");
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 90;
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(1500)
            public class DescriptionField extends AbstractTextAreaField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Description");
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 90;
                }
            }


            @Order(2000)
            public class StartAtField extends AbstractDateTimeField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("StartAt");
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 90;
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }
            }

            @Order(3000)
            public class EndAtField extends AbstractDateTimeField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("EndAt");
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 90;
                }
            }

            @Order(4000)
            public class ColorField extends AbstractColorField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Color");
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 90;
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

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            EventFormData formData = new EventFormData();
            exportFormData(formData);
            formData = BEANS.get(IEventService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            EventFormData formData = new EventFormData();
            exportFormData(formData);
            formData = BEANS.get(IEventService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            EventFormData formData = new EventFormData();
            exportFormData(formData);
            formData = BEANS.get(IEventService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            EventFormData formData = new EventFormData();
            exportFormData(formData);
            formData = BEANS.get(IEventService.class).store(formData);
            importFormData(formData);
        }
    }
}
