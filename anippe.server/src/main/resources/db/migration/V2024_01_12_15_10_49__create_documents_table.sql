create table documents
(
    id                serial
        primary key,
    folder_id         integer,
    user_id           integer,
    file_name         varchar,
    file_size         integer,
    file_type         varchar,
    file_extension    varchar,
    description       varchar,
    created_at        timestamp,
    updated_at        timestamp,
    deleted_at        timestamp,
    name              varchar,
    organisation_id   integer,
    absolute_path     varchar,
    relative_path     varchar,
    full_path         varchar,
    related_id        integer,
    related_type      smallint,
    file_name_on_disk varchar
);
