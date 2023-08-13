package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;

import static org.apache.logging.log4j.ThreadContext.get;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Override
    public Student addStudent(Student student){
        return studentRepository.save(student);
    }
    @Override
    public Student findStudent(long id){
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student savedStudent = findStudent(id);
        if (savedStudent == null) {
            return null;
        }
        savedStudent.setName(student.getName());
        savedStudent.setAge(student.getAge());
        return studentRepository.save(savedStudent);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentByAge(int age){
        return studentRepository.findByAge(age);
    }

    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyByStudent(Long id) {
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElse(null);
    }

    @Override
    public int getNumberOfStudents() {
        return studentRepository.getNumberOfStudents();
    }

    @Override
    public int getAverageAgeStudents() {
        return studentRepository.getAverageAgeStudents();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }


}
