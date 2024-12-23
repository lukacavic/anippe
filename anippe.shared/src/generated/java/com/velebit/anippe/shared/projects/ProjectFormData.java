package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;
import java.util.Date;
import java.util.Set;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.projects.ProjectForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class ProjectFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public Client getClient() {
        return getFieldByClass(Client.class);
    }

    public ClientsListBox getClientsListBox() {
        return getFieldByClass(ClientsListBox.class);
    }

    public Deadline getDeadline() {
        return getFieldByClass(Deadline.class);
    }

    public Description getDescription() {
        return getFieldByClass(Description.class);
    }

    public MembersListBox getMembersListBox() {
        return getFieldByClass(MembersListBox.class);
    }

    public Name getName() {
        return getFieldByClass(Name.class);
    }

    /**
     * access method for property ProjectId.
     */
    public Integer getProjectId() {
        return getProjectIdProperty().getValue();
    }

    /**
     * access method for property ProjectId.
     */
    public void setProjectId(Integer projectId) {
        getProjectIdProperty().setValue(projectId);
    }

    public ProjectIdProperty getProjectIdProperty() {
        return getPropertyByClass(ProjectIdProperty.class);
    }

    public StartDate getStartDate() {
        return getFieldByClass(StartDate.class);
    }

    public Status getStatus() {
        return getFieldByClass(Status.class);
    }

    public Type getType() {
        return getFieldByClass(Type.class);
    }

    public static class Client extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class ClientsListBox extends AbstractValueFieldData<Set<Long>> {
        private static final long serialVersionUID = 1L;
    }

    public static class Deadline extends AbstractValueFieldData<Date> {
        private static final long serialVersionUID = 1L;
    }

    public static class Description extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class MembersListBox extends AbstractValueFieldData<Set<Long>> {
        private static final long serialVersionUID = 1L;
    }

    public static class Name extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class ProjectIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class StartDate extends AbstractValueFieldData<Date> {
        private static final long serialVersionUID = 1L;
    }

    public static class Status extends AbstractValueFieldData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Type extends AbstractValueFieldData<Integer> {
        private static final long serialVersionUID = 1L;
    }
}
