select * from students s;
select * from students where age>13 and age<17;
select name from students;
select * from students where name like '%и%';
select * from students where age<id;
select * from students order by age;
select * from students s , faculties f where s.faculty_id = f.id and f.id = 2;
select * from students s , faculties f where f.students_id =s.id and s.id =3;