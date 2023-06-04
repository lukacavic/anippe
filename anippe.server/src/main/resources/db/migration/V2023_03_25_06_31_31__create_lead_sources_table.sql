create table lead_sources (
		id serial primary key,
		name character varying,
		created_at timestamp without time zone,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    organisation_id integer
);
