package com.velebit.anippe.shared.utilities.announcements;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IAnnouncementService extends IService {
    AnnouncementFormData prepareCreate(AnnouncementFormData formData);

    AnnouncementFormData create(AnnouncementFormData formData);

    AnnouncementFormData load(AnnouncementFormData formData);

    AnnouncementFormData store(AnnouncementFormData formData);

    AnnouncementFormData preview(AnnouncementFormData formData);

    void markAsRead(Integer announcementId);
}
