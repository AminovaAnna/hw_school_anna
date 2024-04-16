package ru.hogwarts.school.Anna.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Anna.model.Faculty;
import ru.hogwarts.school.Anna.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static long nextId = 1;

    public Faculty addFaculty(Faculty faculty){
        faculty.setId(nextId++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }
    public Faculty getFacultyById(long id){
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty){
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }
    public boolean deleteFaculty(long id){
        return faculties.remove(id) != null;
    }

    public Collection<Faculty> getByColor(String color) {
        return faculties.values()
                .stream()
                .filter(f->f.getColor().equals(color))
                .toList();
    }
}

