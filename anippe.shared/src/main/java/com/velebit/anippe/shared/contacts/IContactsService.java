package com.velebit.anippe.shared.contacts;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IContactsService extends IService {
    ContactsFormData prepareCreate(ContactsFormData formData);

    ContactsFormData create(ContactsFormData formData);

}
