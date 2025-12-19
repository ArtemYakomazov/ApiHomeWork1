package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.Collection;
import java.util.Collections;

@RequestMapping("student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> readStudent(@PathVariable long id) {
        Student student = studentService.readStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student.getId(), student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/between")
    public Collection<Student> findByAgeBetween(@RequestParam Integer from,
                                                @RequestParam Integer to) {
        return studentService.findByAgeBetween(from, to);
    }
}
