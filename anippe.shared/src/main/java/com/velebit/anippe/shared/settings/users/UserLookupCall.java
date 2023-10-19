package com.velebit.anippe.shared.settings.users;

import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import java.util.List;

public class UserLookupCall extends LookupCall<Long> {
    private static final long serialVersionUID = 1L;

    private List<Long> excludeIds = CollectionUtility.emptyArrayList();
    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Long> getExcludeIds() {
        return excludeIds;
    }

    public void setExcludeIds(List<Long> excludeIds) {
        this.excludeIds = excludeIds;
    }

    @Override
    protected Class<? extends ILookupService<Long>> getConfiguredService() {
        return IUserLookupService.class;
    }
}
