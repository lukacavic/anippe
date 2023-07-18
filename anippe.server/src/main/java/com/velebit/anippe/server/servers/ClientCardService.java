package com.velebit.anippe.server.servers;

import com.velebit.anippe.shared.shareds.ClientCardFormData;
import com.velebit.anippe.shared.shareds.IClientCardService;

public class ClientCardService implements IClientCardService {

    @Override
    public ClientCardFormData load(ClientCardFormData formData) {
        return formData;
    }

    @Override
    public ClientCardFormData store(ClientCardFormData formData) {
        return formData;
    }

}
