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

    @Query(value = "select count(*) from Student", nativeQuery = true)
    int countStudents();

    @Query(value="select avg(age).from Student", nativeQuery = true)
    double avgAge();

    @Query(value="select * from Student order by id desk limit 5", nativeQuery = true)
    Collection<Student> getLastFive();

}
