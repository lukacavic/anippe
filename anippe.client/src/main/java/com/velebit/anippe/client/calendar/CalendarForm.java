package com.velebit.anippe.client.calendar;

import com.velebit.anippe.client.calendar.CalendarForm.MainBox.GroupBox;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.events.EventForm;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.calendar.CalendarFormData;
import com.velebit.anippe.shared.calendar.ICalendarService;
import com.velebit.anippe.shared.events.Event;
import com.velebit.anippe.shared.events.IEventService;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.basic.calendar.AbstractCalendar;
import org.eclipse.scout.rt.client.ui.basic.calendar.provider.AbstractCalendarItemProvider;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.calendarfield.AbstractCalendarField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

                    @Override
                    protected boolean getConfiguredRangeSelectionAllowed() {
                        return true;
                    }

                    @Order(1000)
                    public class TasksItemProvider extends AbstractCalendarItemProvider {

                    }

                    @Order(1000)
                    public class EventsItemProvider extends AbstractCalendarItemProvider {
                        @Override
                        protected long getConfiguredRefreshIntervallMillis() {
                            return 10000;
                        }

                        @Override
                        protected boolean getConfiguredMoveItemEnabled() {
                            return true;
                        }

                        @Override
                        protected void execLoadItemsInBackground(IClientSession session, Date minDate, Date maxDate, Set<ICalendarItem> result) {
                            result.clear();

                            List<Event> events = BEANS.get(ICalendarService.class).fetchEvents(minDate, maxDate);
                            for (Event event : events) {
                                CalendarAppointment item = new CalendarAppointment();
                                item.setItemId(event.getId());
                                item.setEnd(event.getEndsAt());
                                item.setFullDay(event.isFullDay());
                                item.setStart(event.getStartAt());
                                item.setBody(event.getDescription());
                                item.setSubject(event.getName());
                                item.setBusyStatus(ICalendarAppointment.STATUS_BUSY);
                                item.setSubjectIconId(FontIcons.Calendar);

                                result.add(item);
                            }
                        }

                        @Override
                        protected void execItemMoved(ICalendarItem item, Date fromDate, Date toDate) {
                            super.execItemMoved(item, fromDate, toDate);

                            BEANS.get(IEventService.class).updateEventDate((Integer) item.getItemId(), fromDate, toDate);
                        }

                        @Order(1000)
                        public class NewEventMenu extends AbstractAddMenu {
                            @Override
                            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                return CollectionUtility.hashSet(CalendarMenuType.EmptySpace);
                            }

                            @Override
                            protected void execAction() {
                                EventForm form = new EventForm();
                                form.getStartAtField().setValue(getSelectedDate());
                                form.startNew();
                                form.waitFor();
                                if (form.isFormStored()) {
                                    reloadProvider();
                                }
                            }
                        }

                        @Order(2000)
                        public class EditEventMenu extends AbstractEditMenu {
                            @Override
                            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
                            }

                            @Override
                            protected void execAction() {
                                EventForm form = new EventForm();
                                form.setEventId((Integer) getSelectedComponent().getItem().getItemId());
                                form.startModify();
                                form.waitFor();
                                if (form.isFormStored()) {
                                    NotificationHelper.showSaveSuccessNotification();
                                    reloadProvider();
                                }
                            }
                        }

                        @Order(3000)
                        public class DeleteEventMenu extends AbstractDeleteMenu {
                            @Override
                            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                                return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
                            }

                            @Override
                            protected void execAction() {
                                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                                    BEANS.get(IEventService.class).delete((Integer) getSelectedComponent().getItem().getItemId());

                                    NotificationHelper.showDeleteSuccessNotification();

                                    reloadProvider();
                                }
                            }
                        }
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

}
