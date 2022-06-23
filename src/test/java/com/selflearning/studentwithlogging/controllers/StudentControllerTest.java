package com.selflearning.studentwithlogging.controllers;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearning.studentwithlogging.dtos.StudentDTO;
import com.selflearning.studentwithlogging.entities.Student;
import com.selflearning.studentwithlogging.repositories.StudentRepository;
import com.selflearning.studentwithlogging.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.logging.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//import java.util.logging.Logger;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class StudentControllerTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired private ModelMapper modelMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void addStudentTest() throws Exception {

        Logger logger = (Logger) LoggerFactory.getLogger(StudentController.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        
        StudentDTO studentDTO = new StudentDTO(1L, "Hemant", "Patil", 42);
        Student student = modelMapper.map(studentDTO, Student.class);
        String studentRequest = objectMapper.writeValueAsString(studentDTO);

        when(studentRepository.save(student)).thenReturn(student);

        MvcResult result = mockMvc.perform(post("/student/").content(studentRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        List<ILoggingEvent> logsList = listAppender.list;
        String loggedMessage = logsList.get(1).getMessage();
        System.out.println("####################################################################################");
        System.out.println("####################################################################################");
        System.out.println("Logged Messages : " + loggedMessage);
        System.out.println("####################################################################################");
        System.out.println("####################################################################################");
        StudentDTO studentActual = objectMapper.readValue(result.getResponse().getContentAsString(), StudentDTO.class);
//        ResponseEntity<StudentDTO> studentExpected = new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.CREATED);
        assertEquals(studentDTO.getFname(),
                studentActual.getFname());
//        Assertions.assertTrue(studentDTO.equals(studentActual));
    }
}