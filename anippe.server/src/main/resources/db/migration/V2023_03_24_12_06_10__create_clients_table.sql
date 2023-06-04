CREATE TABLE clients (
    id serial primary key,
    name character varying,
    address character varying,
    city character varying,
    postal_code character varying,
    country_id integer,
    primary_contact_id integer,
    website character varying,
    phone character varying,
    active boolean default true,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    organisation_id integer
);
