package ru.hogwarts.school.Anna.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.Anna.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate template;

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student(null, "test_name1", 10);
        ResponseEntity<Student> postResponse = template.postForEntity("/student", student, Student.class);
        Student addedStudent = postResponse.getBody();

        var result = template.getForObject("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getName()).isEqualTo("test_name1");
        assertThat(result.getAge()).isEqualTo(10);
        ResponseEntity<Student> resultAfterDelete = template.exchange("/student?id=-1", HttpMethod.GET, null, Student.class);
        assertThat(resultAfterDelete.getStatusCode().value()).isEqualTo(400);
    }
}
