package ru.hogwarts.school.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Array.get;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addStudentTest() throws Exception {
        Long id = 1L;
        String name = "Ron";
        int age = 25;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(id))
                .andExpect((ResultMatcher) jsonPath("$.name").value(name))
                .andExpect((ResultMatcher) jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        Long id = 1L;
        String name = "Ron";
        int age = 25;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.findStudent(any(Long.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/" + id)
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(id))
                .andExpect((ResultMatcher) jsonPath("$.name").value(name))
                .andExpect((ResultMatcher) jsonPath("$.age").value(age));
    }


    @Test
    public void deleteStudentTest() throws Exception {
        Long id = 1L;
        String name = "Ron";
        int age = 25;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/students/" + id)
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllStudentsTest() throws Exception {
        List<Student> students = new ArrayList<>();
        Long id = 1L;
        String name = "Ron";
        int age = 25;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        students.add(student);

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students")
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(students.size())));
    }

    @Test
    public void getStudentsByAgeTest() throws Exception {
        List<Student> students = new ArrayList<>();
        Long id = 1L;
        String name = "Ron";
        int age = 25;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        students.add(student);

        when(studentService.getStudentByAge(age)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/by-age/" + age)
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentBetweenAge() throws Exception {
        List<Student> students = new ArrayList<>();
        Long id = 1L;
        String name = "Ron";
        int age = 25;
        int minAge = 10;
        int maxAge = 25;


        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        students.add(student);

        when(studentService.findByAgeBetween(minAge, maxAge)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/byAgeBetween?min=10&max=12")
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(students.size())));
    }

    @Test
    public void updateStudentTest() throws Exception {
        Long id = 1L;
        String name = "Ron";
        int age = 25;

        String facultyName = "Слизерин";
        Long facultyID = 1L;
        String color = "Зеленый";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", facultyID);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(facultyID);
        faculty.setName(facultyName);
        faculty.setColor(color);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty", faculty);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);

        when(studentService.updateStudent(id, student)).thenReturn(student);

        mockMvc.perform(put("/students/update/{id}", id)
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(id))
                .andExpect((ResultMatcher) jsonPath("$.name").value(name))
                .andExpect((ResultMatcher) jsonPath("$.age").value(age));
    }

    @Test
    public void getFacultyOfStudentTest() throws Exception {
        Long id = 1L;
        String name = "Ron";
        int age = 25;

        String facultyName = "Гриффиндор";
        Long facultyID = 1L;
        String color = "Красный";

        Faculty faculty = new Faculty();
        faculty.setId(facultyID);
        faculty.setName(facultyName);
        faculty.setColor(color);


        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);

        when(studentService.getFacultyByStudent(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/getFacultyByID/{id}", id)
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(facultyID))
                .andExpect((ResultMatcher) jsonPath("$.name").value(facultyName))
                .andExpect((ResultMatcher) jsonPath("$.color").value(color));
    }

}
