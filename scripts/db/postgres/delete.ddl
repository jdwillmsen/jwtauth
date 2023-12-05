drop database if exists jdw;

drop schema if exists jwtauth cascade;

drop table if exists jdw.jwtauth.tokens;
drop table if exists jdw.jwtauth.users_roles;
drop table if exists jdw.jwtauth.roles;
drop table if exists jdw.jwtauth.users;