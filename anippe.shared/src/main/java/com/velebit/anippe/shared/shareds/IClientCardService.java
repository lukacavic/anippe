package com.velebit.anippe.shared.shareds;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IClientCardService extends IService {
    ClientCardFormData load(ClientCardFormData formData);

    ClientCardFormData store(ClientCardFormData formData);
}
