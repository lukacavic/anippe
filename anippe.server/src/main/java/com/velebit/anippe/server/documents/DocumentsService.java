package com.velebit.anippe.server.documents;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.config.AppFileRootDirectoryOrganisationsConfigProperty;
import com.velebit.anippe.shared.beans.Document;
import com.velebit.anippe.shared.documents.DocumentsFormData.DocumentsTable.DocumentsTableRowData;
import com.velebit.anippe.shared.documents.IDocumentsService;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ChangeStatus;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DocumentsService extends AbstractService implements IDocumentsService {

    @Override
    public List<DocumentsTableRowData> fetchDocuments(Integer relatedId, Integer relatedType, List<Integer> temporaryDocumentIds) {
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

        if (!CollectionUtility.isEmpty(temporaryDocumentIds)) {
            varname1.append(" UNION ");

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
            varname1.append("       AND d.id = :temporaryDocumentIds ");
        }

        varname1.append("INTO ");
        varname1.append(":{rows.DocumentId},:{rows.Name}, :{rows.Size}, :{rows.Type}, :{rows.User}, :{rows.CreatedAt}, :{rows.UpdatedAt}");
        SQL.selectInto(
                varname1.toString(),
                new NVPair("relatedId", relatedId),
                new NVPair("relatedType", relatedType),
                new NVPair("temporaryDocumentIds", temporaryDocumentIds),
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
    public void linkNotConnectedDocuments(List<Integer> temporaryDocumentIds, Integer relatedType, Integer relatedId) {
        String stmt = "UPDATE documents SET related_id = :relatedId, related_type = :relatedType WHERE id = :documentIds";
        SQL.update(stmt, new NVPair("relatedId", relatedId), new NVPair("relatedType", relatedType), new NVPair("documentIds", temporaryDocumentIds));
    }

}
