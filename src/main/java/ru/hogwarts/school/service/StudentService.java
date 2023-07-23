package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);
    Student findStudent(long id);
    Student updateStudent(Student student);
    void deleteStudent(long id);
    List<Student> getStudentByAge(int age);

}
