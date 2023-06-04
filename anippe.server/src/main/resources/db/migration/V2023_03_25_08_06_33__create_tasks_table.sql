CREATE TABLE public.tasks (
    id serial primary key,
    name character varying,
    description character varying,
    user_id integer,
    related_id integer,
    related_type integer,
    priority_id integer,
    status_id integer,
    start_at timestamp without time zone,
    deadline_at timestamp without time zone,
    completed_at timestamp without time zone,
    created_at timestamp without time zone,
    updated_at  timestamp without time zone,
    deleted_at  timestamp without time zone,
    organisation_id integer
);
