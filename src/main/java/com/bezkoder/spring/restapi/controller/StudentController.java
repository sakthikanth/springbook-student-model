package com.bezkoder.spring.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.restapi.model.Student;
import com.bezkoder.spring.restapi.service.StudentService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class StudentController {
 
  @Autowired
  StudentService studentService;

  @PostMapping("/students")
  public ResponseEntity<Student> createStudents(@RequestBody Student student) {
    try {
        Student createdStudent = studentService.save(new Student(student.getName(), student.getDateOfBirth(), student.getMonthOfBirth(), student.getYearOfBirth()));
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    } catch (Exception e) {
      return null;
    }
  }

  @PutMapping("/update-age/{id}")
  public ResponseEntity<Student> updateAge(@PathVariable("id") long id) {
    try {
      Student s = studentService.findById(id);
      return new ResponseEntity<>(studentService.updateAge(s), HttpStatus.CREATED);
    } catch (Exception e) {
      return null;
    }
  }


  @GetMapping("/students/{id}")
  public ResponseEntity<Student> getStudent(@PathVariable("id") long id) {
    try {
      Student s = studentService.findById(id);
      return new ResponseEntity<>(s, HttpStatus.OK);
    } catch (Exception e) {
      return null;
    }
  }


  @GetMapping("/students")
  public ResponseEntity<List<Student>> findStudents(@RequestParam int ageFrom, @RequestParam int ageTo) {
    try {
      List<Student> s = studentService.getStudentsBetweenAge(ageFrom, ageTo);
      return new ResponseEntity<>(s, HttpStatus.OK);
    } catch (Exception e) {
      return null;
    }
  }


}
