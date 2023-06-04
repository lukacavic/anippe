CREATE TABLE contacts (
    id serial primary key,
    first_name character varying,
    last_name character varying,
    client_id integer,
    email character varying,
    phone character varying,
    active boolean default false,
    last_login_at timestamp without time zone,
    position character varying,
    password character varying,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    organisation_id integer
);
