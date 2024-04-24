package ru.hogwarts.school.Anna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.Anna.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Collection<Faculty> findAllByColorIgnoreCaseOrNameIgnoreCase (String color, String name);


}
