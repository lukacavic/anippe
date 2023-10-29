package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.gantt.AbstractGanttField;
import com.velebit.anippe.client.gantt.GanttItem;
import com.velebit.anippe.client.projects.GanttForm.MainBox.GroupBox;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.GanttFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.mode.AbstractMode;
import org.eclipse.scout.rt.client.ui.form.fields.modeselector.AbstractModeSelectorField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
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

    public GroupBox.ToolbarBox.GroupByField getGroupByField() {
        return getFieldByClass(GroupBox.ToolbarBox.GroupByField.class);
    }

    public GroupBox.ToolbarBox.PlaceholderField getPlaceholderField() {
        return getFieldByClass(GroupBox.ToolbarBox.PlaceholderField.class);
    }

    public GroupBox.ToolbarBox.TaskStatusField getTaskStatusField() {
        return getFieldByClass(GroupBox.ToolbarBox.TaskStatusField.class);
    }

    public GroupBox.ToolbarBox getToolbarBox() {
        return getFieldByClass(GroupBox.ToolbarBox.class);
    }

    public GroupBox.ToolbarBox.ViewModeField getViewModeField() {
        return getFieldByClass(GroupBox.ToolbarBox.ViewModeField.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            @Override
            protected int getConfiguredGridColumnCount() {
                return 1;
            }

            @Order(900)
            public class ToolbarBox extends org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                public boolean isBorderVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 6;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Order(0)
                public class PlaceholderField extends AbstractPlaceholderField {
                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected String getConfiguredFieldStyle() {
                        return FIELD_STYLE_CLASSIC;
                    }

                }

                @Order(100)
                public class TaskStatusField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Status");
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    protected String getConfiguredFieldStyle() {
                        return FIELD_STYLE_CLASSIC;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }
                }

                @Order(200)
                public class GroupByField extends AbstractSmartField<Long> {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("GroupBy");
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 1;
                    }

                    @Override
                    protected String getConfiguredFieldStyle() {
                        return FIELD_STYLE_CLASSIC;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_ON_FIELD;
                    }
                }

                @Order(300)
                public class ViewModeField extends AbstractModeSelectorField<String> {
                    @Override
                    public boolean isLabelVisible() {
                        return false;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        setValue("Week");
                    }

                    @Override
                    protected void execChangedValue() {
                        super.execChangedValue();

                        getGanttField().setViewMode(getValue());
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredGridW() {
                        return 2;
                    }

                    @Override
                    protected String getConfiguredFieldStyle() {
                        return FIELD_STYLE_CLASSIC;
                    }

                    @Order(1000)
                    @ClassId("4403da94-739a-43d9-aa28-eb6a9a96bde1")
                    public class DaysMode extends AbstractMode<String> {
                        @Override
                        protected String getConfiguredText() {
                            return "Days";
                        }

                        @Override
                        protected String getConfiguredRef() {
                            return "Day";
                        }

                    }

                    @Order(1500)
                    @ClassId("c7aeb5a6-b430-47cf-8f52-7ad08027741c")
                    public class WeeksMode extends AbstractMode<String> {
                        @Override
                        protected String getConfiguredText() {
                            return "Weeks";
                        }

                        @Override
                        protected void execInitAction() {
                            super.execInitAction();

                            setSelected(true);
                        }

                        @Override
                        protected String getConfiguredRef() {
                            return "Week";
                        }
                    }

                    @Order(2000)
                    @ClassId("c7aeb5a6-b430-47cf-8f52-7ad08027741c")
                    public class MonthsMode extends AbstractMode<String> {
                        @Override
                        protected String getConfiguredText() {
                            return "Months";
                        }

                        @Override
                        protected String getConfiguredRef() {
                            return "Month";
                        }


                    }
                }
            }


            @Order(1000)
            public class GanttField extends AbstractGanttField {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }
                @Override
                protected void execInitField() {
                    super.execInitField();

                    setViewMode("Week");
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
