create table person
(
  id       int          not null
    primary key AUTO_INCREMENT,
  name       varchar(255) not null,
  surname    varchar(255) not null,
  login      varchar(255) not null,
  password   varchar(255) null,
  role       varchar(255) not null,
  age      int          not null,
   UNIQUE (id),
    UNIQUE (login)
  
)