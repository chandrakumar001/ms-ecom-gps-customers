CREATE SCHEMA customer;
CREATE SEQUENCE customer.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;
CREATE SEQUENCE hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;
CREATE table customer.customer (customer_id uuid not null, email_id varchar(255),age varchar(255), favourite_colour varchar(255), first_name varchar(255), last_name varchar(255), created_by varchar(255),creation_date timestamp,last_modified_by varchar(255),last_modified_date timestamp,action varchar(10), primary key (customer_id));

CREATE VIEW customer.customer_query_view AS
SELECT
    c.customer_id, c.email_id,c.age
    ,c.favourite_colour,c.first_name,c.last_name
    ,c.created_by,c.creation_date,c.last_modified_by
    ,c.last_modified_date,c.action
FROM customer.customer c
where c.action <> 'DELETED';