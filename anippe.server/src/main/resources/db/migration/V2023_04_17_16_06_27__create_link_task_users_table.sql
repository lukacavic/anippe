CREATE TABLE public.link_task_users(
    id serial primary key,
    task_id integer,
    user_id integer
);