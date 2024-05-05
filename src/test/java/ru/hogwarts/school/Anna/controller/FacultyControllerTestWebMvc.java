package ru.hogwarts.school.Anna.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.Anna.model.Faculty;
import ru.hogwarts.school.Anna.repository.FacultyRepository;
import ru.hogwarts.school.Anna.service.AvatarService;
import ru.hogwarts.school.Anna.service.FacultyService;
import ru.hogwarts.school.Anna.service.StudentService;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerTestWebMvc {
    @Autowired
    MockMvc mvc;
    @MockBean
    FacultyRepository facultyRepository;
    @SpyBean
    FacultyService facultyService;
    @MockBean
    AvatarService avatarService;
    @MockBean
    StudentService studentService;
    @InjectMocks
    FacultyController controller;

    @BeforeEach
    void setUp(){

    }
    @Test
    void testGet() throws Exception {
        when (facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty(1L, "test_faculty_mvc", "test_color_mvc")));
        mvc.perform(MockMvcRequestBuilders.get("/faculty?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test_faculty_mvc"))
                .andExpect(jsonPath("$.color").value("test_color_mvc"));


    }
}
