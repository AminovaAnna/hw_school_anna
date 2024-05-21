package ru.hogwarts.school.Anna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.Anna.model.Faculty;
import ru.hogwarts.school.Anna.model.Student;
import ru.hogwarts.school.Anna.repository.FacultyRepository;
import ru.hogwarts.school.Anna.repository.StudentRepository;
import ru.hogwarts.school.Anna.service.AvatarService;
import ru.hogwarts.school.Anna.service.FacultyService;
import ru.hogwarts.school.Anna.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest

public class StudentControllerTestWebMvc {
    @Autowired
    MockMvc mvc;
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    StudentService studentService;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    AvatarService avatarService;
    @MockBean
    FacultyService facultyService;

    @InjectMocks
    StudentController controller;

    @Test
    void testGet() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(null, "test_student_mvc", 10)));
        mvc.perform(MockMvcRequestBuilders.get("/student?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test_student_mvc"))
                .andExpect(jsonPath("$.age").value(10));
    }

    @Test
    void testUpdate() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_student", 10)));
        Student student = new Student(1L, "test_student2", 19);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(MockMvcRequestBuilders.put("/student?id=1") // не работает
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test_student2"))
                .andExpect(jsonPath("$.age").value(19));
    }

    @Test
    void testDelete() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_student", 10)));

        mvc.perform(MockMvcRequestBuilders.delete("/student?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        when(studentRepository.findById(11111L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.delete("/student?id=11111"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void testAdd() throws Exception {
        when(studentRepository.save(any(Student.class))).then(new Answer<Student>() {
            @Override
            public Student answer(InvocationOnMock invocationOnMock) throws Throwable {
                Student input = invocationOnMock.getArgument(0, Student.class);
                Student s = new Student();
                s.setId(15L);
                s.setName(input.getName());
                s.setAge(input.getAge());
                return s;
            }
        });
        Student student = new Student(null, "name_name", 10);
        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(MockMvcRequestBuilders.post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(15L))
                .andExpect(jsonPath("$.name").value("name_name"))
                .andExpect(jsonPath("$.age").value(10));
    }

    @Test
    void testGetByAge() throws Exception {
        when(studentRepository.findAllByAgeBetween(anyInt(), anyInt()))
                .thenReturn(List.of(
                        new Student(1L, "name1", 10),
                        new Student(2L, "name2", 12),
                        new Student(3L, "name3", 18)));

        mvc.perform(MockMvcRequestBuilders.get("/student/byAge?min=11&max=15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].age").value(12));

    }

    @Test
    void getFacultyByStudent() throws Exception {
        Faculty f = new Faculty(1L, "test_faculty", "test_color");
        Student s = new Student(1L, "name1", 10);
        s.setFaculty(f);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));
        mvc.perform(MockMvcRequestBuilders.get("/student/facultyByStudent?studentId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test_faculty"))
                .andExpect(jsonPath("$.color").value("test_color"));

        mvc.perform(MockMvcRequestBuilders.get("/student/facultyByStudent?studentId="))
                .andExpect(status().is(400));
    }

}












