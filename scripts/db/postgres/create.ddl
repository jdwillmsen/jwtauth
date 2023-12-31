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

create table if not exists jdw.jwtauth.tokens
(
    token_id               bigint           not null generated always as identity,
    user_id                bigint           not null,
    token                  text             not null,
    active                 boolean          not null default true,
    created_time           timestamptz      not null,
    expired_time           timestamptz      not null,
    modified_time          timestamptz      not null,
    primary key (token_id),
    constraint fk_user foreign key (user_id) references jwtauth.users
);

-- Read Only
create user jdw_non_sup with password 'default_password' valid until 'infinity';
create group group_jdw_ro with user jdw_non_sup;
grant connect on database jdw to group_jdw_ro;
grant usage on schema jwtauth to group_jdw_ro;
grant select on all tables in schema jwtauth to group_jdw_ro;
alter default privileges in schema jwtauth grant select on tables to group_jdw_ro;

-- Read Write
create user jdw_non_rw with password 'default_password' valid until 'infinity';
create group group_jdw_rw with user jdw_non_rw;
grant connect on database jdw to group_jdw_rw;
grant usage on schema jwtauth to group_jdw_rw;
grant select, insert, update, delete on all tables in schema jwtauth to group_jdw_rw;
alter default privileges in schema jwtauth grant select, insert, update, delete on tables to group_jdw_rw;
