package com.velebit.anippe.server.email;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.server.organisations.OrganisationDao;
import com.velebit.anippe.server.servers.ClientDao;
import com.velebit.anippe.shared.beans.*;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.email.Email;
import com.velebit.anippe.shared.email.EmailAttachment;
import com.velebit.anippe.shared.email.EmailRequest;
import com.velebit.anippe.shared.organisations.EmailSettings;
import com.velebit.anippe.shared.organisations.Organisation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class EmailDao {

	Logger LOG = LoggerFactory.getLogger(getClass());

	public Email find(Integer emailId) {
		EmailRequest request = new EmailRequest();

		return get(request).stream().filter(r -> r.getId().equals(emailId)).findFirst().orElse(null);
	}

	public void markAsNotSent(Email email, String errorMessage) {
		String stmt = "UPDATE emails SET status_id = :statusNotSent, error_message = :errorMessage WHERE id = :emailId";
		SQL.update(stmt, new NVPair("emailId", email.getId()), new NVPair("statusNotSent", Constants.EmailStatus.ERROR), new NVPair("errorMessage", errorMessage));

		LOG.info("Email with ID {} not sent.", email.getId());

		SQL.commit();
	}

	public void processSentMail(Email email) {
		String stmt = "UPDATE emails SET status_id = :statusSent WHERE id = :emailId";
		SQL.update(stmt, new NVPair("emailId", email.getId()), new NVPair("statusSent", Constants.EmailStatus.SENT));

		LOG.info("Email queue with ID {} sent success.", email.getId());
	}

	private List<BinaryResource> findEmailQueueAttachments(Integer emailId) {
		BeanArrayHolder<EmailAttachment> attachments = new BeanArrayHolder<>(EmailAttachment.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT eqa.content, ");
		varname1.append("       eqa.file_name, ");
		varname1.append("       eqa.file_size, ");
		varname1.append("       eqa.file_type ");
		varname1.append("FROM   email_attachments eqa ");
		varname1.append("WHERE  eqa.email_id = :emailId ");
		varname1.append("INTO   :{rows.content}, ");
		varname1.append("       :{rows.fileName}, ");
		varname1.append("       :{rows.fileSize}, ");
		varname1.append("       :{rows.fileExtension}");
		SQL.selectInto(varname1.toString(), new NVPair("rows", attachments), new NVPair("emailId", emailId));

		List<BinaryResource> binaryResources = new ArrayList<>();
		for (EmailAttachment attachment : attachments.getBeans()) {
			binaryResources.add(new BinaryResource(attachment.getFileName(), attachment.getContent()));
		}

		return binaryResources;
	}

	public List<EmailSettings> findAllOrganisationsEmailSettings() {
		BeanArrayHolder<EmailSettings> emailSettings = new BeanArrayHolder<>(EmailSettings.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT o.id, ");
		varname1.append("       os.email_smtp_server, ");
		varname1.append("       os.email_smtp_username, ");
		varname1.append("       os.email_smtp_port, ");
		varname1.append("       os.email_smtp_password, ");
		varname1.append("       os.email_smtp_protocol, ");
		varname1.append("       os.email_smtp_email ");
		varname1.append("FROM   organisation_settings os, ");
		varname1.append("       organisations o ");
		varname1.append("WHERE  os.organisation_id = o.id ");
		varname1.append("       AND o.deleted_at IS NULL ");
		varname1.append("INTO ");
		varname1.append(":{rows.organisationId}, :{rows.smtpServer}, :{rows.smtpUsername}, :{rows.smtpPort}, :{rows.smtpPassword}, :{rows.smtpProtocol}, :{rows.smtpEmail} ");
		SQL.selectInto(varname1.toString(), new NVPair("rows", emailSettings));

		return Arrays.asList(emailSettings.getBeans());
	}

	public Integer insert(EmailRequest request) {
		IntegerHolder emailId = new IntegerHolder();

		if (CollectionUtility.isEmpty(request.getReceivers()))
			return null;

		CollectionUtils.filter(request.getReceivers(), PredicateUtils.notNullPredicate());

		String receivers = String.join(",", request.getReceivers());
		String bccReceivers = null;
		String ccReceivers = null;

		Organisation organisation = null;
		if (request.getOrganisationId() != null) {
			organisation = BEANS.get(OrganisationDao.class).findById(request.getOrganisationId());
		} else {
			organisation = ServerSession.get().getCurrentOrganisation();
		}

		if (!CollectionUtility.isEmpty(request.getBccReceivers())) {
			CollectionUtils.filter(request.getBccReceivers(), PredicateUtils.notNullPredicate());

			bccReceivers = String.join(",", request.getBccReceivers());
		}

		if (!CollectionUtility.isEmpty(request.getCcReceivers())) {
			CollectionUtils.filter(request.getCcReceivers(), PredicateUtils.notNullPredicate());

			ccReceivers = String.join(",", request.getCcReceivers());
		}

		if (StringUtility.isNullOrEmpty(request.getSenderName())) {
			request.setSenderName(request.getSenderName());
		}

		if (StringUtility.isNullOrEmpty(request.getSenderEmail())) {
			request.setSenderEmail(organisation.getOrganisationSettings().getEmailSettings().getSmtpEmail());
		}

		if (StringUtility.isNullOrEmpty(request.getSenderName())) {
			request.setSenderName(organisation.getName());
		}

		if (request.getSubject() != null) {
			request.setSubject(StringUtility.join(" - ", request.getSubject()));
		}

		if (request.getUserId() == null) {
			request.setUserId(-1);
		}

		if (request.getLanguage() == null) {
			request.setLanguage("hr");
		}

		StringBuffer varname1 = new StringBuffer();
		varname1.append("INSERT INTO emails ");
		varname1.append("            (created_at, ");
		varname1.append("             subject, ");
		varname1.append("             message, ");
		varname1.append("             status_id, ");
		varname1.append("             related_id, ");
		varname1.append("             related_type, ");
		varname1.append("             organisation_id, ");
		varname1.append("             user_id, ");
		varname1.append("             sender_name, ");
		varname1.append("             sender_email, ");
		varname1.append("             receivers, ");
		varname1.append("             cc_receivers, ");
		varname1.append("             bcc_receivers, ");
		varname1.append("             client_id) ");
		varname1.append("VALUES      (now(), ");
		varname1.append("             :{request.subject}, ");
		varname1.append("             :body, ");
		varname1.append("             :statusPrepareSend, ");
		varname1.append("             :{request.relatedId}, ");
		varname1.append("             :{request.relatedType}, ");
		varname1.append("             :{request.organisationId}, ");
		varname1.append("             :{request.userId}, ");
		varname1.append("             :{organisation.name}, ");
		varname1.append("             :{organisation.email}, ");
		varname1.append("             :receivers, ");
		varname1.append("             :ccReceivers, ");
		varname1.append("             :bccReceivers, ");
		varname1.append("             :{request.clientId}) ");
		varname1.append("RETURNING id INTO :emailId");
		SQL.selectInto(
				varname1.toString(),
				new NVPair("emailId", emailId),
				new NVPair("body", request.getMessage()),
				new NVPair("receivers", receivers),
				new NVPair("organisation", organisation),
				new NVPair("request", request),
				new NVPair("ccReceivers", ccReceivers),
				new NVPair("bccReceivers", bccReceivers),
				new NVPair("statusPrepareSend", Constants.EmailStatus.PREPARE_SEND));

		return emailId.getValue();
	}

	public void delete(List<Integer> emailIds) {
		String stmt = "UPDATE emails SET deleted_at = now() WHERE id = :emailIds";
		SQL.update(stmt, new NVPair("emailIds", emailIds));
	}

	public List<Email> findEmailsToSend() {
		BeanArrayHolder<Email> emails = new BeanArrayHolder<>(Email.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT   id, ");
		varname1.append("         subject, ");
		varname1.append("         message, ");
		varname1.append("         sender_name, ");
		varname1.append("         sender_email, ");
		varname1.append("         receivers, ");
		varname1.append("         cc_receivers, ");
		varname1.append("         bcc_receivers, ");
		varname1.append("         client_id, ");
		varname1.append("         organisation_id, ");
		varname1.append("         user_id ");
		varname1.append("FROM     emails ");
		varname1.append("WHERE    deleted_at IS NULL ");
		varname1.append("AND      status_id = :statusPrepareSend ");
		varname1.append("ORDER BY organisation_id, id ASC ");
		varname1.append("LIMIT 20 ");
		varname1.append("INTO     :{rows.id}, ");
		varname1.append("         :{rows.subject}, ");
		varname1.append("         :{rows.content}, ");
		varname1.append("         :{rows.senderName}, ");
		varname1.append("         :{rows.senderEmail}, ");
		varname1.append("         :{rows.receivers}, ");
		varname1.append("         :{rows.ccReceivers}, ");
		varname1.append("         :{rows.bccReceivers}, ");
		varname1.append("         :{rows.clientId},");
		varname1.append("         :{rows.organisationId},");
		varname1.append("         :{rows.userId}");
		SQL.selectInto(varname1.toString(), new NVPair("rows", emails), new NVPair("statusPrepareSend", Constants.EmailStatus.PREPARE_SEND));
		List<Email> emailQueues = Arrays.asList(emails.getBeans());

		for (Email email : emailQueues) {
			email.setAttachments(findEmailQueueAttachments(email.getId()));
		}

		return emailQueues;
	}

	public List<Email> get(EmailRequest request) {
		BeanArrayHolder<EmailDto> emailDto = new BeanArrayHolder<EmailDto>(EmailDto.class);

		List<Email> emails = new ArrayList<>();

		StringBuffer varname1 = new StringBuffer();
		varname1.append("WITH email_attachments_count AS ");
		varname1.append("( ");
		varname1.append("         SELECT   et.email_id, ");
		varname1.append("                  Count(0) AS total_attachments ");
		varname1.append("         FROM     email_attachments et ");
		varname1.append("         GROUP BY email_id ) ");
		varname1.append("SELECT          e.id, ");
		varname1.append("                e.subject, ");
		varname1.append("                e.message, ");
		varname1.append("                u.id, ");
		varname1.append("                u.first_name, ");
		varname1.append("                u.last_name, ");
		varname1.append("                e.created_at, ");
		varname1.append("                COALESCE(eac.total_attachments, 0), ");
		varname1.append("                e.receivers, ");
		varname1.append("                e.cc_receivers, ");
		varname1.append("                e.bcc_receivers, ");
		varname1.append("                e.status_id ");
		varname1.append("FROM            emails e ");
		varname1.append("LEFT OUTER JOIN email_attachments_count eac ");
		varname1.append("ON              eac.email_id = e.id , ");
		varname1.append("                users u ");
		varname1.append("WHERE           e.user_id = u.id ");
		varname1.append("AND             e.deleted_at IS NULL ");
		varname1.append("AND             e.organisation_id = :organisationId ");

		if (request.getId() != null) {
			varname1.append(" AND e.id = :{request.id} ");
		}

		if (request.getClientId() != null) {
			varname1.append(" AND e.client_id = :{request.clientId} ");
		}

		varname1.append("ORDER BY        e.created_at DESC ");
		varname1.append("into            :{dto.id}, ");
		varname1.append("                :{dto.subject}, ");
		varname1.append("                :{dto.message}, ");
		varname1.append("                :{dto.userId}, ");
		varname1.append("                :{dto.userFirstName}, ");
		varname1.append("                :{dto.userLastName}, ");
		varname1.append("                :{dto.createdAt}, ");
		varname1.append("                :{dto.attachmentsCount},");
		varname1.append("                :{dto.receivers},");
		varname1.append("                :{dto.ccReceivers},");
		varname1.append("                :{dto.bccReceivers}, ");
		varname1.append("                :{dto.statusId} ");
		SQL.selectInto(varname1.toString(), new NVPair("dto", emailDto), new NVPair("request", request), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("userId", ServerSession.get().getCurrentUser().getId()));

		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new EmailMap());

		List<EmailDto> dtos = Arrays.asList(emailDto.getBeans());

		dtos.forEach(item -> emails.add(mapper.map(item, Email.class)));

		return emails;
	}


    public List<Email> getPortal(EmailRequest request) {
        BeanArrayHolder<EmailDto> emailDto = new BeanArrayHolder<EmailDto>(EmailDto.class);

        List<Email> emails = new ArrayList<>();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("WITH email_attachments_count AS ");
        varname1.append("( ");
        varname1.append("         SELECT   et.email_id, ");
        varname1.append("                  Count(0) AS total_attachments ");
        varname1.append("         FROM     email_attachments et ");
        varname1.append("         GROUP BY email_id ) ");
        varname1.append("SELECT          e.id, ");
        varname1.append("                e.subject, ");
        varname1.append("                e.message, ");
        varname1.append("                u.id, ");
        varname1.append("                u.first_name, ");
        varname1.append("                u.last_name, ");
        varname1.append("                e.created_at, ");
        varname1.append("                COALESCE(eac.total_attachments, 0), ");
        varname1.append("                e.receivers, ");
        varname1.append("                e.cc_receivers, ");
        varname1.append("                e.bcc_receivers, ");
        varname1.append("                e.status_id ");
        varname1.append("FROM            emails e ");
        varname1.append("LEFT OUTER JOIN email_attachments_count eac ");
        varname1.append("ON              eac.email_id = e.id , ");
        varname1.append("                users u ");
        varname1.append("WHERE           e.user_id = u.id ");
        varname1.append("AND             e.deleted_at IS NULL ");
        varname1.append("AND             e.organisation_id = :organisationId ");

        if (request.getId() != null) {
            varname1.append(" AND e.id = :{request.id} ");
        }

        if (request.getClientId() != null) {
            varname1.append(" AND e.client_id = :{request.clientId} ");
        }

        if (request.getStatusId() != null) {
            varname1.append(" AND e.status_id = :{request.statusId} ");
        }

        varname1.append("ORDER BY        e.created_at DESC ");
        varname1.append("into            :{dto.id}, ");
        varname1.append("                :{dto.subject}, ");
        varname1.append("                :{dto.message}, ");
        varname1.append("                :{dto.userId}, ");
        varname1.append("                :{dto.userFirstName}, ");
        varname1.append("                :{dto.userLastName}, ");
        varname1.append("                :{dto.createdAt}, ");
        varname1.append("                :{dto.attachmentsCount},");
        varname1.append("                :{dto.receivers},");
        varname1.append("                :{dto.ccReceivers},");
        varname1.append("                :{dto.bccReceivers}, ");
        varname1.append("                :{dto.statusId} ");
        SQL.selectInto(varname1.toString(),
            new NVPair("dto", emailDto),
            new NVPair("request", request),
            new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId())
        );

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new EmailMap());

        List<EmailDto> dtos = Arrays.asList(emailDto.getBeans());

        dtos.forEach(item -> emails.add(mapper.map(item, Email.class)));

        return emails;
    }

	public List<EmailAttachment> findAttachments(Integer emailId) {
		BeanArrayHolder<EmailAttachment> attachments = new BeanArrayHolder<>(EmailAttachment.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT id,  ");
		varname1.append("       file_name, ");
		varname1.append("       file_size, ");
		varname1.append("       file_type, ");
		varname1.append("       content "); // TODO:: Do not load content here. Performace reasons
		varname1.append("FROM   email_attachments ");
		varname1.append("WHERE  email_id = :emailId ");
		varname1.append("INTO   :{row.id}, ");
		varname1.append("       :{row.fileName}, ");
		varname1.append("       :{row.fileSize}, ");
		varname1.append("       :{row.fileExtension}, ");
		varname1.append("       :{row.content} ");
		SQL.selectInto(varname1.toString(), new NVPair("emailId", emailId), new NVPair("row", attachments));

		return CollectionUtility.arrayList(attachments.getBeans());
	}
}
