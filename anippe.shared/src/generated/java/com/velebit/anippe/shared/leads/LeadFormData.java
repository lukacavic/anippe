package com.velebit.anippe.shared.leads;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;
import java.util.Date;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.leads.LeadForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class LeadFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public Address getAddress() {
        return getFieldByClass(Address.class);
    }

    public AssignedUser getAssignedUser() {
        return getFieldByClass(AssignedUser.class);
    }

    public City getCity() {
        return getFieldByClass(City.class);
    }

    /**
     * access method for property ClientId.
     */
    public Integer getClientId() {
        return getClientIdProperty().getValue();
    }

    /**
     * access method for property ClientId.
     */
    public void setClientId(Integer clientId) {
        getClientIdProperty().setValue(clientId);
    }

    public ClientIdProperty getClientIdProperty() {
        return getPropertyByClass(ClientIdProperty.class);
    }

    public Company getCompany() {
        return getFieldByClass(Company.class);
    }

    public Country getCountry() {
        return getFieldByClass(Country.class);
    }

    public Description getDescription() {
        return getFieldByClass(Description.class);
    }

    public Email getEmail() {
        return getFieldByClass(Email.class);
    }

    public LastContactAt getLastContactAt() {
        return getFieldByClass(LastContactAt.class);
    }

    /**
     * access method for property LeadId.
     */
    public Integer getLeadId() {
        return getLeadIdProperty().getValue();
    }

    /**
     * access method for property LeadId.
     */
    public void setLeadId(Integer leadId) {
        getLeadIdProperty().setValue(leadId);
    }

    public LeadIdProperty getLeadIdProperty() {
        return getPropertyByClass(LeadIdProperty.class);
    }

    /**
     * access method for property Lost.
     */
    public boolean isLost() {
        return getLostProperty().getValue() == null ? false : getLostProperty().getValue();
    }

    /**
     * access method for property Lost.
     */
    public void setLost(boolean lost) {
        getLostProperty().setValue(lost);
    }

    public LostProperty getLostProperty() {
        return getPropertyByClass(LostProperty.class);
    }

    public Name getName() {
        return getFieldByClass(Name.class);
    }

    public Phone getPhone() {
        return getFieldByClass(Phone.class);
    }

    public Position getPosition() {
        return getFieldByClass(Position.class);
    }

    public PostalCode getPostalCode() {
        return getFieldByClass(PostalCode.class);
    }

    public Project getProject() {
        return getFieldByClass(Project.class);
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

    public Source getSource() {
        return getFieldByClass(Source.class);
    }

    public Status getStatus() {
        return getFieldByClass(Status.class);
    }

    public Website getWebsite() {
        return getFieldByClass(Website.class);
    }

    public static class Address extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class AssignedUser extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class City extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class ClientIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Company extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class Country extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class Description extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class Email extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class LastContactAt extends AbstractValueFieldData<Date> {
        private static final long serialVersionUID = 1L;
    }

    public static class LeadIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class LostProperty extends AbstractPropertyData<Boolean> {
        private static final long serialVersionUID = 1L;
    }

    public static class Name extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class Phone extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class Position extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class PostalCode extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class Project extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class ProjectIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Source extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class Status extends AbstractValueFieldData<Long> {
        private static final long serialVersionUID = 1L;
    }

    public static class Website extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }
}
