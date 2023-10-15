package com.velebit.anippe.client.projects.settings;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.projects.PredefinedReplyForm;
import com.velebit.anippe.client.projects.settings.PredefinedRepliesTablePage.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.Project;
import com.velebit.anippe.shared.projects.settings.IPredefinedRepliesService;
import com.velebit.anippe.shared.projects.settings.PredefinedRepliesTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(PredefinedRepliesTablePageData.class)
public class PredefinedRepliesTablePage extends AbstractPageWithTable<Table> {

    private Project project;

    public PredefinedRepliesTablePage(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IPredefinedRepliesService.class).getPredefinedRepliesTableData(filter, project.getId()));
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.UserCheck;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.UserCheck;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("PredefinedReplies");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            PredefinedReplyForm form = new PredefinedReplyForm();
            form.setProjectId(getProject().getId());
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }
    }

    public class Table extends AbstractTable {

        @Order(1000)
        public class EditMenu extends AbstractEditMenu {

            @Override
            protected void execAction() {
                PredefinedReplyForm form = new PredefinedReplyForm();
                form.setPredefinedReplyId(getPredefinedReplyIdColumn().getSelectedValue());
                form.startModify();
                form.waitFor();
                if (form.isFormStored()) {
                    NotificationHelper.showSaveSuccessNotification();

                    reloadPage();
                }
            }
        }

        @Order(2000)
        public class DeleteMenu extends AbstractDeleteMenu {

            @Override
            protected void execAction() {
                if (MessageBoxHelper.showDeleteConfirmationMessage() == IMessageBox.YES_OPTION) {
                    BEANS.get(IPredefinedRepliesService.class).delete(getPredefinedReplyIdColumn().getSelectedValue());

                    NotificationHelper.showDeleteSuccessNotification();

                    reloadPage();
                }
            }
        }

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        public ContentColumn getContentColumn() {
            return getColumnSet().getColumnByClass(ContentColumn.class);
        }

        public PredefinedReplyIdColumn getPredefinedReplyIdColumn() {
            return getColumnSet().getColumnByClass(PredefinedReplyIdColumn.class);
        }

        public TitleColumn getTitleColumn() {
            return getColumnSet().getColumnByClass(TitleColumn.class);
        }

        @Order(1000)
        public class PredefinedReplyIdColumn extends AbstractIDColumn {

        }

        @Order(2000)
        public class TitleColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Title");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(3000)
        public class ContentColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Content");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }
    }
}
