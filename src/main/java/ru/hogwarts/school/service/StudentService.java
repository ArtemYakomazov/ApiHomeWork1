package ru.hogwarts.school.service;

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


    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student editeStudent(Student student) {
        return studentRepository.save(student);
    }


    public Collection<Student> findAllByAge(Integer age) {
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findAllByAgeBetween(Integer from, Integer to) {
        return studentRepository.findByAgeBetween(from, to);
    }

    public Integer numberOfStudentInUniversity() {
        return studentRepository.numberOfStudentsInUniversity();
    }

    public Integer avgAgeOfStudents() {
        return studentRepository.avgAgeOfStudents();
    }

    public List<Student> getFiveLastStudents() {
        return studentRepository.getFiveLastStudents();
    }
}