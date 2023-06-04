CREATE TABLE public.client_groups (
    id serial primary key,
    name character varying,
    organisation_id integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone
);