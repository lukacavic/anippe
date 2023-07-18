package com.velebit.anippe.client.vaults;

import com.velebit.anippe.client.common.columns.AbstractIDColumn;
import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.vaults.VaultsForm.MainBox.GroupBox;
import com.velebit.anippe.shared.vaults.VaultsFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = VaultsFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class VaultsForm extends AbstractForm {

    private Integer relatedId;
    private Integer relatedType;

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public Integer getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
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

    public void fetchVaults() {

    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Order(1000)
            public class AddMenu extends AbstractAddMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("AddVault");
                }

                @Override
                protected void execAction() {
                    VaultForm form = new VaultForm();
                    form.setRelatedId(getRelatedId());
                    form.setRelatedType(getRelatedType());
                    form.startNew();
                    form.waitFor();
                    if (form.isFormStored()) {
                        fetchVaults();
                    }
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

                    @Order(1000)
                    public class VaultIdColumn extends AbstractIDColumn {

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

                    @Order(0)
                    public class EditMenu extends AbstractEditMenu {

                        @Override
                        protected void execAction() {
                            VaultForm form = new VaultForm();
                            form.setVaultId(getVaultIdColumn().getSelectedValue());
                            form.startModify();
                            form.waitFor();
                            if (form.isFormStored()) {
                                fetchVaults();
                            }
                        }
                    }

                    @Order(1000)
                    public class DeleteMenu extends AbstractDeleteMenu {

                        @Override
                        protected void execAction() {

                        }
                    }

                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
                    }

                    public CreatedByColumn getCreatedByColumn() {
                        return getColumnSet().getColumnByClass(CreatedByColumn.class);
                    }

                    public NameColumn getNameColumn() {
                        return getColumnSet().getColumnByClass(NameColumn.class);
                    }

                    public VaultIdColumn getVaultIdColumn() {
                        return getColumnSet().getColumnByClass(VaultIdColumn.class);
                    }
                }
            }
        }

    }

}
