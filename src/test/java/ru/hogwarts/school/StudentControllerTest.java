package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoadsTest() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void contextLoadsFacultyTest() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void createStudentTest() throws Exception {

        Student student = new Student();
        student.setId(1);
        student.setName("Иван");
        student.setAge(15);

        ResponseEntity<Student> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/student", student, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void readStudent() {

        Student student = new Student();
        student.setName("Иван");
        student.setAge(15);
        ResponseEntity<Student> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/student", Student.class);
        Assertions.assertThat(response.getBody()).isEqualTo(student);
    }

    @Test
    void deleteStudentTest() {

        Student student = new Student();
        student.setName("Иван");
        student.setAge(15);
        studentController.createStudent(student);

        restTemplate.delete("http://localhost:" + port + "/student" + student.getId());

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student",
                String.class).isEmpty());

        studentController.deleteStudent(student.getId());
    }

    @Test
    void editeStudentTest() {
        Student student = new Student();
        student.setAge(12);
        student.setName("Петр");

        studentController.createStudent(student);
        Student student2 = new Student();
        student2.setId(student.getId());
        student2.setAge(15);
        student2.setName("Иван");


        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.PUT, new HttpEntity<>(student2), Student.class);

        Assertions
                .assertThat(response.getStatusCode().is2xxSuccessful());

        studentController.deleteStudent(student2.getId());
        studentController.deleteStudent(student.getId());
    }

    @Test
    void findByAgeTest() {
        Student student = new Student();
        student.setName("Иван");
        student.setAge(15);

        List<Student> students = new ArrayList<>();
        int age = 15;

        studentController.createStudent(student);

        ResponseEntity<List<Student>> actual = restTemplate.exchange("http://localhost:" + port + "/student/age?age=" + age,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);

        studentController.deleteStudent(student.getId());
    }

    @Test
    void getAllStudents() {

        ResponseEntity<List<Student>> actual = restTemplate.exchange("http://localhost:" + port + "/student/all",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findByAgeBetweenTest() {
        Student student1 = new Student();
        student1.setName("Петр");
        student1.setAge(12);
        Student student2 = new Student();
        student2.setName("Кирилл");
        student2.setAge(17);
        Student student3 = new Student();
        student3.setName("Андрей");
        student3.setAge(21);
        studentController.createStudent(student1);
        studentController.createStudent(student2);
        studentController.createStudent(student3);


        ResponseEntity<List<Student>> actual = restTemplate
                .exchange("http://localhost:" + port + "/student/between?from=13&to=17",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Student>>() {
                        });

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        studentController.deleteStudent(student1.getId());
        studentController.deleteStudent(student2.getId());
        studentController.deleteStudent(student3.getId());
    }

    @Test
    void getStudentWithFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Гриффиндор");
        faculty.setColor("красный");
        facultyController.createFaculty(faculty);

        Student student = new Student();
        student.setName("Иван");
        student.setAge(15);
        student.setFaculty(faculty);
        studentController.createStudent(student);

        ResponseEntity <Faculty> actual = restTemplate
                .getForEntity("http://localhost:" + port + "/student/studentFaculty/" + student.getId(),
                        Faculty.class);

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
