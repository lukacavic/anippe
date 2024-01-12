CREATE TABLE tickets (
    id serial primary key,
    subject character varying,
    contact_id integer,
    code character varying,
    project_id integer,
    last_reply_at timestamp without time zone,
    status_id integer,
    priority_id integer,
    department_id integer,
    closed_at timestamp without time zone,
    assigned_user_id integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    organisation_id integer
);
