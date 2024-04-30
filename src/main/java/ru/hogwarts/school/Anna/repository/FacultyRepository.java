package ru.hogwarts.school.Anna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.Anna.model.Faculty;

import java.util.Collection;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Collection<Faculty> findAllByColorIgnoreCaseOrNameIgnoreCase (String color, String name);


}
