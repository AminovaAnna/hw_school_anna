package ru.hogwarts.school.Anna.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Anna.exceptions.RecordNotFoundException;
import ru.hogwarts.school.Anna.model.Faculty;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.repository.FacultyRepository;
import ru.hogwarts.school.Anna.repository.StudentRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository repository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public Faculty getFacultyById(long id) {
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    public boolean deleteFaculty(long id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                }).orElse(false);
    }

    public Collection<Faculty> getByColorOrName(String color, String name) {
        return repository.findAllByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public Collection<Faculty> getAll() {
        return repository.findAll();
    }
    public Collection<Student> getAllStudentsByFacultyId(long facultyId){
        return studentRepository.findAllByFaculty_Id(facultyId);
    }
}

