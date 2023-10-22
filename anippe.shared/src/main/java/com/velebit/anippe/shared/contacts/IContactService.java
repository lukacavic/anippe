package com.velebit.anippe.shared.contacts;

import com.velebit.anippe.shared.clients.Contact;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IContactService extends IService {
    ContactFormData prepareCreate(ContactFormData formData);

    ContactFormData create(ContactFormData formData);

    ContactFormData load(ContactFormData formData);

    ContactFormData store(ContactFormData formData);

    void delete(Integer contactId);

    void toggleActivated(Integer contactId, Boolean value);

    boolean isEmailUnique(String rawValue, Integer contactId);

    Contact findContactByEmail(String email, Integer organisationId);

}
