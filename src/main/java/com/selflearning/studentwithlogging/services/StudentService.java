package com.selflearning.studentwithlogging.services;

import com.selflearning.studentwithlogging.entities.Student;
import com.selflearning.studentwithlogging.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired private StudentRepository studentRepository;

    public Student addStudent(Student student){
        studentRepository.save(student);
        return student;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudent(Long id){
        return studentRepository.findById(id);
    }
}
