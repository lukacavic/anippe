package com.velebit.anippe.server.reminders;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.reminders.Reminder;
import com.velebit.anippe.shared.reminders.ReminderRequest;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class ReminderDao {

	public List<Reminder> getByRequest(ReminderRequest request) {
		BeanArrayHolder<ReminderDto> dtos = new BeanArrayHolder<>(ReminderDto.class);
		List<Reminder> reminders = new ArrayList<>();

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT   r.id, ");
		varname1.append("         r.created_at, ");
		varname1.append("         r.user_created_id, ");
		varname1.append("         uc.last_name, ");
		varname1.append("         uc.first_name, ");
		varname1.append("         r.remind_at, ");
		varname1.append("         r.title, ");
		varname1.append("         r.content, ");
		varname1.append("         r.system_generated, ");
		varname1.append("         r.send_email, ");
		varname1.append("         u.id, ");
		varname1.append("         u.last_name, ");
		varname1.append("         u.first_name ");
		varname1.append("FROM     reminders r, users u, users uc ");
		varname1.append("WHERE    r.user_created_id = uc.id ");
		varname1.append("AND      r.deleted_at IS NULL ");
		varname1.append("AND      r.user_id = u.id ");
		varname1.append("AND      r.organisation_id = :organisationId ");

		if (request.getRelatedId() != null) {
			varname1.append(" AND r.related_id = :{request.relatedId} ");
		}

		if (request.getRelatedType() != null) {
			varname1.append(" AND r.related_type = :{request.relatedType} ");
		}

		varname1.append("ORDER BY r.remind_at ASC ");
		varname1.append("INTO     :{dto.id}, ");
		varname1.append("         :{dto.createdAt}, ");
		varname1.append("         :{dto.userCreatedId}, ");
		varname1.append("         :{dto.userCreatedLastName}, ");
		varname1.append("         :{dto.userCreatedFirstName}, ");
		varname1.append("         :{dto.remindAt}, ");
		varname1.append("         :{dto.title}, ");
		varname1.append("         :{dto.content}, ");
		varname1.append("         :{dto.systemGenerated},  ");
		varname1.append("         :{dto.sendEmail},  ");
		varname1.append("         :{dto.userId},  ");
		varname1.append("         :{dto.userFirstName},  ");
		varname1.append("         :{dto.userLastName}  ");
		SQL.selectInto(varname1.toString(), new NVPair("dto", dtos), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("request", request));
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new ReminderMap());

		List<ReminderDto> dto = Arrays.asList(dtos.getBeans());

		dto.forEach(item -> reminders.add(mapper.map(item, Reminder.class)));

		return reminders;
	}

	public void create(ReminderRequest request) {

		StringBuffer varname1 = new StringBuffer();
		varname1.append("INSERT INTO reminders ");
		varname1.append("            ( ");
		varname1.append("                        created_at, ");
		varname1.append("                        organisation_id, ");
		varname1.append("                        system_generated, ");
		varname1.append("                        user_created_id, ");
		varname1.append("                        title, ");
		varname1.append("                        content, ");
		varname1.append("                        remind_at, ");
		varname1.append("                        related_type, ");
		varname1.append("                        related_id ");
		varname1.append("            ) ");
		varname1.append("            VALUES ");
		varname1.append("            ( ");
		varname1.append("                        now(), ");
		varname1.append("                        :organisationId, ");
		varname1.append("                        :{request.systemGenerated}, ");
		varname1.append("                        :userId, ");
		varname1.append("                        :{request.title}, ");
		varname1.append("                        :{request.content}, ");
		varname1.append("                        :{request.remindAt}, ");
		varname1.append("                        :{request.relatedType}, ");
		varname1.append("                        :{request.relatedId} ");
		varname1.append("            )");
		SQL.insert(varname1.toString(), new NVPair("request", request), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("userId", ServerSession.get().getCurrentUser().getId()));
	}

	public void delete(Integer relatedType, Integer relatedId) {
		String stmt = "UPDATE reminders SET deleted_at = now() WHERE related_id = :relatedId AND related_type = :relatedType";
		SQL.update(stmt, new NVPair("relatedId", relatedId), new NVPair("relatedType", relatedType));
	}

	public void delete(Integer reminderId) {
		String stmt = "UPDATE reminders SET deleted_at = now() WHERE id = :reminderId";
		SQL.update(stmt, new NVPair("reminderId", reminderId));
	}
}
