CREATE TABLE tickets (
    id serial primary key,
    subject character varying,
    contact_id integer,
    status_id integer,
    priority_id integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    organisation_id integer
);
