package ru.hogwarts.school.Anna.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/student" )
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public Student getStudentById(@RequestParam long id){
        return service.getStudentById(id);
    }
    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return service.addStudent(student);
    }
    @PutMapping
    public Student updateStudent(@RequestBody Student student){
        return service.updateStudent(student);
    }
    @DeleteMapping
    public boolean deleteStudent(@RequestParam long id){
        return service.deleteStudent(id);
    }
    @GetMapping(path = "/byAge")
    public Collection<Student> getByAge (@RequestParam int age){
        return service.getByAge(age);
    }

}
