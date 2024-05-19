package ru.hogwarts.school.Anna.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Anna.exceptions.RecordNotFoundException;
import ru.hogwarts.school.Anna.model.Faculty;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.repository.FacultyRepository;
import ru.hogwarts.school.Anna.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final static Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository repository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Faculty has been added");
        return repository.save(faculty);
    }
    public Faculty getFacultyById(long id) {
        try {
            logger.info("Faculty has been received id=" + id);
            return repository.findById(id).orElseThrow(RecordNotFoundException::new);
        } catch (RecordNotFoundException e) {
            logger.error("There is not faculty with id =" + id);
            throw e;
        }
    }
//    public Faculty getFacultyById(long id) {
//        logger.info("Faculty has been received id=" + id);
//        logger.error("There is not faculty with id =" + id);
//        return repository.findById(id).orElseThrow(RecordNotFoundException::new);
//    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Faculty has been updated");
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    public boolean deleteFaculty(long id) {
        logger.info("Faculty with id = {} has been deleted", id);
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                }).orElse(false);
    }

    public Collection<Faculty> getByColorOrName(String color, String name) {
        logger.info ("Faculties were queried by color and name");
        return repository.findAllByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public Collection<Faculty> getAll() {
        logger.info("All faculties received");
        return repository.findAll();
    }

    public Collection<Student> getAllStudentsByFacultyId(long facultyId) {
        logger.info("Students requested by faculty");
        return studentRepository.findAllByFaculty_Id(facultyId);
    }

    public List<String> getLongestName(){
        int maxLength = repository.findAll().stream()
                .map(Faculty::getName)
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(0);

        return repository.findAll().stream()
                .map(Faculty::getName)
                .filter(name -> name.length() == maxLength)
                .collect(Collectors.toList());
    }
}

