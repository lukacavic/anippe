package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.gantt.AbstractGanttField;
import com.velebit.anippe.client.gantt.GanttItem;
import com.velebit.anippe.client.projects.GanttForm.MainBox.GroupBox;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.GanttFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;

import java.util.Collection;
import java.util.Date;

@FormData(value = GanttFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class GanttForm extends AbstractForm {

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
    protected String getConfiguredTitle() {
        return TEXTS.get("Gantt");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Chart;
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.GanttField getGanttField() {
        return getFieldByClass(GroupBox.GanttField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class GanttField extends AbstractGanttField {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected void execInitField() {
                    super.execInitField();

                    Collection<GanttItem> items = CollectionUtility.emptyArrayList();

                    for (int i = 1; i < 20; i++) {
                        GanttTask task = new GanttTask();
                        task.setId(i + "");
                        task.setTitle("Moj zadatak" + i);
                        task.setStartDate(new Date());
                        task.setEndDate(DateUtility.addDays(new Date(), i));
                        task.setProgress(39);

                        items.add(task);
                    }


                    setItems(items);

                }

                @Override
                protected int getConfiguredGridH() {
                    return 10;
                }
            }

        }

    }

}
