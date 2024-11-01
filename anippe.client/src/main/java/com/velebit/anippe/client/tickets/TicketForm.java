package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.common.fields.texteditor.AbstractTextEditorField;
import com.velebit.anippe.client.lookups.PriorityLookupCall;
import com.velebit.anippe.client.tickets.TicketForm.MainBox.CancelButton;
import com.velebit.anippe.client.tickets.TicketForm.MainBox.GroupBox;
import com.velebit.anippe.client.tickets.TicketForm.MainBox.OkButton;
import com.velebit.anippe.shared.contacts.ContactLookupCall;
import com.velebit.anippe.shared.icons.FontIcons;
import com.velebit.anippe.shared.projects.ProjectLookupCall;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tickets.ITicketService;
import com.velebit.anippe.shared.tickets.TicketDepartmentLookupCall;
import com.velebit.anippe.shared.tickets.TicketFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = TicketFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TicketForm extends AbstractForm {

    private Integer ticketId;

    @FormData
    public Integer getTicketId() {
        return ticketId;
    }

    @FormData
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Ticket");
    }

    public GroupBox.AssignedToField getAssignedToField() {
        return getFieldByClass(GroupBox.AssignedToField.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    @Override
    protected String getConfiguredIconId() {
        return FontIcons.Info;
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public GroupBox.ContactField getContactField() {
        return getFieldByClass(GroupBox.ContactField.class);
    }

    public GroupBox.DepartmentField getDepartmentField() {
        return getFieldByClass(GroupBox.DepartmentField.class);
    }

    public GroupBox.PriorityField getPriorityField() {
        return getFieldByClass(GroupBox.PriorityField.class);
    }

    public GroupBox.ProjectField getProjectField() {
        return getFieldByClass(GroupBox.ProjectField.class);
    }

    public GroupBox.ReplyBox getReplyBox() {
        return getFieldByClass(GroupBox.ReplyBox.class);
    }

    public GroupBox.TitleField getTitleField() {
        return getFieldByClass(GroupBox.TitleField.class);
    }

    @Override
    protected String getConfiguredSubTitle() {
        return TEXTS.get("NewEntry");
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class TitleField extends AbstractStringField {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Title");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected int getConfiguredMaxLength() {
                    return 128;
                }
            }

            @Order(2000)
            public class ContactField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Contact");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return ContactLookupCall.class;
                }
            }

            @Order(2500)
            public class ProjectField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Project");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return ProjectLookupCall.class;
                }
            }

            @Order(3000)
            public class DepartmentField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Department");
                }

                @Override
                protected Class<? extends IValueField> getConfiguredMasterField() {
                    return ProjectField.class;
                }

                @Override
                protected boolean getConfiguredMasterRequired() {
                    return true;
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    if (getProjectField().getValue() != null) {
                        TicketDepartmentLookupCall c = (TicketDepartmentLookupCall) call;
                        c.setProjectId(getProjectField().getValue().intValue());
                    }

                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return TicketDepartmentLookupCall.class;
                }
            }

            @Order(4000)
            public class PriorityField extends AbstractSmartField<Integer> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Priority");
                }

                @Override
                protected boolean getConfiguredMandatory() {
                    return true;
                }

                @Override
                protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
                    return PriorityLookupCall.class;
                }
            }

            @Order(5000)
            public class AssignedToField extends AbstractSmartField<Long> {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("AssignedUser");
                }

                @Override
                protected Class<? extends IValueField> getConfiguredMasterField() {
                    return ProjectField.class;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    if (getProjectField().getValue() != null) {
                        UserLookupCall c = (UserLookupCall) call;
                        c.setProjectId(getProjectField().getValue().intValue());
                    }

                }

                @Override
                protected boolean getConfiguredMasterRequired() {
                    return true;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }
            }

            @Order(6000)
            public class ReplyBox extends AbstractGroupBox {
                @Override
                protected String getConfiguredLabel() {
                    return TEXTS.get("Body");
                }

                @Override
                protected int getConfiguredGridW() {
                    return 2;
                }

                @Override
                protected int getConfiguredGridColumnCount() {
                    return 2;
                }

                @Order(1000)
                public class ReplyField extends AbstractTextEditorField {
                    @Override
                    protected String getConfiguredLabel() {
                        return TEXTS.get("Reply");
                    }

                    @Override
                    protected int getConfiguredGridH() {
                        return 3;
                    }
                }

            }

        }

        @Order(2000)
        public class OkButton extends AbstractOkButton {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("OpenTicket");
            }
        }

        @Order(3000)
        public class CancelButton extends AbstractCancelButton {

        }
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TicketFormData formData = new TicketFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TicketFormData formData = new TicketFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            TicketFormData formData = new TicketFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            TicketFormData formData = new TicketFormData();
            exportFormData(formData);
            formData = BEANS.get(ITicketService.class).store(formData);
            importFormData(formData);
        }
    }
}
