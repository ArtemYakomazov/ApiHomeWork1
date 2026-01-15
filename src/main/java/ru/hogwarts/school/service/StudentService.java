package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Primary
@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student");
        return studentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public Student editeStudent(Student student) {
        logger.info("Was invoked method for edite student");
        return studentRepository.save(student);
    }


    public Collection<Student> findAllByAge(Integer age) {
        logger.info("Was invoked method for find students by age");
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for output all students");
        return studentRepository.findAll();
    }

    public Collection<Student> findAllByAgeBetween(Integer from, Integer to) {
        logger.info("Was invoked method for find students by age between");
        return studentRepository.findByAgeBetween(from, to);
    }

    public Integer numberOfStudentInUniversity() {
        logger.info("Was invoked method for determining the number of students in a university");
        return studentRepository.numberOfStudentsInUniversity();
    }

    public Integer avgAgeOfStudents() {
        logger.info("Was invoked method for determining the average age of students at a university");
        return studentRepository.avgAgeOfStudents();
    }

    public List<Student> getFiveLastStudents() {
        logger.info("Was invoked method for output five last students");
        return studentRepository.getFiveLastStudents();
    }
}