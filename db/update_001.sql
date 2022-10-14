create table person (
    id serial primary key not null,
    username varchar(2000),
    password varchar(2000)
);

insert into person (username, password) values ('parsentev', '$2a$10$UErrbEfFdNnvDMmtjbeByu0lQslllMBuUqIiB5Jn4AtBmc2smbb1O');
insert into person (username, password) values ('ban', '$2a$10$UErrbEfFdNnvDMmtjbeByu0lQslllMBuUqIiB5Jn4AtBmc2smbb1O');
insert into person (username, password) values ('ivan', '$2a$10$UErrbEfFdNnvDMmtjbeByu0lQslllMBuUqIiB5Jn4AtBmc2smbb1O');