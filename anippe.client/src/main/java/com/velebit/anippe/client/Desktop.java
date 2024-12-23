package com.velebit.anippe.client;

import com.velebit.anippe.client.Desktop.UserProfileMenu.ThemeMenu.DarkThemeMenu;
import com.velebit.anippe.client.Desktop.UserProfileMenu.ThemeMenu.DefaultThemeMenu;
import com.velebit.anippe.client.clients.ClientForm;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.leads.LeadForm;
import com.velebit.anippe.client.projects.ProjectsOutline;
import com.velebit.anippe.client.reminders.ReminderForm;
import com.velebit.anippe.client.search.SearchOutline;
import com.velebit.anippe.client.settings.SettingsOutline;
import com.velebit.anippe.client.tasks.TaskForm;
import com.velebit.anippe.client.tickets.TicketViewForm;
import com.velebit.anippe.client.utilities.announcements.AnnouncementForm;
import com.velebit.anippe.client.work.TodoForm;
import com.velebit.anippe.client.work.WorkOutline;
import com.velebit.anippe.shared.Icons;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.reminders.IReminderService;
import com.velebit.anippe.shared.reminders.Reminder;
import com.velebit.anippe.shared.utilities.announcements.Announcement;
import com.velebit.anippe.shared.utilities.announcements.IAnnouncementService;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.*;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.datachange.DataChangeEvent;
import org.eclipse.scout.rt.client.ui.desktop.datachange.IDataChangeListener;
import org.eclipse.scout.rt.client.ui.desktop.datachange.ItemDataChangeEvent;
import org.eclipse.scout.rt.client.ui.desktop.notification.NativeNotificationDefaults;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.client.ui.popup.AbstractFormPopup;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.security.IAccessControlService;
import org.quartz.SimpleScheduleBuilder;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lukacavic
 */
public class Desktop extends AbstractDesktop {
    protected final IDataChangeListener m_dataChangeListener = this::onDataChanged;

    public Desktop() {
        addPropertyChangeListener(PROP_THEME, this::onThemeChanged);
    }

    private void checkReminders() {
        Reminder reminder = BEANS.get(IReminderService.class).findReminderToShow();

        if (reminder != null) {
            ReminderForm form = new ReminderForm();
            form.setReminderId(reminder.getId());
            form.startView();
            form.waitFor();
        }
    }

    private void checkAnnouncements() {
        Announcement announcement = BEANS.get(IAnnouncementService.class).findAnnouncementToShow();

        if (announcement == null) return;

        AnnouncementForm form = new AnnouncementForm();
        form.setAnnouncementId(announcement.getId());
        form.startPreview();
    }

