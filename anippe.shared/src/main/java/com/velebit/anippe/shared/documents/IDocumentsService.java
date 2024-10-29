package com.velebit.anippe.shared.documents;

import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.documents.DocumentsFormData.DocumentsTable.DocumentsTableRowData;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IDocumentsService extends IService {
    List<DocumentsTableRowData> fetchDocuments(Integer relatedId, Integer relatedType);

    BinaryResource download(Integer selectedValue);

    void delete(List<Integer> documentIds);

    void upload(List<Attachment> attachments);
}
