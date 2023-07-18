create table if not exists email_attachments
(
    id        serial primary key,
    email_id  integer,
    content   bytea,
    file_name character varying,
    file_size character varying,
    file_type character varying
);
