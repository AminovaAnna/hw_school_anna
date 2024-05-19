package ru.hogwarts.school.Anna.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Anna.model.Faculty;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/student" )
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Student getStudentById(@RequestParam long id){
        return studentService.getStudentById(id);
    }
    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }
    @PutMapping
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }
    @DeleteMapping
    public boolean deleteStudent(@RequestParam long id){
        return studentService.deleteStudent(id);
    }
    @GetMapping(path = "/byAge")
    public Collection<Student> getByAgeBetween (@RequestParam(required = false) Integer min,
                                                @RequestParam(required = false) Integer max){
        if (min != null && max != null){
            return studentService.getByAgeBetween (min, max);
        }
        return studentService.getAll();
    }
    @GetMapping(path = "/facultyByStudent")
    public Faculty getStudentFacultyByStudent(@RequestParam long studentId){
        return studentService.getStudentById(studentId).getFaculty();
    }

    @GetMapping("/count")
    public int getStudentCount(){
        return studentService.getStudentCount();
    }

    @GetMapping("/avg-age")
    public double getAvgAge(){
        return studentService.getAvgAge();
    }

    @GetMapping("/last")
    public Collection<Student> getLastStudents(){
        return studentService.getLastFive();
    }
    @GetMapping("/nameStartsA")
    public Collection<String> getStudentsNameStartsA(){
        return studentService.getNameStartsWithA();
    }
    @GetMapping("/avg-age-stream")
    public double getAverageAgeStream(){
        return studentService.getAverageAge();
    }

}
