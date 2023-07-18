create table if not exists languages
(
    id          serial primary key,
    name        character varying,
    code        character varying,
    created_at  timestamp without time zone,
    deleted_at  timestamp without time zone,
    locale_code character varying
);