    private void startModelJobs() {
        ModelJobs.schedule(() -> {
            checkAnnouncements();
            checkReminders();
        }, ModelJobs.newInput(ClientRunContexts.copyCurrent()).withName("ModelJobs").withRunContext(ClientRunContexts.copyCurrent()).withExecutionTrigger(Jobs.newExecutionTrigger().withStartIn(5, TimeUnit.SECONDS).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever())));
    }

    private void onDataChanged(DataChangeEvent dataChangeEvent) {
        if (dataChangeEvent.getSource().getClass().getName().equals(Announcement.class.getName())) {
            ItemDataChangeEvent itemDataChangeEvent = (ItemDataChangeEvent) dataChangeEvent;

            Announcement announcement = (Announcement) dataChangeEvent.getSource();
            User user = (User) itemDataChangeEvent.getData();

            if (user.getId().equals(ClientSession.get().getCurrentUser().getId())) return;

            NotificationHelper.showNotification(TEXTS.get("NewAnnouncementFrom", user.getFullName()));

            AnnouncementForm form = new AnnouncementForm();
            form.setAnnouncementId(announcement.getId());
            form.startPreview();
        }
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ApplicationTitle");
    }

    @Override
    protected String getConfiguredLogoId() {
        return Icons.AppLogo;
    }

    @Override
    protected void execClosing() {
        super.execClosing();

        removeDataChangeListener(m_dataChangeListener);
    }

    @Override
    protected void execInit() {
        super.execInit();

        addDataChangeListener(m_dataChangeListener, Announcement.class);

        startModelJobs();
    }

    @Override
    protected NativeNotificationDefaults getConfiguredNativeNotificationDefaults() {
        return super.getConfiguredNativeNotificationDefaults().withIconId("application_logo.png");
    }

    @Override
    protected List<Class<? extends IOutline>> getConfiguredOutlines() {
        return CollectionUtility.<Class<? extends IOutline>>arrayList(WorkOutline.class, SearchOutline.class, SettingsOutline.class, ProjectsOutline.class);
    }

    @Override
    protected void execDefaultView() {
        selectFirstVisibleOutline();
    }

    protected void selectFirstVisibleOutline() {
        for (IOutline outline : getAvailableOutlines()) {
            if (outline.isEnabled() && outline.isVisible()) {
                setOutline(outline.getClass());
                return;
            }
        }
    }

    protected void onThemeChanged(PropertyChangeEvent evt) {
        IMenu darkMenu = getMenuByClass(DarkThemeMenu.class);
        IMenu defaultMenu = getMenuByClass(DefaultThemeMenu.class);
        String newThemeName = (String) evt.getNewValue();
        if (DarkThemeMenu.DARK_THEME.equalsIgnoreCase(newThemeName)) {
            darkMenu.setIconId(Icons.CheckedBold);
            defaultMenu.setIconId(null);
        } else {
            darkMenu.setIconId(null);
            defaultMenu.setIconId(Icons.CheckedBold);
        }
    }

    @Order(0)
    public class QuickAddMenu extends AbstractMenu {
        @Override
        protected String getConfiguredIconId() {
            return FontIcons.CirclePlus;
        }

        @Order(1000)
        public class NewClientMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("NewClient");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Users1;
            }

            @Override
            protected void execAction() {
                ClientForm form = new ClientForm();
                form.startNew();
            }
        }

        @Order(2000)
        public class NewLead extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("NewLead");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.UserPlus;
            }

            @Override
            protected void execAction() {
                LeadForm form = new LeadForm();
                form.startNew();
                form.waitFor();
                if(form.isFormStored()) {
                    NotificationHelper.showSaveSuccessNotification();
                }
            }
        }

        @Order(2000)
        public class NewTicketMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("NewTicket");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Info;
            }

            @Override
            protected void execAction() {
                TicketViewForm form = new TicketViewForm();
                form.startNew();
            }
        }

        @Order(2000)
        public class NewTask extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("NewTask");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Tasks;
            }

            @Override
            protected void execAction() {
                TaskForm form = new TaskForm();
                form.startNew();
            }
        }
    }

    @Order(-0.5)
    public class TodoMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType>hashSet(TableMenuType.EmptySpace, TableMenuType.SingleSelection);
        }

        @Override
        protected String getConfiguredIconId() {
            return FontIcons.Tasks;
        }

        @Override
        protected boolean getConfiguredHtmlEnabled() {
            return true;
        }

        @Override
        protected void execAction() {
            AbstractFormPopup<TodoForm> popup = new AbstractFormPopup<TodoForm>() {
                @Override
                protected TodoForm createForm() {
                    TodoForm form = new TodoForm();

                    return form;
                }

            };

            popup.setAnchor(MenuUtility.getMenuByClass(Desktop.this, TodoMenu.class));
            popup.setCloseOnMouseDownOutside(false);
            popup.setHorizontalSwitch(true);
            popup.setTrimWidth(true);
            popup.setTrimHeight(true);
            popup.setWithArrow(true);
            popup.setClosable(true);
            popup.setMovable(false);
            popup.setResizable(true);
            popup.open();
        }
    }

    @Order(500)
    public class InboxMenu extends AbstractMenu {

        @Override
        protected String getConfiguredIconId() {
            return FontIcons.Inbox;
        }

        @Override
        protected void execAction() {

        }
    }

    @Order(1000)
    public class UserProfileMenu extends AbstractMenu {

        @Override
        protected String getConfiguredKeyStroke() {
            return IKeyStroke.F10;
        }

        @Override
        protected String getConfiguredIconId() {
            return Icons.PersonSolid;
        }

        @Override
        protected String getConfiguredText() {
            String userId = BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject();
            return StringUtility.uppercaseFirst(userId);
        }

        @Order(1000)
        public class AboutMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
                return TEXTS.get("About");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Info;
            }

            @Override
            protected void execAction() {
                ScoutInfoForm form = new ScoutInfoForm();
                form.startModify();
            }
        }

        @Order(2000)
        public class ThemeMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
                return TEXTS.get("Theme");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Pencil;
            }

            @Order(1000)
            public class DefaultThemeMenu extends AbstractMenu {

                private static final String DEFAULT_THEME = "Default";

                @Override
                protected String getConfiguredText() {
                    return DEFAULT_THEME;
                }

                @Override
                protected void execAction() {
                    setTheme(DEFAULT_THEME.toLowerCase());
                }
            }

            @Order(2000)
            public class DarkThemeMenu extends AbstractMenu {

                private static final String DARK_THEME = "Dark";

                @Override
                protected String getConfiguredText() {
                    return DARK_THEME;
                }

                @Override
                protected void execAction() {
                    setTheme(DARK_THEME.toLowerCase());
                }
            }
        }

        @Order(3000)
        public class LogoutMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
                return TEXTS.get("Logout");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.PowerOff;
            }

            @Override
            protected void execAction() {
                ClientSessionProvider.currentSession().stop();
            }
        }
    }

    @Order(1000)
    public class WorkOutlineViewButton extends AbstractOutlineViewButton {

        public WorkOutlineViewButton() {
            this(WorkOutline.class);
        }

        protected WorkOutlineViewButton(Class<? extends WorkOutline> outlineClass) {
            super(Desktop.this, outlineClass);
        }

        @Override
        protected String getConfiguredKeyStroke() {
            return IKeyStroke.F2;
        }
    }

    @Order(2000)
    public class ProjectsOutlineViewButton extends AbstractOutlineViewButton {

        public ProjectsOutlineViewButton() {
            this(ProjectsOutline.class);
        }

        protected ProjectsOutlineViewButton(Class<? extends ProjectsOutline> outlineClass) {
            super(Desktop.this, outlineClass);
        }

        @Override
        protected String getConfiguredKeyStroke() {
            return IKeyStroke.F4;
        }
    }


    @Order(3000)
    public class SettingsOutlineViewButton extends AbstractOutlineViewButton {

        public SettingsOutlineViewButton() {
            this(SettingsOutline.class);
        }

        protected SettingsOutlineViewButton(Class<? extends SettingsOutline> outlineClass) {
            super(Desktop.this, outlineClass);
        }

        @Override
        protected DisplayStyle getConfiguredDisplayStyle() {
            return DisplayStyle.TAB;
        }

        @Override
        protected String getConfiguredKeyStroke() {
            return IKeyStroke.F10;
        }
    }
}
