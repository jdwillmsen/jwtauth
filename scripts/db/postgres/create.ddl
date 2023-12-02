create database jdw;

create schema if not exists jwtauth;

create table if not exists jdw.jwtauth.users
(
    user_id                 bigint          not null generated always as identity,
    first_name              text            not null,
    last_name               text            not null,
    email_address           text            not null unique,
    password                text            not null,
    created_by_user_id      bigint          not null,
    created_time            timestamptz     not null,
    modified_by_user_id     bigint          not null,
    modified_time           timestamptz     not null,
    primary key (user_id)
);

create table if not exists jdw.jwtauth.roles
(
    role_id                 bigint          not null generated always as identity,
    role_name               text            not null,
    role_description        text            not null,
    active                  boolean         not null default true,
    created_by_user_id      bigint          not null,
    created_time            timestamptz     not null,
    modified_by_user_id     bigint          not null,
    modified_time           timestamptz     not null,
    primary key (role_id)
);

create table if not exists jdw.jwtauth.users_roles
(
    user_id                 bigint          not null,
    role_id                 bigint          not null,
    created_by_user_id      bigint          not null,
    created_time            timestamptz     not null,
    primary key (user_id, role_id),
    constraint fk_user foreign key (user_id) references jwtauth.users,
    constraint fk_role foreign key (role_id) references jwtauth.roles
);

create group group_jdw_ro;
create user jdw_non_sup with password 'default_password' valid until 'infinity';
alter group group_jdw_ro add user jdw_non_sup;
grant connect on database jdw to group_jdw_ro;
grant select on all tables in schema jwtauth to group_jdw_ro;

create group group_jdw_rw;
create user jdw_non_rw with password 'default_password' valid until 'infinity';
alter group group_jdw_rw add user jdw_non_rw;
grant connect on database jdw to group_jdw_rw;
grant select, insert, update, delete on all tables in schema jwtauth to group_jdw_rw;
