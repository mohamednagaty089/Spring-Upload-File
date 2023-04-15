
select * from Employee;
CREATE TABLE roles(
   role_id serial PRIMARY KEY,
   role_name VARCHAR (255) UNIQUE NOT NULL
);
CREATE TABLE cities (
     id serial PRIMARY KEY,
    name varchar(80)
);