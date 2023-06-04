CREATE TABLE public.link_project_users (
    id serial primary key,
    project_id integer,
    user_id integer
);