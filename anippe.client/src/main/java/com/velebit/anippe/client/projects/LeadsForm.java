package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.leads.AbstractLeadsTable;
import com.velebit.anippe.client.leads.LeadForm;
import com.velebit.anippe.client.projects.LeadsForm.MainBox.GroupBox;
import com.velebit.anippe.shared.projects.ILeadsService;
import com.velebit.anippe.shared.projects.LeadsFormData;
import com.velebit.anippe.shared.projects.LeadsFormData.LeadsTable.LeadsTableRowData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;

@FormData(value = LeadsFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LeadsForm extends AbstractForm {

    private Integer projectId;

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        fetchLeads();
    }

    public void fetchLeads() {
        List<LeadsTableRowData> rows = BEANS.get(ILeadsService.class).fetchLeads(getProjectId());
        getLeadsTableField().getTable().importFromTableRowBeanData(rows, LeadsTableRowData.class);
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Leads");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.LeadsTableField getLeadsTableField() {
        return getFieldByClass(GroupBox.LeadsTableField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Order(1000)
            public class LeadsTableField extends AbstractTableField<LeadsTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                public boolean isStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @ClassId("122a638c-1803-47eb-9e5e-28a2790ba373")
                public class Table extends AbstractLeadsTable {

                    @Override
                    public void reloadData() {

                    }
                }
            }

        }

    }

}
