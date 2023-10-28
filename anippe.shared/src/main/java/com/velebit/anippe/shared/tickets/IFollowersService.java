package com.velebit.anippe.shared.tickets;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.Set;

@TunnelToServer
public interface IFollowersService extends IService {

    FollowersFormData prepareCreate(FollowersFormData formData);

    FollowersFormData create(FollowersFormData formData);

    Set<Long> fetchFollowers(Integer ticketId);
}
