package com.velebit.anippe.client.reminders;

import com.velebit.anippe.client.common.menus.AbstractAddMenu;
import com.velebit.anippe.client.reminders.RemindersForm.MainBox.GroupBox;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.reminders.RemindersFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
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
                protected void execAction() {

                }
            }

            @Order(1000)
            public class RemindersTableField extends AbstractTableField<RemindersTableField.Table> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Reminders");
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
                }

                @ClassId("615472b4-e451-426b-b9e3-d7b56ae589bf")
                public class Table extends AbstractRemindersTable {

                    @Override
                    protected void reloadTableData() {
                        fetchReminders();
                    }
                }
            }
        }

    }

}
