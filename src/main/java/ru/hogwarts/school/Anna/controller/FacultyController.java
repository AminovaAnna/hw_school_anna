package ru.hogwarts.school.Anna.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Anna.model.Faculty;
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
    public Faculty getFacultyById(@RequestParam long id){
        return service.getFacultyById(id);
    }
    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty){
        return service.addFaculty(faculty);
    }
    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty){
        return service.updateFaculty(faculty);
    }
    @DeleteMapping
    public boolean deleteFaculty(@RequestParam long id){
        return service.deleteFaculty(id);
    }

@GetMapping (path = "/byColor")
    public Collection<Faculty> getByColor (@RequestParam String color){
        return service.getByColor(color);
}

}
