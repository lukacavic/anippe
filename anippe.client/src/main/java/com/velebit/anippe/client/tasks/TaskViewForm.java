package com.velebit.anippe.client.tasks;

import com.velebit.anippe.client.common.fields.AbstractTextAreaField;
import com.velebit.anippe.client.common.menus.AbstractDeleteMenu;
import com.velebit.anippe.client.common.menus.AbstractEditMenu;
import com.velebit.anippe.client.tasks.TaskViewForm.MainBox.GroupBox;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.tasks.ITaskViewService;
import com.velebit.anippe.shared.tasks.TaskViewFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.CssClasses;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.LogicalGridLayoutConfig;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = TaskViewFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TaskViewForm extends AbstractForm {

    private Integer taskId;

    @FormData
    public Integer getTaskId() {
        return taskId;
    }

    @FormData
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Task");
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Tasks;
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("ViewTask");
    }

    public GroupBox.DetailsBox.CommentsBox.AddCommentButton getAddCommentButton() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.AddCommentButton.class);
    }

    public GroupBox.DetailsBox.CommentsBox.CommentField getCommentField() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.CommentField.class);
    }

    public GroupBox.DetailsBox.CommentsBox getCommentsBox() {
        return getFieldByClass(GroupBox.DetailsBox.CommentsBox.class);
    }

    public GroupBox.DetailsBox.DescriptionField getDescriptionField() {
        return getFieldByClass(GroupBox.DetailsBox.DescriptionField.class);
    }

    public GroupBox.DetailsBox getDetailsBox() {
        return getFieldByClass(GroupBox.DetailsBox.class);
    }

    public GroupBox.InformationsBox.DueDateLabelField getDueDateLabelField() {
        return getFieldByClass(GroupBox.InformationsBox.DueDateLabelField.class);
    }

    public GroupBox.InformationsBox.FollowersTableField getFollowersTableField() {
        return getFieldByClass(GroupBox.InformationsBox.FollowersTableField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public GroupBox.InformationsBox getInformationsBox() {
        return getFieldByClass(GroupBox.InformationsBox.class);
    }

    public GroupBox.InformationsBox.PriorityLabelField getPriorityLabelField() {
        return getFieldByClass(GroupBox.InformationsBox.PriorityLabelField.class);
    }

    public GroupBox.InformationsBox.RemindersTableField getRemindersTableField() {
        return getFieldByClass(GroupBox.InformationsBox.RemindersTableField.class);
    }

    public GroupBox.InformationsBox.StartDateLabelField getStartDateLabelField() {
        return getFieldByClass(GroupBox.InformationsBox.StartDateLabelField.class);
    }

    public GroupBox.InformationsBox.StatusLabelField getStatusLabelField() {
        return getFieldByClass(GroupBox.InformationsBox.StatusLabelField.class);
    }

    public GroupBox.DetailsBox.SubTasksBox getSubTasksBox() {
        return getFieldByClass(GroupBox.DetailsBox.SubTasksBox.class);
    }

    public GroupBox.DetailsBox.SubTasksBox.SubTasksTableField getSubTasksTableField() {
        return getFieldByClass(GroupBox.DetailsBox.SubTasksBox.SubTasksTableField.class);
    }

    @Override
    protected boolean getConfiguredClosable() {
        return true;
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Override
            protected int getConfiguredGridColumnCount() {
                return 3;
            }

            /*@Override
            protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
                return super.getConfiguredBodyLayoutConfig().withHGap(1);
            }*/

            @Override
            public boolean isBorderVisible() {
                return false;
            }

            @Override
            protected String getConfiguredCssClass() {
                return CssClasses.TOP_PADDING_INVISIBLE;
            }

            @Order(1000)
            public class DetailsBox extends AbstractGroupBox {
                @Order(0)
                public class MarkAsCompletedMenu extends AbstractMenu {

                    @Override
                    protected boolean getConfiguredToggleAction() {
                        return true;
                    }

                    @Override
                    protected int getConfiguredActionStyle() {
                        return ACTION_STYLE_BUTTON;
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Check;
                    }

                }

                @Order(500)
                public class StartTimerMenu extends AbstractMenu {
                    @Override
                    protected String getConfiguredText() {
                        return TEXTS.get("StartTimer");
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Clock;
                    }

                }


                @Override
                protected String getConfiguredMenuBarPosition() {
                    return MENU_BAR_POSITION_TOP;
                }

                @Override
                protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
                    return super.getConfiguredBodyLayoutConfig().withVGap(0);
                }

                @Override
                public boolean isStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected String getConfiguredSubLabel() {
                    return "Vezan za:";
                }

                @Override
                protected String getConfiguredLabel() {
                    return "eHitna";
                }

                @Override
                protected boolean getConfiguredLabelHtmlEnabled() {
                    return true;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 1;
                }

                @Order(1000)
                public class DescriptionField extends AbstractTextAreaField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.span("Description").style("font-weight:bold;font-size:13px;"),
                                HTML.span("  "),
                                HTML.appLink("editDescription", HTML.icon(FontIcons.Pencil).style("color:#234d74;"))
                        );

                        return content.toHtml();
                    }


                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        setValue("Queen, in a large caterpillar, that was said, and went on in the last time she had hoped) a fan and two or three of the Queen's absence, and were quite dry again, the cook till his eyes were getting.\n" +
                                "\n" +
                                "I like\"!' 'You might just as the Dormouse shall!' they both sat silent for a dunce? Go on!' 'I'm a poor man, your Majesty,' said the Dormouse indignantly. However, he consented to go and live in that poky little house, on the bank--the birds with draggled feathers, the animals with their hands and feet, to make ONE respectable person!' Soon her eye fell upon a time she went in without knocking,.\n");
                    }

                    @Override
                    protected boolean getConfiguredEnabled() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected int getConfiguredDisabledStyle() {
                        return DISABLED_STYLE_READ_ONLY;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 3;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected int getConfiguredMaxLength() {
                        return 128;
                    }
                }

                @Order(2000)
                public class SubTasksBox extends AbstractGroupBox {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("SubTasks");
                    }

                    @Override
                    protected String getConfiguredMenuBarPosition() {
                        return MENU_BAR_POSITION_TITLE;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Order(1000)
                    public class HideCompletedTasksMenu extends AbstractMenu {

                        @Override
                        protected byte getConfiguredHorizontalAlignment() {
                            return 1;
                        }

                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Filter;
                        }

                        @Override
                        protected boolean getConfiguredToggleAction() {
                            return true;
                        }

                        @Override
                        protected void execSelectionChanged(boolean selection) {
                            super.execSelectionChanged(selection);

                            setIconId(selection ? FontIcons.FilterRemove : FontIcons.Filter);
                        }


                        @Override
                        protected void execAction() {

                        }
                    }

                    @Order(1000)
                    public class SubTasksTableField extends AbstractTableField<SubTasksTableField.Table> {
                        @Override
                        public boolean isLabelVisible() {
                            return false;
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 4;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }

                        @ClassId("2d4f86d4-9e72-463e-8f57-72390387f171")
                        public class Table extends AbstractTable {

                            @Override
                            protected void execInitTable() {
                                super.execInitTable();

                                ITableRow row = addRow();
                                getCompletedColumn().setValue(row, true);
                                getTaskColumn().setValue(row, "I like\"!' 'You might just as she swam.");
                            }

                            public CompletedColumn getCompletedColumn() {
                                return getColumnSet().getColumnByClass(CompletedColumn.class);
                            }

                            public TaskColumn getTaskColumn() {
                                return getColumnSet().getColumnByClass(TaskColumn.class);
                            }

                            @Override
                            protected boolean getConfiguredHeaderVisible() {
                                return false;
                            }

                            @Override
                            public boolean isAutoResizeColumns() {
                                return true;
                            }

                            @Order(1000)
                            public class CompletedColumn extends AbstractBooleanColumn {
                                @Override
                                protected boolean getConfiguredEditable() {
                                    return true;
                                }

                                @Override
                                public boolean isFixedWidth() {
                                    return true;
                                }

                                @Override
                                public boolean isFixedPosition() {
                                    return true;
                                }

                                @Override
                                protected int getConfiguredWidth() {
                                    return 50;
                                }
                            }

                            @Order(2000)
                            public class TaskColumn extends AbstractStringColumn {
                                @Override
                                protected boolean getConfiguredEditable() {
                                    return true;
                                }

                                @Override
                                protected int getConfiguredWidth() {
                                    return 100;
                                }
                            }
                        }
                    }
                }

                @Order(3000)
                public class CommentsBox extends AbstractGroupBox {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Comments");
                    }

                    @Override
                    protected boolean getConfiguredExpanded() {
                        return false;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected boolean getConfiguredExpandable() {
                        return true;
                    }

                    @Override
                    protected int getConfiguredGridColumnCount() {
                        return 1;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Order(1000)
                    public class CommentField extends AbstractTextAreaField {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("AddComment");
                        }

                        @Override
                        protected byte getConfiguredLabelPosition() {
                            return LABEL_POSITION_ON_FIELD;
                        }

                        @Override
                        protected boolean getConfiguredStatusVisible() {
                            return false;
                        }
                    }

                    @Order(2000)
                    public class AddCommentButton extends AbstractButton {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("AddComment");
                        }

                        @Override
                        public boolean isProcessButton() {
                            return false;
                        }

                        @Override
                        protected Boolean getConfiguredDefaultButton() {
                            return true;
                        }

                        @Override
                        protected int getConfiguredHorizontalAlignment() {
                            return 1;
                        }

                        @Override
                        public boolean isStatusVisible() {
                            return false;
                        }

                        @Override
                        protected void execClickAction() {

                        }
                    }

                }

            }

            @Order(2000)
            public class InformationsBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return "Task Info";
                }

                @Override
                protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
                    return super.getConfiguredBodyLayoutConfig().withVGap(0);
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 1;
                }

                @Override
                protected String getConfiguredSubLabel() {
                    return "Created at 2023-10-20 05:13:22";
                }

                @Override
                public boolean isStatusVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return 1;
                }

                @Override
                protected String getConfiguredMenuBarPosition() {
                    return MENU_BAR_POSITION_TITLE;
                }

                @Order(1000)
                public class ActionsMenu extends AbstractMenu {
                    @Override
                    protected byte getConfiguredHorizontalAlignment() {
                        return 1;
                    }

                    @Override
                    protected String getConfiguredIconId() {
                        return FontIcons.Menu;
                    }

                    @Order(1000)
                    public class EditMenu extends AbstractEditMenu {


                        @Override
                        protected void execAction() {

                        }
                    }

                    @Order(2000)
                    public class CopyMenu extends AbstractEditMenu {
                        @Override
                        protected String getConfiguredText() {
                            return TEXTS.get("Clone");
                        }

                        @Override
                        protected String getConfiguredIconId() {
                            return FontIcons.Clone;
                        }

                        @Override
                        protected void execAction() {

                        }
                    }

                    @Order(3000)
                    public class DeleteMenu extends AbstractDeleteMenu {


                        @Override
                        protected void execAction() {

                        }
                    }
                }

                @Order(0)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class StatusLabelField extends org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.span(HTML.icon(FontIcons.Star), HTML.appLink("status", HTML.span(" Status:").style("border-bottom:1px dashed #333;"))).style("font-size:13px;")
                        );

                        return content.toHtml();
                    }

                    @Override
                    protected void execAppLinkAction(String ref) {
                        super.execAppLinkAction(ref);
                    }

                    @Override
                    public int getLabelWidthInPixel() {
                        return 100;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        IHtmlContent content = HTML.fragment(
                                HTML.span("In progress")
                        );

                        setValue(content.toHtml());
                    }

                    @Override
                    protected boolean getConfiguredHtmlEnabled() {
                        return true;
                    }
                }

                @Order(1000)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class PriorityLabelField extends org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.span(HTML.icon(FontIcons.Star), HTML.appLink("priority", HTML.span(" Priority:").style("border-bottom:1px dashed #333;"))).style("font-size:13px;")
                        );

                        return content.toHtml();
                    }

                    @Override
                    public int getLabelWidthInPixel() {
                        return 100;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        IHtmlContent content = HTML.fragment(
                                HTML.span("Urgent").style("color:red;")
                        );

                        setValue(content.toHtml());
                    }

                    @Override
                    protected boolean getConfiguredHtmlEnabled() {
                        return true;
                    }


                }

                @Order(1500)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class StartDateLabelField extends org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.icon(FontIcons.Clock), HTML.span(" Start Date:")
                        );

                        return content.toHtml();
                    }

                    @Override
                    public int getLabelWidthInPixel() {
                        return 100;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        IHtmlContent content = HTML.fragment(
                                HTML.span("01.11.2023")
                        );

                        setValue(content.toHtml());
                    }

                    @Override
                    protected boolean getConfiguredHtmlEnabled() {
                        return true;
                    }
                }
                @Order(2000)
                @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
                public class DueDateLabelField extends org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.icon(FontIcons.Calendar), HTML.span(" Due date:")
                        );

                        return content.toHtml();
                    }

                    @Override
                    public int getLabelWidthInPixel() {
                        return 100;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected void execInitField() {
                        super.execInitField();

                        IHtmlContent content = HTML.fragment(
                                HTML.span("21.11.2023")
                        );

                        setValue(content.toHtml());
                    }

                    @Override
                    protected boolean getConfiguredHtmlEnabled() {
                        return true;
                    }
                }

                @Order(3000)
                public class RemindersTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<RemindersTableField.Table> {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.icon(FontIcons.Clock),
                                HTML.span(" "),
                                HTML.span("Reminders").style("font-weight:bold;font-size:13px;"),
                                HTML.span("  "),
                                HTML.appLink("addReminder", HTML.span("(Add Reminder)").style("color:#234d74;"))
                        );

                        return content.toHtml();
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 3;
                    }

                    @ClassId("41c152c7-66ec-498f-9ab1-7eaafc01441c")
                    public class Table extends AbstractTable {
                        @Override
                        protected boolean getConfiguredHeaderVisible() {
                            return false;
                        }
                    }
                }

                @Order(4000)
                public class FollowersTableField extends org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField<FollowersTableField.Table> {
                    @Override
                    protected String getConfiguredLabel() {
                        IHtmlContent content = HTML.fragment(
                                HTML.icon(FontIcons.Users1),
                                HTML.span(" "),
                                HTML.span("Followers").style("font-weight:bold;font-size:13px;"),
                                HTML.span("  "),
                                HTML.appLink("addReminder", HTML.span("(Add Follower)").style("color:#234d74;"))
                        );

                        return content.toHtml();
                    }

                    @Override
                    protected boolean getConfiguredLabelHtmlEnabled() {
                        return true;
                    }

                    @Override
                    protected boolean getConfiguredStatusVisible() {
                        return false;
                    }

                    @Override
                    protected byte getConfiguredLabelPosition() {
                        return LABEL_POSITION_TOP;
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 3;
                    }

                    @ClassId("41c152c7-66ec-498f-9ab1-7eaafc01441c")
                    public class Table extends AbstractTable {
                        @Override
                        protected boolean getConfiguredHeaderVisible() {
                            return false;
                        }
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
            TaskViewFormData formData = new TaskViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskViewService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TaskViewFormData formData = new TaskViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskViewService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TaskViewFormData formData = new TaskViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskViewService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TaskViewFormData formData = new TaskViewFormData();
            exportFormData(formData);
            formData = BEANS.get(ITaskViewService.class).store(formData);
            importFormData(formData);
        }
    }
}
