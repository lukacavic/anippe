package com.velebit.anippe.server.servers;

import com.velebit.anippe.shared.clients.Client;
import com.velebit.anippe.shared.shareds.ClientCardFormData;
import com.velebit.anippe.shared.shareds.IClientCardService;
import org.eclipse.scout.rt.platform.BEANS;

public class ClientCardService implements IClientCardService {

    @Override
    public ClientCardFormData load(ClientCardFormData formData) {
        Client client = BEANS.get(ClientDao.class).find(formData.getClientId());
        formData.setClient(client);

        return formData;
    }

    @Override
    public ClientCardFormData store(ClientCardFormData formData) {
        return formData;
    }

}
