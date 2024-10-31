package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.leads.AbstractLeadsTable;
import com.velebit.anippe.client.projects.LeadsForm.MainBox.GroupBox;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.leads.LeadSourceLookupCall;
import com.velebit.anippe.shared.leads.LeadStatusLookupCall;
import com.velebit.anippe.shared.projects.ILeadsService;
import com.velebit.anippe.shared.projects.LeadsFormData;
import com.velebit.anippe.shared.projects.LeadsFormData.LeadsTable.LeadsTableRowData;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.List;

@FormData(value = LeadsFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LeadsForm extends AbstractForm {

    private Integer projectId;

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        fetchLeads();
    }

    public void fetchLeads() {
        getLeadsTableField().getTable().discardAllRows();

        Long assignedUserId = getAssignedToField().getValue();
        Long statusId = getStatusField().getValue();
        Long sourceId = getSourceField().getValue();

        List<LeadsTableRowData> rows = BEANS.get(ILeadsService.class).fetchLeads(getProjectId(), statusId, sourceId, assignedUserId);
        getLeadsTableField().getTable().importFromTableRowBeanData(rows, LeadsTableRowData.class);
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Leads");
    }

    public GroupBox.FilterTableBox.AssignedToField getAssignedToField() {
        return getFieldByClass(GroupBox.FilterTableBox.AssignedToField.class);
    }

    public GroupBox.FilterTableBox getFilterTableBox() {
        return getFieldByClass(GroupBox.FilterTableBox.class);
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

    public GroupBox.FilterTableBox.ResetButton getResetButton() {
        return getFieldByClass(GroupBox.FilterTableBox.ResetButton.class);
    }

    public GroupBox.FilterTableBox.SearchButton getSearchButton() {
        return getFieldByClass(GroupBox.FilterTableBox.SearchButton.class);
    }

    public GroupBox.FilterTableBox.SourceField getSourceField() {
        return getFieldByClass(GroupBox.FilterTableBox.SourceField.class);
    }

    public GroupBox.FilterTableBox.StatusField getStatusField() {
        return getFieldByClass(GroupBox.FilterTableBox.StatusField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Override
            protected boolean getConfiguredStatusVisible() {
                return false;
            }

            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Order(0)
            public class FilterTableBox extends AbstractSequenceBox {
                @Override
                protected byte getConfiguredLabelPosition() {
                    return LABEL_POSITION_TOP;
                }

                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("FilterBy");
                }

                @Override
                public boolean isStatusVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredAutoCheckFromTo() {
                    return false;
                }

                @Order(1000)
                public class AssignedToField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Assigned");
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return UserLookupCall.class;
                    }

                    @Override
                    protected void execPrepareLookup(ILookupCall<Long> call) {
                        if (getProjectId() != null) {
                            UserLookupCall c = (UserLookupCall) call;
                            c.setProjectId(getProjectId());
                        }
                    }
                }

                @Order(2000)
                public class StatusField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Status");
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }

                    @Override
                    protected void execPrepareLookup(ILookupCall<Long> call) {
                        super.execPrepareLookup(call);

                        LeadStatusLookupCall c = (LeadStatusLookupCall) call;
                        c.setProjectId(getProjectId());
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return LeadStatusLookupCall.class;
                    }

                }

                @Order(3000)
                public class SourceField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Source0");
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }

                    @Override
                    public boolean isStatusVisible() {
                        return false;
                    }

                    @Override
                    protected void execPrepareLookup(ILookupCall<Long> call) {
                        super.execPrepareLookup(call);

                        LeadSourceLookupCall c = (LeadSourceLookupCall) call;
                        c.setProjectId(getProjectId());
                    }

                    @Override
                    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return LeadSourceLookupCall.class;
                    }
                }

                @Order(3500)
                public class ResetButton extends AbstractButton {
                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Remove;
                    }

                    @Override
                    protected boolean getConfiguredProcessButton() {
                        return false;
                    }

                    @Override
                    protected Boolean getConfiguredDefaultButton() {
                        return false;
                    }

                    @Override
                    protected void execClickAction() {
                        getStatusField().setValue(null);
                        getAssignedToField().setValue(null);
                        getSourceField().setValue(null);

                        fetchLeads();
                    }
                }

                @Order(4000)
                public class SearchButton extends AbstractButton {
                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Search;
                    }

                    @Override
                    protected boolean getConfiguredProcessButton() {
                        return false;
                    }

                    @Override
                    protected Boolean getConfiguredDefaultButton() {
                        return true;
                    }

                    @Override
                    protected void execClickAction() {
                        fetchLeads();
                    }
                }

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
                    public boolean isUseHierarchy() {
                        return true;
                    }

                    @Override
                    public void reloadData() {
                        fetchLeads();
                    }


                }
            }

        }

    }

}
