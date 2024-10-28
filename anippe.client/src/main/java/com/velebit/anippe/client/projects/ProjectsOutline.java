package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.ClientSession;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.Icons;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.IProjectsService;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;
import java.util.Set;

public class ProjectsOutline extends AbstractOutline {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Projects");
    }

    @Override
    protected String getConfiguredIconId() {
        return Icons.Folder;
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        super.execCreateChildPages(pageList);

        Integer userId = ClientSession.get().getCurrentUser().getId();
        List<Project> projects = BEANS.get(IProjectsService.class).findForUser(userId);

        if (CollectionUtility.isEmpty(projects)) return;
        for (Project project : projects) {
            pageList.add(new ProjectNodePage(project));
        }

    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {
        @Override
        protected String getConfiguredText() {
            return null;
        }

        @Override
        protected String getConfiguredIconId() {
            return FontIcons.CirclePlus;
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TreeMenuType.Header);
        }

        @Override
        protected void execAction() {
            ProjectForm form = new ProjectForm();
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();


            }
        }
    }
}
