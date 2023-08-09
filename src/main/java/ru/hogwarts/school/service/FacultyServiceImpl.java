package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.List;

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
    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
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
        facultyRepository.deleteById(id);
    }
    @Override
    public List<Faculty> getFacultiesByColor(String color){
        return facultyRepository.findByColor(color);
    }

    @Override
    public List<Faculty> getFacultiesByColorOrName(String color, String name) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    @Override
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public List<Student> getStudents(Long id) {
        return facultyRepository.findById(id)
                .map(Faculty:: getStudents)
                .orElse(null);
    }


}
