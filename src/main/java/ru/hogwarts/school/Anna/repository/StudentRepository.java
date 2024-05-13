package ru.hogwarts.school.Anna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.Anna.model.Student;

import java.util.Collection;
@Repository
public interface StudentRepository  extends JpaRepository<Student, Long> {
    Collection<Student> findAllByAgeBetween (Integer min, Integer max);
    Collection<Student> findAllByFaculty_Id (Long facultyId);
}
