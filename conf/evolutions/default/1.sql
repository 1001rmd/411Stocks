# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table history (
  id                            bigint auto_increment not null,
  portfolio_id                  bigint,
  description                   varchar(255),
  value                         double not null,
  constraint pk_history primary key (id)
);

create table leaderboard (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  owner_id                      bigint,
  starting_account              double not null,
  constraint pk_leaderboard primary key (id)
);

create table portfolio (
  id                            bigint auto_increment not null,
  user_id                       bigint,
  leaderboard_id                bigint,
  value                         double not null,
  account                       double not null,
  constraint pk_portfolio primary key (id)
);

create table stock (
  id                            bigint auto_increment not null,
  symbol                        varchar(255),
  value                         double not null,
  quantity                      double not null,
  portfolio_id                  bigint,
  constraint pk_stock primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  constraint pk_user primary key (id)
);

create index ix_history_portfolio_id on history (portfolio_id);
alter table history add constraint fk_history_portfolio_id foreign key (portfolio_id) references portfolio (id) on delete restrict on update restrict;

create index ix_leaderboard_owner_id on leaderboard (owner_id);
alter table leaderboard add constraint fk_leaderboard_owner_id foreign key (owner_id) references user (id) on delete restrict on update restrict;

create index ix_portfolio_user_id on portfolio (user_id);
alter table portfolio add constraint fk_portfolio_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_portfolio_leaderboard_id on portfolio (leaderboard_id);
alter table portfolio add constraint fk_portfolio_leaderboard_id foreign key (leaderboard_id) references leaderboard (id) on delete restrict on update restrict;

create index ix_stock_portfolio_id on stock (portfolio_id);
alter table stock add constraint fk_stock_portfolio_id foreign key (portfolio_id) references portfolio (id) on delete restrict on update restrict;


# --- !Downs

alter table history drop constraint if exists fk_history_portfolio_id;
drop index if exists ix_history_portfolio_id;

alter table leaderboard drop constraint if exists fk_leaderboard_owner_id;
drop index if exists ix_leaderboard_owner_id;

alter table portfolio drop constraint if exists fk_portfolio_user_id;
drop index if exists ix_portfolio_user_id;

alter table portfolio drop constraint if exists fk_portfolio_leaderboard_id;
drop index if exists ix_portfolio_leaderboard_id;

alter table stock drop constraint if exists fk_stock_portfolio_id;
drop index if exists ix_stock_portfolio_id;

drop table if exists history;

drop table if exists leaderboard;

drop table if exists portfolio;

drop table if exists stock;

drop table if exists user;

