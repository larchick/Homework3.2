package ru.hogwarts.school.controller.RestTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplate {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    public void testGetFaculties() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties", String.class))
                .isNotNull();
    }
    @Test
    public void testGetFacultyById() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/{id}", Faculty.class, params));
    }
    @Test
    public void testGetFacultyByColor() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("color", "Красный");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/by-color", Faculty.class, params));
    }
    @Test
    public void testGetStudentsByIdOfFaculty() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/studentsByID", Faculty.class, params));
    }
    @Test
    public void testGetFacultyByNameOrColor() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Гриффиндор");
        params.put("color", "Красный");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/findFaculty", String.class, params));
    }

    @Test
    public void testPostFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(5L);
        faculty.setName("Barbie");
        faculty.setColor("Розовый");

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculties", faculty, String.class))
                .isNotNull();
    }

    @Test
    public void testPutFaculty() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Гриффиндор");
        faculty.setColor("Красный");

        restTemplate.put("http://localhost:" + port + "/faculties", faculty, params);
    }
    @Test
    public void testDeleteFaculty() throws Exception {
        Map<String, String> params = new HashMap<>();

        restTemplate.put("http://localhost:" + port + "/faculties", params);
    }
}
