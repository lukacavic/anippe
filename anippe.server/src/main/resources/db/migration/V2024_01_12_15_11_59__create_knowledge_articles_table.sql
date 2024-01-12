create table knowledge_articles
(
    id              bigserial
        primary key,
    title           varchar,
    content         text,
    user_created_id integer not null,
    category_id     integer,
    created_at      timestamp,
    updated_at      timestamp,
    deleted_at      timestamp,
    organisation_id integer not null,
    project_id      integer,
    description     varchar
);

create table knowledge_categories
(
    id              bigserial
        primary key,
    name            varchar,
    description     text,
    created_at      timestamp,
    updated_at      timestamp,
    deleted_at      timestamp,
    organisation_id integer not null,
    project_id      integer
);
