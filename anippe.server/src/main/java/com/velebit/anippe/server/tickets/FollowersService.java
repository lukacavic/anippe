package com.velebit.anippe.server.tickets;

import com.velebit.anippe.shared.tickets.FollowersFormData;
import com.velebit.anippe.shared.tickets.IFollowersService;
import org.eclipse.scout.rt.platform.holders.LongArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Set;

public class FollowersService implements IFollowersService {

    @Override
    public FollowersFormData prepareCreate(FollowersFormData formData) {
        return formData;
    }

    @Override
    public FollowersFormData create(FollowersFormData formData) {
        if (CollectionUtility.isEmpty(formData.getFollowersBox().getValue())) return formData;

        deleteFollowers(formData.getTicketId());

        for (Long userId : formData.getFollowersBox().getValue()) {
            SQL.insert("INSERT INTO ticket_followers (user_id, ticket_id) VALUES (:userId, :ticketId)", new NVPair("userId", userId), new NVPair("ticketId", formData.getTicketId()));
        }

        return formData;
    }

    @Override
    public Set<Long> fetchFollowers(Integer ticketId) {
        LongArrayHolder holder = new LongArrayHolder();

        SQL.selectInto("SELECT user_id FROM ticket_followers WHERE ticket_id = :ticketId INTO :holder", new NVPair("ticketId", ticketId), new NVPair("holder", holder));

        return CollectionUtility.hashSet(holder.getValue());
    }

    private void deleteFollowers(Integer ticketId) {
        SQL.delete("DELETE FROM ticket_followers WHERE ticket_id = :ticketId", new NVPair("ticketId", ticketId));
    }

}
