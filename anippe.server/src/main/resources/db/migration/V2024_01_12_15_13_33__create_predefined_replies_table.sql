create table predefined_replies
(
    id              bigserial
        primary key,
    title           varchar,
    content         varchar,
    user_id         integer,
    project_id      integer,
    created_at      timestamp,
    updated_at      timestamp,
    deleted_at      timestamp,
    organisation_id integer not null
);
