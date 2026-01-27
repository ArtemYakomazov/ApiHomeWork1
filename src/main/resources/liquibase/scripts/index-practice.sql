--liquibase formated sql

--changeset artem: 1
create index student_name_index on Student (name);

--changeset artem: 2
create index name_color_faculty_index on Faculty (name, color);