package com.velebit.anippe.client.events;

import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.events.EventForm.MainBox.CancelButton;
import com.velebit.anippe.client.events.EventForm.MainBox.GroupBox;
import com.velebit.anippe.client.events.EventForm.MainBox.OkButton;
import com.velebit.anippe.shared.events.EventFormData;
import com.velebit.anippe.shared.events.EventTypeCodeType;
import com.velebit.anippe.shared.events.IEventService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

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

    public GroupBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DescriptionField.class);
    }

    public GroupBox.EndAtField getEndAtField() {
        return getFieldByClass(GroupBox.EndAtField.class);
    }

    public GroupBox.PublicFullDayBox.FullDayField getFullDayField() {
        return getFieldByClass(GroupBox.PublicFullDayBox.FullDayField.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    public GroupBox.NameField getNameField() {
        return getFieldByClass(GroupBox.NameField.class);
    }

    public GroupBox.PublicFullDayBox.PublicField getPublicField() {
        return getFieldByClass(GroupBox.PublicFullDayBox.PublicField.class);
    }

    public GroupBox.PublicFullDayBox getPublicFullDayBox() {
        return getFieldByClass(GroupBox.PublicFullDayBox.class);
    }

    public GroupBox.StartAtField getStartAtField() {
        return getFieldByClass(GroupBox.StartAtField.class);
    }

    public GroupBox.TypeField getTypeField() {
        return getFieldByClass(GroupBox.TypeField.class);
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

            @Order(3500)
            public class TypeField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Type");
                }

                @Override
                protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
                    return EventTypeCodeType.class;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 90;
                }
            }

            @Order(7000)
            public class PublicFullDayBox extends AbstractSequenceBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredAutoCheckFromTo() {
                    return false;
                }

                @Override
                protected int getConfiguredLabelWidthInPixel() {
                    return 90;
                }

                @Order(5000)
                public class PublicField extends AbstractBooleanField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("PublicEvent");
                    }

                    @Override
                    protected int getConfiguredLabelWidthInPixel() {
                        return 90;
                    }
                }

                @Order(6000)
                public class FullDayField extends AbstractBooleanField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("FullDay");
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
