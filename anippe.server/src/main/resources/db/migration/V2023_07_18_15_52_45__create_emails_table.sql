create table emails
(
    id              serial primary key,
    client_id       integer,
    organisation_id integer,
    created_at      timestamp without time zone default now(),
    updated_at      timestamp without time zone,
    deleted_at      timestamp without time zone,
    subject         character varying,
    message         text,
    sender_name     character varying,
    sender_email    character varying,
    user_id         integer,
    receivers       character varying,
    cc_receivers    character varying,
    bcc_receivers   character varying,
    status_id       smallint,
    error_message   character varying,
    reminder_id     integer,
    related_id      integer,
    related_type    integer
);
