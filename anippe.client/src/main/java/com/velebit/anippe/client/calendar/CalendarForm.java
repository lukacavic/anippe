package com.velebit.anippe.client.calendar;

import com.velebit.anippe.client.calendar.CalendarForm.MainBox.GroupBox;
import com.velebit.anippe.shared.calendar.CalendarFormData;
import com.velebit.anippe.shared.calendar.CreateCalendarPermission;
import com.velebit.anippe.shared.calendar.ICalendarService;
import com.velebit.anippe.shared.calendar.UpdateCalendarPermission;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.calendar.AbstractCalendar;
import org.eclipse.scout.rt.client.ui.basic.calendar.provider.AbstractCalendarItemProvider;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.calendarfield.AbstractCalendarField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = CalendarFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CalendarForm extends AbstractForm {
    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Calendar");
    }

    public GroupBox.CalendarField getCalendarField() {
        return getFieldByClass(GroupBox.CalendarField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(1000)
        public class FilterMenu extends AbstractMenu {
            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Filter;
            }

            @Override
            protected byte getConfiguredHorizontalAlignment() {
                return 1;
            }

            @Override
            protected void execAction() {

            }
        }
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Order(1000)
            public class CalendarField extends AbstractCalendarField<CalendarField.Calendar> {

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @ClassId("1380e29e-b2d1-4668-9c3f-5e4fb4444515")
                public class Calendar extends AbstractCalendar {
                    @Order(1000)
                    public class TasksItemProvider extends AbstractCalendarItemProvider {

                    }
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @Override
                protected boolean getConfiguredLabelVisible() {
                    return false;
                }
            }
        }

    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            CalendarFormData formData = new CalendarFormData();
            exportFormData(formData);
            formData = BEANS.get(ICalendarService.class).prepareCreate(formData);
            importFormData(formData);

            setEnabledPermission(new CreateCalendarPermission());
        }

        @Override
        protected void execStore() {
            CalendarFormData formData = new CalendarFormData();
            exportFormData(formData);
            formData = BEANS.get(ICalendarService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            CalendarFormData formData = new CalendarFormData();
            exportFormData(formData);
            formData = BEANS.get(ICalendarService.class).load(formData);
            importFormData(formData);

            setEnabledPermission(new UpdateCalendarPermission());
        }

        @Override
        protected void execStore() {
            CalendarFormData formData = new CalendarFormData();
            exportFormData(formData);
            formData = BEANS.get(ICalendarService.class).store(formData);
            importFormData(formData);
        }
    }
}
