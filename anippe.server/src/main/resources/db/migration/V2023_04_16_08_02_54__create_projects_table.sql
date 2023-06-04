CREATE TABLE public.projects (
    id serial primary key,
    name character varying,
    organisation_id integer,
    created_at timestamp without time zone default now(),
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    type_id integer,
    description character varying,
    start_at date,
    deadline_at date,
    client_id integer,
    status_id integer
);