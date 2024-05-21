package ru.hogwarts.school.Anna.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Anna.exceptions.RecordNotFoundException;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.repository.StudentRepository;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

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
        try {
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

    public Collection<Student> getByAgeBetween(Integer min, Integer max) {
        logger.info("Students with ages from min={} to max={} received", min, max);
        return repository.findAllByAgeBetween(min, max);
    }

    public Collection<Student> getAll() {
        logger.info("All students received");
        return repository.findAll();
    }

    public int getStudentCount() {
        logger.info("Count of students received");
        return repository.countStudents();
    }

    public double getAvgAge() {
        logger.info("Average age of students requested");
        return repository.avgAge();
    }

    public Collection<Student> getLastFive() {
        logger.info("Last five students received");
        return repository.getLastFive();
    }

    public Collection<String> getNameStartsWithA() {
        return repository.findAll().stream()      //findAllByFacultyId??
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .toList();
    }

    public double getAverageAge() {
        return repository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }

    public void printParallel() {
        var students = repository.findAll();
        logger.info(students.get(0).toString());
        logger.info(students.get(1).toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                logger.info(students.get(2).toString());
                logger.info(students.get(3).toString());
            }
        }).start();

        new Thread(() -> {
            logger.info(students.get(4).toString());
            logger.info(students.get(5).toString());
        }).start();
    }

    public void printSync() {
        var students = repository.findAll();
        print(students.get(0));
        print(students.get(1));
        new Thread(() -> {
            print(students.get(2));
            print(students.get(3));
        }).start();

        new Thread(() -> {
            print(students.get(4));
            print(students.get(5));
        }).start();
    }


    private synchronized void print(Object o) {
        logger.info(o.toString());
    }

}
