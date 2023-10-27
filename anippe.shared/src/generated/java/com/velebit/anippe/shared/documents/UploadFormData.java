package com.velebit.anippe.shared.documents;

import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import javax.annotation.Generated;
import java.util.Date;
import java.util.List;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "com.velebit.anippe.client.documents.UploadForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class UploadFormData extends AbstractFormData {
    private static final long serialVersionUID = 1L;

    public CreatedAt getCreatedAt() {
        return getFieldByClass(CreatedAt.class);
    }

    public Description getDescription() {
        return getFieldByClass(Description.class);
    }

    public Document getDocument() {
        return getFieldByClass(Document.class);
    }

    /**
     * access method for property DocumentIds.
     */
    public List<Integer> getDocumentIds() {
        return getDocumentIdsProperty().getValue();
    }

    /**
     * access method for property DocumentIds.
     */
    public void setDocumentIds(List<Integer> documentIds) {
        getDocumentIdsProperty().setValue(documentIds);
    }

    public DocumentIdsProperty getDocumentIdsProperty() {
        return getPropertyByClass(DocumentIdsProperty.class);
    }

    public Name getName() {
        return getFieldByClass(Name.class);
    }

    /**
     * access method for property RelatedId.
     */
    public Integer getRelatedId() {
        return getRelatedIdProperty().getValue();
    }

    /**
     * access method for property RelatedId.
     */
    public void setRelatedId(Integer relatedId) {
        getRelatedIdProperty().setValue(relatedId);
    }

    public RelatedIdProperty getRelatedIdProperty() {
        return getPropertyByClass(RelatedIdProperty.class);
    }

    /**
     * access method for property RelatedType.
     */
    public Integer getRelatedType() {
        return getRelatedTypeProperty().getValue();
    }

    /**
     * access method for property RelatedType.
     */
    public void setRelatedType(Integer relatedType) {
        getRelatedTypeProperty().setValue(relatedType);
    }

    public RelatedTypeProperty getRelatedTypeProperty() {
        return getPropertyByClass(RelatedTypeProperty.class);
    }

    public UploadedFilesTable getUploadedFilesTable() {
        return getFieldByClass(UploadedFilesTable.class);
    }

    public User getUser() {
        return getFieldByClass(User.class);
    }

    public static class CreatedAt extends AbstractValueFieldData<Date> {
        private static final long serialVersionUID = 1L;
    }

    public static class Description extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class Document extends AbstractValueFieldData<BinaryResource> {
        private static final long serialVersionUID = 1L;
    }

    public static class DocumentIdsProperty extends AbstractPropertyData<List<Integer>> {
        private static final long serialVersionUID = 1L;
    }

    public static class Name extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }

    public static class RelatedIdProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class RelatedTypeProperty extends AbstractPropertyData<Integer> {
        private static final long serialVersionUID = 1L;
    }

    public static class UploadedFilesTable extends AbstractTableFieldBeanData {
        private static final long serialVersionUID = 1L;

        @Override
        public UploadedFilesTableRowData addRow() {
            return (UploadedFilesTableRowData) super.addRow();
        }

        @Override
        public UploadedFilesTableRowData addRow(int rowState) {
            return (UploadedFilesTableRowData) super.addRow(rowState);
        }

        @Override
        public UploadedFilesTableRowData createRow() {
            return new UploadedFilesTableRowData();
        }

        @Override
        public Class<? extends AbstractTableRowData> getRowType() {
            return UploadedFilesTableRowData.class;
        }

        @Override
        public UploadedFilesTableRowData[] getRows() {
            return (UploadedFilesTableRowData[]) super.getRows();
        }

        @Override
        public UploadedFilesTableRowData rowAt(int index) {
            return (UploadedFilesTableRowData) super.rowAt(index);
        }

        public void setRows(UploadedFilesTableRowData[] rows) {
            super.setRows(rows);
        }

        public static class UploadedFilesTableRowData extends AbstractTableRowData {
            private static final long serialVersionUID = 1L;
            public static final String documentId = "documentId";
            public static final String file = "file";
            public static final String name = "name";
            public static final String description = "description";
            public static final String fileType = "fileType";
            public static final String fileSize = "fileSize";
            private Integer m_documentId;
            private BinaryResource m_file;
            private String m_name;
            private String m_description;
            private String m_fileType;
            private String m_fileSize;

            public Integer getDocumentId() {
                return m_documentId;
            }

            public void setDocumentId(Integer newDocumentId) {
                m_documentId = newDocumentId;
            }

            public BinaryResource getFile() {
                return m_file;
            }

            public void setFile(BinaryResource newFile) {
                m_file = newFile;
            }

            public String getName() {
                return m_name;
            }

            public void setName(String newName) {
                m_name = newName;
            }

            public String getDescription() {
                return m_description;
            }

            public void setDescription(String newDescription) {
                m_description = newDescription;
            }

            public String getFileType() {
                return m_fileType;
            }

            public void setFileType(String newFileType) {
                m_fileType = newFileType;
            }

            public String getFileSize() {
                return m_fileSize;
            }

            public void setFileSize(String newFileSize) {
                m_fileSize = newFileSize;
            }
        }
    }

    public static class User extends AbstractValueFieldData<String> {
        private static final long serialVersionUID = 1L;
    }
}
