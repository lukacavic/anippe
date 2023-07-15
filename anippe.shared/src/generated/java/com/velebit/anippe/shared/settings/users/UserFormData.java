package com.velebit.anippe.shared.settings.users;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;
import java.util.Set;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.settings.users.UserForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class UserFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public Active getActive() {
        return getFieldByClass(Active.class);
    }

    public Administrator getAdministrator() {
        return getFieldByClass(Administrator.class);
    }

    public Email getEmail() {
        return getFieldByClass(Email.class);
    }

    public FirstName getFirstName() {
        return getFieldByClass(FirstName.class);
    }

    public LastName getLastName() {
        return getFieldByClass(LastName.class);
    }

    public RolesBox getRolesBox() {
        return getFieldByClass(RolesBox.class);
    }

    /**
     * access method for property UserId.
     */
    public Integer getUserId() {
        return getUserIdProperty().getValue();
    }

    /**
     * access method for property UserId.
     */
    public void setUserId(Integer userId) {
        getUserIdProperty().setValue(userId);
    }

    public UserIdProperty getUserIdProperty() {
        return getPropertyByClass(UserIdProperty.class);
    }

    public Username getUsername() {
        return getFieldByClass(Username.class);
    }

    public static class Active extends AbstractValueFieldData<Boolean> {
        private static final long serialVersionUID = 1L;
    }

    public static class Administrator extends AbstractValueFieldData<Boolean> {
        private static final long serialVersionUID = 1L;
    }

    public static class Email extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class FirstName extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class LastName extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class RolesBox extends AbstractValueFieldData<Set<Long>> {
        private static final long serialVersionUID = 1L;
    }

    public static class UserIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Username extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }
}
