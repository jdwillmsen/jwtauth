# Script for setting up/updating jwtauth-postgres (docker) database
version=0.0.0

docker pull postgres:latest

docker run \
  --name jwtauth-postgres-builder \
  -e POSTGRES_PASSWORD=default_password \
  -e PGDATA=/var/lib/postgresql/pgdata \
  -p 5432:5432 \
  -d postgres:latest

RETRIES=10
until docker exec jwtauth-postgres-builder psql -U postgres -d postgres -c "select 1" > /dev/null 2>&1 || [ $RETRIES -eq 0 ]; do
  echo "Waiting for postgres server, $((RETRIES--)) remaining attempts..."
  sleep 1
done

docker exec -i \
jwtauth-postgres-builder \
psql -U postgres -d postgres <<'EOF'
\x
create database jdw;
\c jdw

drop database if exists postgres;
drop schema if exists public;

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

create user jdw_non_sup with password 'default_password' valid until 'infinity';
create group group_jdw_ro with user jdw_non_sup;
grant connect on database jdw to group_jdw_ro;
grant usage on schema jwtauth to group_jdw_ro;
grant select on all tables in schema jwtauth to group_jdw_ro;
alter default privileges in schema jwtauth grant select on tables to group_jdw_ro;

create user jdw_non_rw with password 'default_password' valid until 'infinity';
create group group_jdw_rw with user jdw_non_rw;
grant connect on database jdw to group_jdw_rw;
grant usage on schema jwtauth to group_jdw_rw;
grant select, insert, update, delete on all tables in schema jwtauth to group_jdw_rw;
alter default privileges in schema jwtauth grant select, insert, update, delete on tables to group_jdw_rw;

insert into jdw.jwtauth.users (first_name, last_name, email_address, password, created_by_user_id, created_time, modified_by_user_id, modified_time)
values ('jwtauth', 'system', 'jwtauth.system@jdw.com', '$2a$10$x65/IK3i4Bdt6Mpw6WvVrO1xbYhpJyhBNezk7SXsNJ5Ujpe3qj/Pi', 1, now(), 1, now());

insert into jdw.jwtauth.roles (role_name, role_description, active, created_by_user_id, created_time, modified_by_user_id, modified_time)
values ('ADMIN', 'An administrator role', true, 1, now(), 1, now());

insert into jdw.jwtauth.users_roles (user_id, role_id, created_by_user_id, created_time)
values (1, 1, 1, now())
EOF

docker commit \
  -a "Jake Willmsen <jdwillmsen@gmail.com>" \
  -m "Added jdw jwtauth schema and data" \
  "$(docker ps -a -q -f name=jwtauth-postgres-builder)" \
  jdwillmsen/jwtauth-postgres:${version}

docker tag jdwillmsen/jwtauth-postgres:${version} jdwillmsen/jwtauth-postgres:latest

docker push jdwillmsen/jwtauth-postgres:${version}
docker push jdwillmsen/jwtauth-postgres:latest

docker rm -f jwtauth-postgres-builder
