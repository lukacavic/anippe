package com.velebit.anippe.shared.tickets;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.tickets.TicketForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class TicketFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public AssignedTo getAssignedTo() {
        return getFieldByClass(AssignedTo.class);
    }

    public Contact getContact() {
        return getFieldByClass(Contact.class);
    }

    public Department getDepartment() {
        return getFieldByClass(Department.class);
    }

    public Priority getPriority() {
        return getFieldByClass(Priority.class);
    }

    public Project getProject() {
        return getFieldByClass(Project.class);
    }

    public Reply getReply() {
        return getFieldByClass(Reply.class);
    }

    /**
     * access method for property TicketId.
     */
    public Integer getTicketId() {
        return getTicketIdProperty().getValue();
    }

    /**
     * access method for property TicketId.
     */
    public void setTicketId(Integer ticketId) {
        getTicketIdProperty().setValue(ticketId);
    }

    public TicketIdProperty getTicketIdProperty() {
        return getPropertyByClass(TicketIdProperty.class);
    }

    public Title getTitle() {
        return getFieldByClass(Title.class);
    }

    public static class AssignedTo extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class Contact extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class Department extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class Priority extends AbstractValueFieldData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Project extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class Reply extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class TicketIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Title extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }
}
