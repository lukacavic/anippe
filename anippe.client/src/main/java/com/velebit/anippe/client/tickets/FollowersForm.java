package com.velebit.anippe.client.tickets;

import com.velebit.anippe.client.tickets.FollowersForm.MainBox.GroupBox;
import com.velebit.anippe.shared.settings.users.UserLookupCall;
import com.velebit.anippe.shared.tickets.FollowersFormData;
import com.velebit.anippe.shared.tickets.IFollowersService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Set;

@FormData(value = FollowersFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class FollowersForm extends AbstractForm {

    private Integer ticketId;
    private Integer projectId;

    @FormData
    public Integer getTicketId() {
        return ticketId;
    }

    @FormData
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
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
        return TEXTS.get("Followers");
    }

    public GroupBox.FollowersBox getFollowersBox() {
        return getFieldByClass(GroupBox.FollowersBox.class);
    }

    @Override
    protected void execInitForm() {
        super.execInitForm();

        Set<Long> followers = BEANS.get(IFollowersService.class).fetchFollowers(getTicketId());
        getFollowersBox().setValue(followers);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredWidthInPixel() {
            return 250;
        }

        @Order(1000)
        public class GroupBox extends AbstractGroupBox {
            @Order(1000)
            public class FollowersBox extends AbstractListBox<Long> {
                @Override
                public boolean isLabelVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                }

                @Override
                protected void execPrepareLookup(ILookupCall<Long> call) {
                    super.execPrepareLookup(call);

                    if (getProjectId() != null) {
                        UserLookupCall c = (UserLookupCall) call;
                        c.setProjectId(getProjectId());
                    }

                }

                @Override
                protected void execChangedValue() {
                    super.execChangedValue();

                    FollowersFormData formData = new FollowersFormData();
                    exportFormData(formData);
                    BEANS.get(IFollowersService.class).create(formData);

                    IForm parentForm = FollowersForm.this.getParentOfType(TicketForm.class);
                    if (parentForm != null) {
                        TicketForm form = (TicketForm) parentForm;
                        form.renderFollowingIcon();
                    }
                }

                @Override
                protected int getConfiguredGridH() {
                    return 6;
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
            FollowersFormData formData = new FollowersFormData();
            exportFormData(formData);
            formData = BEANS.get(IFollowersService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            FollowersFormData formData = new FollowersFormData();
            exportFormData(formData);
            formData = BEANS.get(IFollowersService.class).create(formData);
            importFormData(formData);
        }
    }

}
