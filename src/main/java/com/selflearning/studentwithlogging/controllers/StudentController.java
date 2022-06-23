package com.selflearning.studentwithlogging.controllers;

import com.selflearning.studentwithlogging.dtos.StudentDTO;
import com.selflearning.studentwithlogging.entities.Student;
import com.selflearning.studentwithlogging.services.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired private StudentService studentService;

    @Autowired private ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO){

        logger.info("Adding Student...");
        Student studentRequest = modelMapper.map(studentDTO, Student.class);

        Student student = studentService.addStudent(studentRequest);

        StudentDTO studentResponse = modelMapper.map(student, StudentDTO.class);

        logger.info("Added Student...");

        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> studentDTOList = studentService.getAllStudents().stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable("id") Long studentId){
        Optional<Student> student = studentService.getStudent(studentId);
        StudentDTO studentDTO;
        studentDTO = student.map(value -> modelMapper.map(value, StudentDTO.class)).orElse(null);

        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }
}
