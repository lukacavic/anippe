package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.projects.SupportForm.MainBox.GroupBox;
import com.velebit.anippe.client.tickets.AbstractTicketsTable;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.ISupportService;
import com.velebit.anippe.shared.projects.SupportFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = SupportFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SupportForm extends AbstractForm {

    private Integer projectId;
    private Integer clientId;

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @FormData
    public Integer getProjectId() {
        return projectId;
    }

    @FormData
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Support");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.TicketsTableField getTicketsTableField() {
        return getFieldByClass(GroupBox.TicketsTableField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(2000)
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
            public class TicketsTableField extends AbstractTableField<TicketsTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @ClassId("f2852610-de4f-43d6-95ef-9867ba98b85d")
                public class Table extends AbstractTicketsTable {

                    @Override
                    public void reloadData() {
                        
                    }
                }
            }
        }

    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            SupportFormData formData = new SupportFormData();
            exportFormData(formData);
            formData = BEANS.get(ISupportService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            SupportFormData formData = new SupportFormData();
            exportFormData(formData);
            formData = BEANS.get(ISupportService.class).create(formData);
            importFormData(formData);
        }
    }

}
