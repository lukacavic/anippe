create table if not exists public.link_task_followers(
    id bigserial primary key,
    user_id integer,
    task_id integer
);
