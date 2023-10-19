package com.velebit.anippe.client.reminders;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.reminders.RemindersForm.MainBox.GroupBox;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.reminders.IRemindersService;
import com.velebit.anippe.shared.reminders.RemindersFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = RemindersFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class RemindersForm extends AbstractForm {

    private Integer relatedId;
    private Integer relatedType;

    @FormData
    public Integer getRelatedId() {
        return relatedId;
    }

    @FormData
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    @FormData
    public Integer getRelatedType() {
        return relatedType;
    }

    @FormData
    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Reminders");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Clock;
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public void fetchReminders() {

    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.RemindersTableField getRemindersTableField() {
        return getFieldByClass(GroupBox.RemindersTableField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Order(1000)
            public class AddMenu extends AbstractAddMenu {
                @Override
                protected String getConfiguredText() {
                    return TEXTS.get("AddReminder");
                }

                @Override
                protected void execAction() {
                    ReminderForm form = new ReminderForm();
                    form.setRelatedId(getRelatedId());
                    form.setRelatedType(getRelatedType());
                    form.startNew();
                    form.waitFor();
                    if (form.isFormStored()) {
                        fetchReminders();
                    }
                }
            }

            @Order(1000)
            public class RemindersTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<RemindersTableField.Table> {
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

                @ClassId("f462d63e-dddf-4bc6-84d8-751da68a3ba3")
                public class Table extends AbstractTable {
                    @Override
                    protected boolean getConfiguredAutoResizeColumns() {
                        return true;
                    }

                    public ReminderAtColumn getReminderAtColumn() {
                        return getColumnSet().getColumnByClass(ReminderAtColumn.class);
                    }

                    public DescriptionColumn getDescriptionColumn() {
                        return getColumnSet().getColumnByClass(DescriptionColumn.class);
                    }

                    public IsRemindedColumn getIsRemindedColumn() {
                        return getColumnSet().getColumnByClass(IsRemindedColumn.class);
                    }

                    @Order(1000)
                    public class DescriptionColumn extends AbstractStringColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("Description");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(2000)
                    public class ReminderAtColumn extends AbstractDateTimeColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("ReminderAt");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 100;
                        }
                    }

                    @Order(3000)
                    public class IsRemindedColumn extends AbstractBooleanColumn {
                        @Override
                        protected String getConfiguredHeaderText() {
                            return TEXTS.get("IsReminded");
                        }

                        @Override
                        protected int getConfiguredWidth() {
                            return 120;
                        }
                    }
                }
            }
        }

    }

}
