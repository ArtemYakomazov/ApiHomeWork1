package ru.hogwarts.school.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FacultyControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FacultyRepository facultyRepository;

    @MockitoSpyBean
    private FacultyService facultyService;

    @Test
    public void createFacultyTest() throws Exception {
        final String name = "Гриффиндор";
        final String color = "красный";
        final long id = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name").value(name))
                .andExpect((ResultMatcher) jsonPath("$.color").value(color));

    }

    @Test
    public void readFacultyTest() throws Exception {
        final String name = "Гриффиндор";
        final String color = "красный";
        final long id = 1;

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(id))
                .andExpect((ResultMatcher) jsonPath("$.name").value(name))
                .andExpect((ResultMatcher) jsonPath("$.color").value(color));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        final long id = 1;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id))
                .andExpect(status().isOk());
        verify(facultyRepository).deleteById(id);
    }

    @Test
    public void updateFacultyTest() throws Exception {
        final String name = "Гриффиндор";
        final String color = "красный";
        final long id = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name").value(name))
                .andExpect((ResultMatcher) jsonPath("$.color").value(color));
    }

    @Test
    public void findFacultyByColorTest() throws Exception {
        final String name = "Гриффиндор";
        final String color = "красный";
        final long id = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findByColorContainsIgnoreCase(any(String.class))).thenReturn(List.of(faculty));
        when(facultyRepository.findByNameContainsIgnoreCase(any(String.class))).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color?color=red")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].name").value(name))
                .andExpect((ResultMatcher) jsonPath("$[0].color").value(color));
    }

    @Test
    public void findAllTest() throws Exception {
        final String name = "Гриффиндор";
        final String color = "красный";
        final long id = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findAll()).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].name").value(name))
                .andExpect((ResultMatcher) jsonPath("$[0].color").value(color));
    }

    @Test
    public void findStudentsByFacultyTest() throws Exception {
        final String name = "Гриффиндор";
        final String color = "красный";
        final long id = 1;

        final String sName = "Иван";
        final int age = 15;
        final long idS = 1;

        Student student = new Student();
        student.setId(idS);
        student.setName(sName);
        student.setAge(age);

        List<Student> students = new ArrayList<>();
        students.add(student);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        faculty.setStudents(students);

        when(facultyRepository.findById(any(long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/facultyStudents/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].name").value(sName))
                .andExpect((ResultMatcher) jsonPath("$[0].age").value(age));
    }

}
