package ru.hogwarts.school.service;

import liquibase.pro.packaged.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.Comparator;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    public final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    @Override
    public Faculty addFaculty(Faculty faculty){
        logger.info("Method add was invoked!");
        return facultyRepository.save(faculty);

    }

    @Override
    public Faculty findFaculty(Long id) {
        logger.info("Method find was invoked!");
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.info("Method update was invoked!");
        Faculty savedFaculty = findFaculty(id);
        if (savedFaculty == null) {
            return null;
        }
        savedFaculty.setName(faculty.getName());
        savedFaculty.setColor(faculty.getColor());
        return facultyRepository.save(savedFaculty);
    }

    @Override
    public void deleteFaculty(long id){
        logger.info("Method delete was invoked!");
        facultyRepository.deleteById(id);
    }
    @Override
    public List<Faculty> getFacultiesByColor(String color){
        logger.info("Method getFacultiesByColor was invoked!");
        return facultyRepository.findByColor(color);
    }

    @Override
    public List<Faculty> getFacultiesByColorOrName(String color, String name) {
        logger.info("Method getFacultiesByColorOrName was invoked!");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    @Override
    public List<Faculty> getAllFaculties() {
        logger.info("Method getAllFaculties was invoked!");
        return facultyRepository.findAll();
    }

    @Override
    public List<Student> getStudents(Long id) {
        logger.info("Method getStudents was invoked!");
        return facultyRepository.findById(id)
                .map(Faculty:: getStudents)
                .orElse(null);
    }

    @Override
    public String getTheLongestName() {
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }


}
