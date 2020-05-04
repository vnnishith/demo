set foreign_key_checks=0;
drop table if exists users;
create table users (user_id varchar(255) not null, user_name varchar(255), password varchar(255), email_address varchar(255),preferred_phone_number varchar(255) null,UNIQUE KEY  (user_name),UNIQUE KEY  (email_address),  primary key (user_id)) engine=InnoDB DEFAULT CHARSET=utf8;

drop table if exists phones;
create table phones (phone_id char(36),user_id char(36), phone_name varchar(255), phone_model ENUM('IPHONE','ANDROID','DESK_PHONE','SOFT_PHONE'), phone_number varchar(255),UNIQUE KEY  (phone_number),  primary key (phone_id),FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE) engine=InnoDB DEFAULT CHARSET=utf8;
set foreign_key_checks=1;