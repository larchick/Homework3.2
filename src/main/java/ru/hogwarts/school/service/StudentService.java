package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);
    Student findStudent(long id);
    Student updateStudent(Long id, Student student);
    void deleteStudent(long id);
    List<Student> getStudentByAge(int age);
    List<Student> findByAgeBetween(int min, int max);
    Faculty getFacultyByStudent(Long id);
    List<Student> getAllStudents();

}
