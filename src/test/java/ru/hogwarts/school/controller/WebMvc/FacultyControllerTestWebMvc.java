package ru.hogwarts.school.controller.WebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FacultyController.class)
public class FacultyControllerTestWebMvc {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @MockBean
    private FacultyServiceImpl facultyService;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @InjectMocks
    private StudentController studentController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateStudent() throws Exception {

        //given
        Student student = new Student();
        student.setName("Peter");
        student.setAge(22);
        when(studentService.addStudent(student)).thenReturn(student);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student))
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void shouldGetStudentByIdTest() throws Exception {
        final long id = 1;
        final String name = "Ron";
        final int age = 17;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/" + id)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andDo(print());

    }

    @Test
    void shouldDeleteStudent() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void shouldBeUpdateStudentsTest() throws Exception {
        Student student = new Student(1L, "Rom", 22);
        Long id = student.getId();
        when(studentService.updateStudent(id, student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void shouldUpdateStudentTest() throws Exception {
        final long id = 1;
        Student student = new Student(id, "Malfoy", 15);

        when(studentService.updateStudent(id, student)).thenReturn(student);
        //when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        // when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.put("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

    }

    @Test
    void updateStudentTest() throws Exception {
        Long id = 1L;
        String name = "Peter";
        int age = 22;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.updateStudent(id, student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/students/", +id)
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andDo(print());
    }


    @Test
    void shouldReturnFaculty() throws Exception {
        Long id = 1L;
        Faculty faculty = new Faculty(1L, "Griffindor", "red");

        Student s = new Student(1L, "test", 15);
        s.setFaculty(faculty);
        when(studentRepository.findById(id)).thenReturn(Optional.of(s));
        mockMvc.perform(MockMvcRequestBuilders.get("/students/" + id + "/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());

    }


}