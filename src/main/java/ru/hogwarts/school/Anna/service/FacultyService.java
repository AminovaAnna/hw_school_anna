package ru.hogwarts.school.Anna.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Anna.exceptions.RecordNotFoundException;
import ru.hogwarts.school.Anna.model.Faculty;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.repository.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class FacultyService {
    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty addFaculty(Faculty faculty){
    return repository.save(faculty);
    }
    public Faculty getFacultyById(long id){
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public Faculty updateFaculty(Faculty faculty){
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }
    public boolean deleteFaculty(long id){
        return repository.findById(id)
                        .map(entity -> {
                            repository.delete(entity);
                            return true;
                        }).orElse(false);
    }

    public Collection<Faculty> getByColor(String color) {
        return repository.findAllByColor(color);
    }
}

