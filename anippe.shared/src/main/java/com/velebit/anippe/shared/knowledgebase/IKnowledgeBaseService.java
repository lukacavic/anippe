package com.velebit.anippe.shared.knowledgebase;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IKnowledgeBaseService extends IService {
    List<Article> fetchArticles(Integer projectId, Long typeId);
}
