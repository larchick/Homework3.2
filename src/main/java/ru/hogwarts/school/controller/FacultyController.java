package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id){
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        Faculty savedFaculty = facultyService.updateFaculty(id, faculty);
        if (savedFaculty == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(savedFaculty);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-color")
    public List<Faculty> getFacultiesByColor(@RequestParam String color){
        return facultyService.getFacultiesByColor(color);
    }

    @GetMapping("/by-color-or-name")
    public List<Faculty> getFacultiesByColorOrName(@RequestParam String color, @RequestParam String name){
        return facultyService.getFacultiesByColorOrName(color, name);
    }

    @GetMapping("/{id}/student")
    public List<Student> getStudents(@PathVariable Long id){

        return facultyService.getStudents(id);
    }



}
