package com.velebit.anippe.server.documents;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.utilities.UploadUtility;
import com.velebit.anippe.shared.attachments.UploadedFile;
import com.velebit.anippe.shared.documents.Document;
import com.velebit.anippe.shared.documents.IUploadService;
import com.velebit.anippe.shared.documents.UploadFormData;
import com.velebit.anippe.shared.documents.UploadFormData.UploadedFilesTable.UploadedFilesTableRowData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.resource.MimeType;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ChangeStatus;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class UploadService extends AbstractService implements IUploadService {

    private static final Logger LOG = LoggerFactory.getLogger(UploadService.class);

    @Override
    public UploadFormData prepareCreate(UploadFormData formData) {
        return formData;
    }

    @Override
    public UploadFormData create(UploadFormData formData) {
        List<Integer> documentIds = CollectionUtility.emptyArrayList();

        if (formData.getDocument().getValue() != null) {
            Integer documentId = uploadFile(formData.getDocument().getValue(), formData.getName().getValue(), formData.getDescription().getValue(), formData.getRelatedId(), formData.getRelatedType());
            documentIds.add(documentId);
        }

        for (UploadedFilesTableRowData row : formData.getUploadedFilesTable().getRows()) {
            Integer documentId = uploadFile(row.getFile(), row.getName(), row.getDescription(), formData.getRelatedId(), formData.getRelatedType());
            documentIds.add(documentId);
        }

        emitModuleEvent(Document.class, new Document(), ChangeStatus.INSERTED);

        formData.setDocumentIds(documentIds);

        return formData;
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
            LOG.error(e.getMessage());
            throw new VetoException(TEXTS.get("ErrorWhileUploadingFile", e.getMessage()));
        }

        return holder.getValue();
    }

    @Override
    public UploadFormData load(UploadFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT d.name, ");
        varname1.append("       d.description, ");
        varname1.append("       d.created_at, ");
        varname1.append("       u.last_name ");
        varname1.append("       || ' ' ");
        varname1.append("       || u.first_name ");
        varname1.append("FROM   documents d, ");
        varname1.append("       users u ");
        varname1.append("WHERE  d.user_id = u.id ");
        varname1.append("       AND d.id = :documentId ");
        varname1.append("INTO   :Name, :Description, :CreatedAt, :User");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public UploadFormData store(UploadFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE documents ");
        varname1.append("SET    name = :Name, ");
        varname1.append("       description = :Description, ");
        varname1.append("       updated_at = Now() ");
        varname1.append("WHERE  id = :documentId");
        SQL.update(varname1.toString(), formData);

        emitModuleEvent(Document.class, new Document(), ChangeStatus.UPDATED);

        return formData;
    }
}
