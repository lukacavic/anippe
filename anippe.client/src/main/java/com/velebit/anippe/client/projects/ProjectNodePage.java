package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.menus.AbstractActionsMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.tasks.TaskForm;
import com.velebit.anippe.client.tickets.TicketForm;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.IProjectService;
import com.velebit.anippe.shared.projects.IProjectsService;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;
import java.util.Set;

public class ProjectNodePage extends AbstractPageWithNodes {

    private Project project;

    public ProjectNodePage(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    @Override
    protected boolean getConfiguredShowTileOverview() {
        return true;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return project.getName();
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.FolderOpen;
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        pageList.add(new OverviewNodePage(project));
        pageList.add(new TasksNodePage(project));
        pageList.add(new SupportNodePage(project));
        pageList.add(new DocumentsNodePage(project));
        pageList.add(new SettingsNodePage(project));
    }

    @Order(1000)
    public class NewTaskMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewTask");
        }

        @Override
        protected byte getConfiguredHorizontalAlignment() {
            return 1;
        }

        @Override
        protected String getConfiguredIconId() {
            return FontIcons.Tasks;
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TreeMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
            TaskForm form = new TaskForm();
            form.setRelatedId(getProject().getId());
            form.setRelatedType(Constants.Related.PROJECT);
            form.startNew();
        }
    }

    @Order(1000)
    public class NewTicket extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewTicket");
        }

        @Override
        protected byte getConfiguredHorizontalAlignment() {
            return 1;
        }

        @Override
        protected String getConfiguredIconId() {
            return FontIcons.Info;
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TreeMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
            TicketForm form = new TicketForm();
            form.setProjectId(getProject().getId());
            form.startNew();
        }
    }

    @Order(2000)
    public class ActionsMenu extends AbstractActionsMenu {
        @Override
        protected boolean getConfiguredVisible() {
            return true;
        }

        @Override
        protected byte getConfiguredHorizontalAlignment() {
            return 1;
        }

        @Order(0)
        public class ManageUsersMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("ManageUsers");
            }

            @Override
            protected String getConfiguredIconId() {
                return FontIcons.Users1;
            }

            @Override
            protected void execAction() {
                ManageUsersForm form = new ManageUsersForm();
                form.setProjectId(getProject().getId());
                form.startNew();
                form.waitFor();
                if (form.isFormStored()) {

                }
            }
        }

        @Order(1000)
        public class DeleteMenu extends AbstractDeleteMenu {
            @Override
            protected void execAction() {
                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                    BEANS.get(IProjectsService.class).delete(getProject().getId());
                    NotificationHelper.showDeleteSuccessNotification();
                }
            }
        }
    }
}
