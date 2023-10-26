package com.velebit.anippe.shared.knowledgebase;

import com.velebit.anippe.shared.knowledgebase.KnowledgeBaseFormData.ArticlesTable.ArticlesTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IKnowledgeBaseService extends IService {
    List<ArticlesTableRowData> fetchArticles(Integer projectId, Long typeId);
}
