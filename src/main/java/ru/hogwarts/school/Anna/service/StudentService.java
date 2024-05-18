package ru.hogwarts.school.Anna.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Anna.exceptions.RecordNotFoundException;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }


    public Student addStudent(Student student) {
        logger.info("Student has been added");
        return repository.save(student);
    }

    public Student getStudentById(long id) {
        try{
            logger.info("Student has been received id=" + id);
            return repository.findById(id).orElseThrow(RecordNotFoundException::new);
        } catch (RecordNotFoundException e) {
            logger.error("There is not student with id =" + id);
            throw e;
        }
    }

    public Student updateStudent(Student student) {
        logger.info("Student has been updated");
        return repository.findById(student.getId())
                .map(entity -> repository.save(student))
                .orElse(null);
    }

    public boolean deleteStudent(long id) {
        logger.info("Student with id = {} has been deleted", id);
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                }).orElse(false);
    }

    public Collection<Student> getByAgeBetween (Integer min, Integer max) {
        logger.info("Students with ages from min={} to max={} received", min, max);
        return repository.findAllByAgeBetween(min, max);
    }

    public Collection<Student> getAll() {
        logger.info("All students received");
        return repository.findAll();
    }

    public int getStudentCount(){
        logger.info("Count of students received");
        return repository.countStudents();
    }
    public double getAvgAge(){
        logger.info("Average age of students requested");
        return repository.avgAge();
    }

    public Collection<Student> getLastFive(){
        logger.info("Last five students received");
        return repository.getLastFive();
    }
}
