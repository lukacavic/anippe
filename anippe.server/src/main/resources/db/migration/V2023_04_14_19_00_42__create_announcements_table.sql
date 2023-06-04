CREATE TABLE public.announcements (
    id serial primary key,
    subject character varying not null,
    content character varying not null,
    user_id integer not null,
    show_to_users boolean default false,
    show_to_clients boolean default false,
    show_my_name boolean default false,
    created_at timestamp without time zone default now(),
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    organisation_id integer
);

CREATE TABLE public.link_dismissed_announcements (
    id serial primary key,
    announcement_id integer not null,
    user_id integer,
    contact_id integer,
    created_at timestamp without time zone default now()
);