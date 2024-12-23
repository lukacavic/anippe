package com.velebit.anippe.shared.projects.settings.leads;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.projects.settings.leads.LeadStatusForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class LeadStatusFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public Color getColor() {
        return getFieldByClass(Color.class);
    }

    /**
     * access method for property LeadStatusId.
     */
    public Integer getLeadStatusId() {
        return getLeadStatusIdProperty().getValue();
    }

    /**
     * access method for property LeadStatusId.
     */
    public void setLeadStatusId(Integer leadStatusId) {
        getLeadStatusIdProperty().setValue(leadStatusId);
    }

    public LeadStatusIdProperty getLeadStatusIdProperty() {
        return getPropertyByClass(LeadStatusIdProperty.class);
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

    public Sort getSort() {
        return getFieldByClass(Sort.class);
    }

    public static class Color extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class LeadStatusIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Name extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class ProjectIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Sort extends AbstractValueFieldData<Integer> {
        private static final long serialVersionUID = 1L;
    }
}
