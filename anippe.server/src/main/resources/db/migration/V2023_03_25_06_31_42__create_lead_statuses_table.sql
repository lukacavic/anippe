create table lead_statuses (
		id serial primary key,
		name character varying,
		color character varying,
		created_at timestamp without time zone,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    organisation_id integer
);
