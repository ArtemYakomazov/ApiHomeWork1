package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;
    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoadsTest() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void createFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Гриффиндор");
        faculty.setColor("красный");

        ResponseEntity<Faculty> faculties = this.restTemplate
                .postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        Assertions.assertThat(faculties.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(faculties.getBody().getName()).isEqualTo(faculty.getName());
    }

    @Test
    void readFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Гриффиндор");
        faculty.setColor("красный");

        ResponseEntity<Faculty> faculties = this.restTemplate
                .getForEntity("http://localhost:" + port + "/faculty", Faculty.class);
        Assertions.assertThat(faculties.getBody()).isEqualTo(faculty);
    }

    @Test
    void deleteFaculty() {
        Faculty faculty = new Faculty();

        faculty.setName("Грифф");
        faculty.setColor("черный");
        facultyController.createFaculty(faculty);

        restTemplate.delete("http://localhost:" + port + "/faculty" + faculty.getId());

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty",
                String.class).isEmpty());

        facultyController.deleteFaculty(faculty.getId());
    }

    @Test
    void updateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Griff");
        faculty.setColor("white");

        facultyRepository.save(faculty);

        Faculty faculty1 = new Faculty();
        faculty1.setId(faculty.getId());
        faculty1.setName("Гриффиндор");
        faculty1.setColor("красный");

        ResponseEntity<Faculty> response = restTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.PUT, new HttpEntity<>(faculty1), Faculty.class);

        Assertions
                .assertThat(response.getStatusCode().is2xxSuccessful());

        facultyController.deleteFaculty(faculty.getId());
        facultyController.deleteFaculty(faculty1.getId());
    }

    @Test
    void testFindFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Гриффиндор");
        faculty.setColor("красный");
        facultyController.createFaculty(faculty);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/color/" + faculty.getColor(),
                String.class)).isNotNull();
        assertThat(faculty.getColor()).isEqualTo("красный");

        facultyController.deleteFaculty(faculty.getId());
    }

    @Test
    void getAllFaculties() {

        ResponseEntity<List<Faculty>> actual = restTemplate.exchange("http://localhost:" + port + "/faculty/all",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Faculty>>() {
                });

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getFacultyWithStudents() {
        Faculty faculty = new Faculty();
        faculty.setName("Гриффиндор");
        faculty.setColor("красный");
        facultyController.createFaculty(faculty);

        Student student = new Student();
        student.setName("Иван");
        student.setAge(15);
        student.setFaculty(faculty);


        ResponseEntity<List<Faculty>> actual = restTemplate
                .exchange("http://localhost:" + port + "/faculty/facultyStudents/" + faculty.getId(),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Faculty>>() {
                        });

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);

        facultyController.deleteFaculty(faculty.getId());
    }
}