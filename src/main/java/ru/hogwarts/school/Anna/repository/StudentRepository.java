package ru.hogwarts.school.Anna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.Anna.model.Student;

import java.util.Collection;

public interface StudentRepository  extends JpaRepository<Student, Long> {
    Collection<Student> findAllByAge(int age);
}
