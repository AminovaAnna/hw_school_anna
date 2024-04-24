package ru.hogwarts.school.Anna.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Anna.model.Faculty;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping
    public Faculty getFacultyById(@RequestParam long id) {
        return service.getFacultyById(id);
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return service.addFaculty(faculty);
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return service.updateFaculty(faculty);
    }

    @DeleteMapping
    public boolean deleteFaculty(@RequestParam long id) {
        return service.deleteFaculty(id);
    }

    @GetMapping(path = "/byColorOrName")
    public Collection<Faculty> getByColor(@RequestParam(required = false) String color,
                                          @RequestParam(required = false) String name) {
        if (color == null && name == null) {
            return service.getAll();
        }
        return service.getByColorOrName(color, name);
    }

    @GetMapping("/allStudents")
    public Collection<Student> getAllStudentsByFacultyId(@RequestParam long facultyId) {
        return service.getAllStudentsByFacultyId(facultyId);
    }

}
