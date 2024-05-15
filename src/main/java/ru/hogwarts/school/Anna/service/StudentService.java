package ru.hogwarts.school.Anna.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Anna.exceptions.RecordNotFoundException;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }


    public Student addStudent(Student student) {
        return repository.save(student);
    }

    public Student getStudentById(long id) {
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public Student updateStudent(Student student) {
        return repository.findById(student.getId())
                .map(entity -> repository.save(student))
                .orElse(null);
    }

    public boolean deleteStudent(long id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                }).orElse(false);
    }

    public Collection<Student> getByAgeBetween (Integer min, Integer max) {
        return repository.findAllByAgeBetween(min, max);
    }

    public Collection<Student> getAll() {
        return repository.findAll();
    }

    public int getStudentCount(){
        return repository.countStudents();
    }
    public double getAvgAge(){
        return repository.avgAge();
    }

    public Collection<Student> getLastFive(){
        return repository.getLastFive();
    }
}
