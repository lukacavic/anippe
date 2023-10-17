package com.velebit.anippe.server.attachments;

import com.velebit.anippe.shared.attachments.Attachment;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Arrays;
import java.util.List;

@Bean
public class AttachmentDao {

	public List<Attachment> get(Integer relatedId, Integer relatedType) {
		BeanArrayHolder<Attachment> attachments = new BeanArrayHolder<>(Attachment.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT id, ");
		varname1.append("       NAME, ");
		varname1.append("       file_name, ");
		varname1.append("       description, ");
		varname1.append("       file_size, ");
		varname1.append("       file_extension, ");
		varname1.append("       created_at, ");
		varname1.append("       user_id ");
		varname1.append("FROM   attachments ");
		varname1.append("WHERE  related_id = :relatedId ");
		varname1.append("AND    related_type = :relatedType ");
		varname1.append("AND    deleted_at IS NULL ");
		varname1.append("INTO   :{rows.id}, ");
		varname1.append("       :{rows.name}, ");
		varname1.append("       :{rows.fileName}, ");
		varname1.append("       :{rows.description}, ");
		varname1.append("       :{rows.fileSize}, ");
		varname1.append("       :{rows.fileExtension}, ");
		varname1.append("       :{rows.createdAt}, ");
		varname1.append("       :{rows.userId}");
		SQL.selectInto(varname1.toString(), new NVPair("rows", attachments), new NVPair("relatedId", relatedId), new NVPair("relatedType", relatedType));

		return Arrays.asList(attachments.getBeans());
	}

	public void delete(Integer relatedType, Integer relatedId) {
		String stmt = "UPDATE attachments SET deleted_at = now() WHERE related_id = :relatedId AND related_type = :relatedType";
		SQL.update(stmt, new NVPair("relatedId", relatedId), new NVPair("relatedType", relatedType));
	}

}
