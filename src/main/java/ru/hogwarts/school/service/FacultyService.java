package ru.hogwarts.school.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    Faculty findFaculty(long id);
    Faculty updateFaculty(Faculty faculty);
    void deleteFaculty(long id);

    List<Faculty> getFacultiesByColor(String color);

    List<Faculty> getFacultiesByColorOrName(String color, String name);
    List<Student> getStudents(Long id);
}
