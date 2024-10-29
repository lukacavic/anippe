package com.velebit.anippe.server.documents;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.config.AppFileRootDirectoryOrganisationsConfigProperty;
import com.velebit.anippe.server.utilities.UploadUtility;
import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.attachments.UploadedFile;
import com.velebit.anippe.shared.beans.Document;
import com.velebit.anippe.shared.documents.DocumentsFormData.DocumentsTable.DocumentsTableRowData;
import com.velebit.anippe.shared.documents.IDocumentsService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.resource.MimeType;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ChangeStatus;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DocumentsService extends AbstractService implements IDocumentsService {

    @Override
    public List<DocumentsTableRowData> fetchDocuments(Integer relatedId, Integer relatedType) {
        BeanArrayHolder<DocumentsTableRowData> rowData = new BeanArrayHolder<>(DocumentsTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT d.id, ");
        varname1.append("       d.NAME, ");
        varname1.append("       d.file_size, ");
        varname1.append("       d.file_extension, ");
        varname1.append("       u.last_name ");
        varname1.append("       || ' ' ");
        varname1.append("       || u.first_name, ");
        varname1.append("       d.created_at, ");
        varname1.append("       d.updated_at ");
        varname1.append("FROM   documents d, ");
        varname1.append("       users u ");
        varname1.append("WHERE  d.user_id = u.id ");
        varname1.append("       AND d.organisation_id = :organisationId ");
        varname1.append("       AND d.deleted_at IS NULL ");
        varname1.append("       AND d.full_path IS NOT NULL ");
        varname1.append(" AND d.related_id = :relatedId ");
        varname1.append(" AND d.related_type = :relatedType ");
        varname1.append(" AND d.related_id IS NOT NULL ");
        varname1.append(" AND d.related_type IS NOT NULL ");
        varname1.append("INTO ");
        varname1.append(":{rows.DocumentId},:{rows.Name}, :{rows.Size}, :{rows.Type}, :{rows.User}, :{rows.CreatedAt}, :{rows.UpdatedAt}");
        SQL.selectInto(
                varname1.toString(),
                new NVPair("relatedId", relatedId),
                new NVPair("relatedType", relatedType),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()),
                new NVPair("rows", rowData));

        return Arrays.asList(rowData.getBeans());
    }

    private Document find(Integer documentId) {
        Document document = new Document();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT id, ");
        varname1.append("       file_name, ");
        varname1.append("       file_name_on_disk ");
        varname1.append("FROM   documents ");
        varname1.append("WHERE  id = :documentId ");
        varname1.append("INTO   :{document.id}, ");
        varname1.append("       :{document.fileName}, ");
        varname1.append("       :{document.fileNameOnDisk} ");
        SQL.selectInto(varname1.toString(), new NVPair("document", document), new NVPair("documentId", documentId));

        return document.getId() != null ? document : null;
    }

    @Override
    public BinaryResource download(Integer documentId) {
        Document document = find(documentId);

        if (document == null) {
            throw new VetoException(TEXTS.get("FileNotFoundOnServer"));
        }

        String filePath = getRootPath() + document.getFileNameOnDisk();

        File file = new File(filePath);

        BinaryResource binaryResource = null;
        if (file.exists() && file.isFile()) {
            binaryResource = new BinaryResource(document.getFileName(), IOUtility.getContent(file));
        }

        return binaryResource;
    }

    private String getRootPath() {
        String subdomain = ServerSession.get().getCurrentOrganisation().getSubdomain();
        return CONFIG.getPropertyValue(AppFileRootDirectoryOrganisationsConfigProperty.class) + FILE_SEPARATOR + subdomain + FILE_SEPARATOR;
    }

    @Override
    public void delete(List<Integer> documentIds) {
        String stmt = "UPDATE documents SET deleted_at = now() WHERE id = :documentIds";
        SQL.update(stmt, new NVPair("documentIds", documentIds));

        emitModuleEvent(Document.class, new Document(), ChangeStatus.DELETED);
    }

    @Override
    public void upload(List<Attachment> attachments) {
        if (CollectionUtility.isEmpty(attachments)) return;

        for (Attachment attachment : attachments) {
            uploadFile(attachment.getBinaryResource(), attachment.getFileName(), attachment.getFileName(), attachment.getRelatedId(), attachment.getRelatedTypeId());
        }
    }

    private Integer uploadFile(BinaryResource file, String name, String description, Integer relatedId, Integer relatedType) {
        IntegerHolder holder = new IntegerHolder();

        try {
            UploadedFile uploadedFile = BEANS.get(UploadUtility.class).uploadFile(file);

            StringBuffer varname1 = new StringBuffer();
            varname1.append("INSERT INTO documents ");
            varname1.append("            (user_id, ");
            varname1.append("             name, ");
            varname1.append("             absolute_path, ");
            varname1.append("             relative_path, ");
            varname1.append("             full_path, ");
            varname1.append("             organisation_id, ");
            varname1.append("             file_name, ");
            varname1.append("             file_name_on_disk, ");
            varname1.append("             file_size, ");
            varname1.append("             file_type, ");
            varname1.append("             file_extension, ");
            varname1.append("             description, ");
            varname1.append("             related_id, ");
            varname1.append("             related_type, ");
            varname1.append("             created_at) ");
            varname1.append("VALUES      (:userId, ");
            varname1.append("             :Name, ");
            varname1.append("             :absolutePath, ");
            varname1.append("             :relativePath, ");
            varname1.append("             :fullPath, ");
            varname1.append("             :organisationId, ");
            varname1.append("             :fileName, ");
            varname1.append("             :fileNameOnDisk, ");
            varname1.append("             :fileSize, ");
            varname1.append("             :fileType, ");
            varname1.append("             :fileExtension, ");
            varname1.append("             :Description, ");
            varname1.append("             :relatedId, ");
            varname1.append("             :relatedType, ");
            varname1.append("             now()) RETURNING id INTO :documentId");
            SQL.selectInto(
                    varname1.toString(),
                    new NVPair("relatedId", relatedId),
                    new NVPair("relatedType", relatedType),
                    new NVPair("userId", ServerSession.get().getCurrentUser().getId()),
                    new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()),
                    new NVPair("fileName", uploadedFile.getBinaryResource().getFilename()),
                    new NVPair("fileNameOnDisk", uploadedFile.getFileNameOnDisk()),
                    new NVPair("absolutePath", uploadedFile.getAbsolutePath()),
                    new NVPair("documentId", holder),
                    new NVPair("fullPath", uploadedFile.getFullPath()),
                    new NVPair("Name", ObjectUtility.nvl(name, uploadedFile.getBinaryResource().getFilename())),
                    new NVPair("Description", description),
                    new NVPair("relativePath", uploadedFile.getRelativePath()),
                    new NVPair("fileSize", uploadedFile.getBinaryResource().getContentLength()),
                    new NVPair("fileType", uploadedFile.getBinaryResource().getContentType()),
                    new NVPair("fileExtension", MimeType.convertToMimeType(uploadedFile.getBinaryResource().getContentType()).getFileExtension()));
        } catch (IOException e) {
            throw new VetoException(TEXTS.get("ErrorWhileUploadingFile", e.getMessage()));
        }

        return holder.getValue();
    }

}
