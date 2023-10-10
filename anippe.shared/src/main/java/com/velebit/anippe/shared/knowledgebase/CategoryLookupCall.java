package com.velebit.anippe.shared.knowledgebase;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class CategoryLookupCall extends LookupCall<Long> {
    private static final long serialVersionUID = 1L;

    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    protected Class<? extends ILookupService<Long>> getConfiguredService() {
        return ICategoryLookupService.class;
    }
}
