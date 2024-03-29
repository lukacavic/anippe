package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.projects.PredefinedReplyForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class PredefinedReplyFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public Content getContent() {
        return getFieldByClass(Content.class);
    }

    /**
     * access method for property PredefinedReplyId.
     */
    public Integer getPredefinedReplyId() {
        return getPredefinedReplyIdProperty().getValue();
    }

    /**
     * access method for property PredefinedReplyId.
     */
    public void setPredefinedReplyId(Integer predefinedReplyId) {
        getPredefinedReplyIdProperty().setValue(predefinedReplyId);
    }

    public PredefinedReplyIdProperty getPredefinedReplyIdProperty() {
        return getPropertyByClass(PredefinedReplyIdProperty.class);
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

    public Title getTitle() {
        return getFieldByClass(Title.class);
    }

    public static class Content extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class PredefinedReplyIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class ProjectIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class Title extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }
}
