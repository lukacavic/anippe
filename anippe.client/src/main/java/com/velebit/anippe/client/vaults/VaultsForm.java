package com.velebit.anippe.client.vaults;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.vaults.VaultsForm.MainBox.GroupBox;
import com.velebit.anippe.shared.vaults.IVaultsService;
import com.velebit.anippe.shared.vaults.VaultsFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = VaultsFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class VaultsForm extends AbstractForm {


    private Integer clientId;

    @FormData
    public Integer getClientId() {
        return clientId;
    }

    @FormData
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Vaults");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.VaultsTableField getVaultsTableField() {
        return getFieldByClass(GroupBox.VaultsTableField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Order(1000)
            public class AddMenu extends AbstractAddMenu {

                @Override
                protected void execAction() {

                }
            }

            @Order(1000)
            public class VaultsTableField extends AbstractTableField<VaultsTableField.Table> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @ClassId("8c65ea73-e891-421b-8391-a1b881bf8a25")
                public class Table extends AbstractTable {
                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return false;
                    }


                }
            }
        }

    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            VaultsFormData formData = new VaultsFormData();
            exportFormData(formData);
            formData = BEANS.get(IVaultsService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            VaultsFormData formData = new VaultsFormData();
            exportFormData(formData);
            formData = BEANS.get(IVaultsService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            VaultsFormData formData = new VaultsFormData();
            exportFormData(formData);
            formData = BEANS.get(IVaultsService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            VaultsFormData formData = new VaultsFormData();
            exportFormData(formData);
            formData = BEANS.get(IVaultsService.class).store(formData);
            importFormData(formData);
        }
    }
}
