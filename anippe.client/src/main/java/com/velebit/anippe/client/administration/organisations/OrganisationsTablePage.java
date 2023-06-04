package com.velebit.anippe.client.administration.organisations;

import com.velebit.anippe.client.administration.organisations.OrganisationsTablePage.Table;
import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.interaction.NotificationHelper;
import com.velebit.anippe.shared.administration.organisations.IOrganisationsService;
import com.velebit.anippe.shared.administration.organisations.OrganisationsTablePageData;
import com.velebit.anippe.shared.icons.FontIcons;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(OrganisationsTablePageData.class)
public class OrganisationsTablePage extends AbstractPageWithTable<Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IOrganisationsService.class).getOrganisationsTableData(filter));
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Building;
    }

    @Override
    protected String getConfiguredOverviewIconId() {
        return FontIcons.Building;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Organisations");
    }

    @Order(1000)
    public class AddMenu extends AbstractAddMenu {

        @Override
        protected void execAction() {
            OrganisationForm form = new OrganisationForm();
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

        public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
        }
        public OrganisationIdColumn getOrganisationIdColumn() {
            return getColumnSet().getColumnByClass(OrganisationIdColumn.class);
        }

        @Order(1000)
        public class EditMenu extends AbstractEditMenu {

            @Override
            protected void execAction() {
                OrganisationForm form = new OrganisationForm();
                form.setOrganisationId(getOrganisationIdColumn().getSelectedValue());
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

            }
        }

        @Order(1000)
        public class OrganisationIdColumn extends AbstractIDColumn {

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
    }
}
