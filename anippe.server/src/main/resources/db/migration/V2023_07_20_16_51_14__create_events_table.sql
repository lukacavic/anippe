create table if not exists public.events(
    id bigserial primary key,
    name character varying,
    description character varying,
    user_id integer,
    organisation_id integer,
    start_at timestamp without time zone,
    ends_at timestamp without time zone,
    color character varying,
    created_at timestamp without time zone default now(),
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone
);



