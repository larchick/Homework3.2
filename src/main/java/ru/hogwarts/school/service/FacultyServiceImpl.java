package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.HashMap;
import java.util.List;

import static org.apache.logging.log4j.ThreadContext.get;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    @Override
    public Faculty addFaculty(Faculty faculty){
        return facultyRepository.save(faculty);

    }
    @Override
    public Faculty findFaculty(long id){
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty editFaculty(long id, Faculty faculty) {
        Faculty facultyFromDb = get(id);
        if (facultyFromDb == null){
            return null;
        }
        facultyFromDb.setColor(faculty.getColor());
        facultyFromDb.setName(faculty.getName());
        return facultyRepository.save(facultyFromDb);
    }

    @Override
    public void deleteFaculty(long id){
        facultyRepository.deleteById(id);
    }
    @Override
    public List<Faculty> getFacultiesByColor(String color){
        return facultyRepository.findByColor(color);
    }


}
