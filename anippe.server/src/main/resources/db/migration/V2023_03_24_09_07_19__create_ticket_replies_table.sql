CREATE TABLE ticket_replies (
    id serial primary key,
    ticket_id integer,
    reply character varying,
    user_id integer,
    contact_id integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    organisation_id integer
);
