package com.velebit.anippe.server.contacts;

import com.velebit.anippe.shared.contacts.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class ContactsService implements IContactsService {
    @Override
    public ContactsFormData prepareCreate(ContactsFormData formData) {
        return formData;
    }

    @Override
    public ContactsFormData create(ContactsFormData formData) {
        return formData;
    }
}
