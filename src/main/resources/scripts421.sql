alter table Student
add constraint age_constraint check (age>16),
alter column age set default 20;

alter table Student
add constraint name_unique unique (name);

alter table Student
alter column name set not null;

alter table Faculty
add constraint faculty_unique unique (name, color);