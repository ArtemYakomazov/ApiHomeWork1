package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findAllByAge(Integer age);

    Collection<Student> findByAgeBetween(Integer from, Integer to);

    List<Student> getStudentsByName(String name);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    Integer numberOfStudentsInUniversity();

    @Query(value = "SELECT avg(age) FROM student", nativeQuery = true)
    Integer avgAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getFiveLastStudents();
}
