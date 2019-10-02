create schema if not exists book;
use book;

create table if not exists book (
        book_id int not null auto_increment primary key,
    title varchar(50) not null,
    author varchar(50) not null
);