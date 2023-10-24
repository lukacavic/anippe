package com.velebit.anippe.server.tickets.importer;

import com.sun.mail.imap.protocol.FLAGS;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.organisations.Organisation;
import com.velebit.anippe.shared.tickets.TicketDepartment;
import org.eclipse.scout.rt.mail.imap.ImapHelper;
import org.eclipse.scout.rt.mail.imap.ImapServerConfig;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.IServerSession;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.server.context.ServerRunContexts;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.server.session.ServerSessionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmailImapImportJob implements IPlatformListener {

    Logger LOG = LoggerFactory.getLogger(getClass());

    private Folder folder;
    private Store store;

    @Override
    public void stateChanged(PlatformEvent event) {
        if (event.getState() == IPlatform.State.BeanManagerValid) {
            Jobs.schedule(
                    this::run,
                    Jobs.newInput()
                            .withName("Ticket IMAP import job")
                            .withRunContext(createServerJobContext())
                            .withExecutionTrigger(Jobs.newExecutionTrigger().withStartIn(5, TimeUnit.SECONDS).withSchedule(FixedDelayScheduleBuilder.repeatForever(1, TimeUnit.MINUTES)))
                            .withExceptionHandling(new ExceptionHandler() {
                                @Override
                                public void handle(Throwable t) {
                                    LOG.error("Exception in Ticket IMAP import job.", t);
                                }
                            }, true));
        }
    }

    private ServerRunContext createServerJobContext() {
        Subject s = new Subject();
        s.getPrincipals().add(new Principal() {
            @Override
            public String getName() {
                return "testing";
            }
        });
        s.setReadOnly();


        ServerRunContext serverRunContext = ServerRunContexts.empty();

        IServerSession session = BEANS.get(ServerSessionProvider.class).provide(serverRunContext.copy());
        ServerSession serverSession = (ServerSession) session;
        serverSession.setCurrentOrganisation(new Organisation());

        serverRunContext.withSubject(s);
        serverRunContext.withSession(session);

        return serverRunContext;
    }

    private void run() {
        //if(BEANS.get(DatabaseDao.class).isProduction()) return;

        List<TicketDepartment> ticketDepartments = fetchTicketDepartments();

        if (CollectionUtility.isEmpty(ticketDepartments)) return;

        for (TicketDepartment department : ticketDepartments) {

            ServerSession.get().setCurrentOrganisation(findOrganisation(department.getOrganisationId()));

            processDepartment(department);
        }
    }

    private Organisation findOrganisation(Integer organisationId) {
        Organisation organisation = new Organisation();
        organisation.setId(organisationId);

        return organisation;
    }

    private void processDepartment(TicketDepartment ticketDepartment) {
        List<Message> messages = connectToImapAndFetchMessages(ticketDepartment);

        if (CollectionUtility.isEmpty(messages)) return;

        Integer importedCount = processEmails(messages, ticketDepartment);

        closeFolderAndStore();

        //Send event to CRM to refresh Contact Center.
        pingDepartment(ticketDepartment, importedCount);
    }

    private void closeFolderAndStore() {
        try {
            folder.close();
            store.close();

            folder = null;
            store = null;
        } catch (MessagingException e) {
            LOG.error("Error closing store or folder. Exception is: {}", e.getMessage());
        }
    }

    private void pingDepartment(TicketDepartment ticketDepartment, Integer importedCount) {
        /*ModuleActionNotification notification = new ModuleActionNotification(TicketDepartment.class, new TicketDepartment(), ChangeStatus.UPDATED, ticketDepartment.getOrganisationId());

        if (importedCount > 0) {
            String notificationText = TEXTS.get("TotalImportedEmails", importedCount.toString());
            //notification.setNotification(notificationText);
        }

        BEANS.get(ClientNotificationRegistry.class).putForAllSessions(notification);*/
    }

    private Integer processEmails(List<Message> messages, TicketDepartment ticketDepartment) {
        int countImported = 0;

        for (Message message : messages) {
            try {
                boolean imported = BEANS.get(EmailImporter.class).run(message, ticketDepartment);

                if (imported) {
                    countImported++;

                    markEmailAsProcessed(message, ticketDepartment);
                }
            } catch (EmailImporterException e) {
                System.out.println("Greška s importom: " + e.getMessage());
            }
        }

        return countImported;
    }

    private void markEmailAsProcessed(Message message, TicketDepartment ticketDepartment) {
        try {
            message.setFlag(Flags.Flag.SEEN, true);

            //Delete email from IMAP
            if (ticketDepartment.isImapImportDeletedAfter()) {
                message.setFlag(FLAGS.Flag.DELETED, true);
            }
        } catch (MessagingException e) {
            System.out.println("Greška");
        }
    }

    private List<TicketDepartment> fetchTicketDepartments() {
        BeanArrayHolder<TicketDepartment> rows = new BeanArrayHolder<>(TicketDepartment.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT td.id, ");
        varname1.append("       td.project_id, ");
        varname1.append("       td.organisation_id, ");
        varname1.append("       td.imap_import_host, ");
        varname1.append("       td.imap_import_email, ");
        varname1.append("       td.imap_import_password, ");
        varname1.append("       td.imap_import_encryption, ");
        varname1.append("       td.imap_import_folder, ");
        varname1.append("       td.imap_import_deleted_after ");
        varname1.append("FROM   ticket_departments td ");
        varname1.append("WHERE  td.deleted_at IS NULL ");
        varname1.append("AND    td.active IS true ");
        varname1.append("AND    td.imap_import_enabled IS true ");
        varname1.append("INTO   :{rows.id}, ");
        varname1.append("       :{rows.projectId}, ");
        varname1.append("       :{rows.organisationId}, ");
        varname1.append("       :{rows.imapImportHost}, ");
        varname1.append("       :{rows.imapImportEmail}, ");
        varname1.append("       :{rows.imapImportPassword}, ");
        varname1.append("       :{rows.imapImportEncryption}, ");
        varname1.append("       :{rows.imapImportFolder}, ");
        varname1.append("       :{rows.imapImportDeletedAfter}");
        SQL.selectInto(varname1.toString(), new NVPair("rows", rows));

        return CollectionUtility.arrayList(rows.getBeans());
    }

    private ImapServerConfig createImapServerConfig(TicketDepartment ticketDepartment) {
        ImapServerConfig config = BEANS.get(ImapServerConfig.class);
        config.withHost(ticketDepartment.getImapImportHost())
                .withUsername(ticketDepartment.getImapImportEmail())
                .withPassword(ticketDepartment.getImapImportPassword())
                .withUseSsl(ticketDepartment.isSslEnabled());

        return config;
    }

    private List<Message> connectToImapAndFetchMessages(TicketDepartment ticketDepartment) {
        try {
            ImapServerConfig config = createImapServerConfig(ticketDepartment);

            store = BEANS.get(ImapHelper.class).connect(config);
            folder = store.getFolder(ticketDepartment.getImapImportFolder());

            folder.open(Folder.READ_WRITE);

            Message[] messages = folder.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            Arrays.sort(messages, (m1, m2) -> {
                try {
                    return m2.getSentDate().compareTo(m1.getSentDate());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });

            return List.of(messages);
        } catch (MessagingException e) {
            LOG.error("Error fetching messages from IMAP. {}", e.getMessage());

            return CollectionUtility.emptyArrayList();
        }
    }


}
