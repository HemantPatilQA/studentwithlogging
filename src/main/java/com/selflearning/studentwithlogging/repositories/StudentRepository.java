package com.selflearning.studentwithlogging.repositories;

import com.selflearning.studentwithlogging.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
