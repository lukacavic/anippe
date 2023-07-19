create table if not exists public.vaults (
    id serial primary key ,
    name character varying,
    content text,
    organisation_id integer,
    user_id integer,
    related_id integer,
    related_type smallint,
    visibility_id smallint,
    created_at timestamp without time zone default now(),
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone
);
