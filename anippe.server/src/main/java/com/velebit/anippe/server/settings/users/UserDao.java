package com.velebit.anippe.server.settings.users;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.settings.users.UserRequest;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.matcher.StringMatcher;

import java.util.ArrayList;
import java.util.List;

@Bean
public class UserDao {

    public List<User> get(UserRequest request) {
        BeanArrayHolder<UserDto> dto = new BeanArrayHolder<UserDto>(UserDto.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT u.id, ");
        varname1.append("       u.first_name, ");
        varname1.append("       u.last_name, ");
        varname1.append("       u.username, ");
        varname1.append("       u.email, ");
        varname1.append("       u.last_login_at, ");
        varname1.append("       u.active, ");
        varname1.append("       u.created_at, ");
        varname1.append("       u.administrator ");
        varname1.append("FROM   users u ");
        varname1.append("WHERE  u.deleted_at IS NULL ");
        varname1.append("AND    u.organisation_id = :organisationId ");
        varname1.append("into   :{dto.id}, ");
        varname1.append("       :{dto.firstName}, ");
        varname1.append("       :{dto.lastName}, ");
        varname1.append("       :{dto.username}, ");
        varname1.append("       :{dto.email}, ");
        varname1.append("       :{dto.lastLoginAt}, ");
        varname1.append("       :{dto.active}, ");
        varname1.append("       :{dto.createdAt}, ");
        varname1.append("       :{dto.administrator} ");
        SQL.selectInto(varname1.toString(), new NVPair("dto", dto), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("dto", dto));
        List<UserDto> dtos = CollectionUtility.arrayList(dto.getBeans());

        List<User> users = CollectionUtility.emptyArrayList();

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new UserMap());
        dtos.forEach(item -> users.add(mapper.map(item, User.class)));

        return users;
    }
}
