package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;
@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private long count = 0;

    public Student createStudent(Student student) {
        student.setId(count++);
        students.put(student.getId(), student);
        return student;
    }

    public Student readStudentById (long id) {
        return students.get(id);
    }

    public Student updateStudent(long id, Student student) {
        students.put(id, student);
        return student;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }
}
