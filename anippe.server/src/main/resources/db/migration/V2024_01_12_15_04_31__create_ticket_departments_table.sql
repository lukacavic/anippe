create table if not exists  ticket_departments
(
    id                        serial primary key,
    project_id                integer,
    name                      varchar,
    active                    boolean default false,
    imap_import_enabled       boolean default false,
    imap_import_host          varchar,
    imap_import_email         varchar,
    imap_import_password      varchar,
    imap_import_encryption    varchar,
    imap_import_folder        varchar,
    imap_import_deleted_after boolean default false,
    created_at                timestamp,
    updated_at                timestamp,
    deleted_at                timestamp,
    organisation_id           integer
);
