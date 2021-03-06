# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  mobile                    varchar(255),
  password                  varchar(255),
  roles                     varchar(255),
  constraint pk_account primary key (id))
;

create table feature (
  id                        bigint not null,
  name                      varchar(255),
  info                      varchar(255),
  score                     integer,
  score1                    integer,
  score2                    integer,
  score3                    integer,
  score4                    integer,
  score5                    integer,
  accuracy                  integer,
  score_values              varchar(255),
  location_id               bigint,
  constraint pk_feature primary key (id))
;

create table location (
  id                        bigint not null,
  latitude                  float,
  longitude                 float,
  name                      varchar(255),
  description               varchar(255),
  type                      integer,
  creator_id                bigint,
  constraint pk_location primary key (id))
;

create table photo (
  id                        bigint not null,
  likes                     bigint,
  photo_id                  varchar(255),
  location_id               bigint,
  constraint pk_photo primary key (id))
;

create table tag (
  id                        bigint not null,
  name                      varchar(255),
  info                      varchar(255),
  yes                       integer,
  no                        integer,
  accuracy                  integer,
  location_id               bigint,
  constraint pk_tag primary key (id))
;

create table user_update (
  id                        bigint not null,
  parent_id                 bigint,
  type                      integer,
  score                     integer,
  account_id                bigint,
  constraint pk_user_update primary key (id))
;

create sequence account_seq;

create sequence feature_seq;

create sequence location_seq;

create sequence photo_seq;

create sequence tag_seq;

create sequence user_update_seq;

alter table feature add constraint fk_feature_location_1 foreign key (location_id) references location (id) on delete restrict on update restrict;
create index ix_feature_location_1 on feature (location_id);
alter table location add constraint fk_location_creator_2 foreign key (creator_id) references account (id) on delete restrict on update restrict;
create index ix_location_creator_2 on location (creator_id);
alter table photo add constraint fk_photo_location_3 foreign key (location_id) references location (id) on delete restrict on update restrict;
create index ix_photo_location_3 on photo (location_id);
alter table tag add constraint fk_tag_location_4 foreign key (location_id) references location (id) on delete restrict on update restrict;
create index ix_tag_location_4 on tag (location_id);
alter table user_update add constraint fk_user_update_account_5 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_user_update_account_5 on user_update (account_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

drop table if exists feature;

drop table if exists location;

drop table if exists photo;

drop table if exists tag;

drop table if exists user_update;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

drop sequence if exists feature_seq;

drop sequence if exists location_seq;

drop sequence if exists photo_seq;

drop sequence if exists tag_seq;

drop sequence if exists user_update_seq;

