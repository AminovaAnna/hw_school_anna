package ru.hogwarts.school.Anna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.Anna.model.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findAllByAgeBetween(Integer min, Integer max);

    Collection<Student> findAllByFaculty_Id(Long facultyId);

    @Query(value = "select count(*) from students", nativeQuery = true)
    int countStudents();

    @Query(value="select avg(age) from students", nativeQuery = true)
    double avgAge();

    @Query(value="select * from students order by id desc limit 5", nativeQuery = true)
    Collection<Student> getLastFive();

}
