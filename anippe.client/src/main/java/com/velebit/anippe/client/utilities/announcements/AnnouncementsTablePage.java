package com.velebit.anippe.client.utilities.announcements;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.MessageBoxHelper;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.client.utilities.announcements.AnnouncementsTablePage.Table;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.utilities.announcements.AnnouncementsTablePageData;
import com.velebit.anippe.shared.utilities.announcements.IAnnouncementsService;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(AnnouncementsTablePageData.class)
public class AnnouncementsTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IAnnouncementsService.class).getAnnouncementsTableData(filter));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Announcements");
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Bell;
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Bell;
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {
        
        @Override
        protected void execAction() {
            AnnouncementForm form = new AnnouncementForm();
            form.startNew();
            form.waitFor();
            if (form.isFormStored()) {
                NotificationHelper.showSaveSuccessNotification();

                reloadPage();
            }
        }
    }

    public class Table extends AbstractTable {
        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        public CreatedAtColumn getCreatedAtColumn() {
            return getColumnSet().getColumnByClass(CreatedAtColumn.class);
        }

        public CreatedByColumn getCreatedByColumn() {
            return getColumnSet().getColumnByClass(CreatedByColumn.class);
        }

        public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
        }

        public AnnouncementIdColumn getAnnouncementIdColumn() {
            return getColumnSet().getColumnByClass(AnnouncementIdColumn.class);
        }

        @Order(0)
        public class EditMenu extends AbstractEditMenu {

            @Override
            protected void execAction() {
                AnnouncementForm form = new AnnouncementForm();
                form.setAnnouncementId(getAnnouncementIdColumn().getSelectedValue());
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
                    BEANS.get(IAnnouncementsService.class).delete(getAnnouncementIdColumn().getSelectedValue());

                    NotificationHelper.showDeleteSuccessNotification();

                    reloadPage();
                }
            }
        }

        @Order(1000)
        public class AnnouncementIdColumn extends AbstractIntegerColumn {
            @Override
            protected boolean getConfiguredDisplayable() {
                return false;
            }
        }

        @Order(2000)
        public class NameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Name");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(3000)
        public class CreatedByColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("CreatedBy");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }

        @Order(4000)
        public class CreatedAtColumn extends AbstractDateTimeColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("CreatedAt");
            }

            @Override
            protected int getConfiguredWidth() {
                return 100;
            }
        }
    }
}
