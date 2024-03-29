CREATE TABLE leads
(
    id               serial primary key,
    name             character varying,
    company          character varying,
    description      character varying,
    address          character varying,
    city             character varying,
    postal_code      character varying,
    country_id       integer,
    project_id       integer,
    last_contact_at  timestamp without time zone,
    status_id        integer,
    source_id        integer,
    assigned_user_id integer,
    email            character varying,
    website          character varying,
    phone            character varying,
    lost             boolean default false,
    junk             boolean default false,
    created_at       timestamp without time zone,
    updated_at       timestamp without time zone,
    deleted_at       timestamp without time zone,
    organisation_id  integer
);
