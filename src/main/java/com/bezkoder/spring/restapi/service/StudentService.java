package com.bezkoder.spring.restapi.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bezkoder.spring.restapi.model.Student;

@Service
public class StudentService {

  static List<Student> students = new ArrayList<Student>();
  static long id = 0;

  public Student findById(long id) {
    return students.stream().filter(s -> id == s.getId()).findAny().orElse(null);
  }

  public Student save(Student student) {
    // update Student
    if (student.getId() != 0) {
      long _id = student.getId();

      for (int idx = 0; idx < students.size(); idx++)
        if (_id == students.get(idx).getId()) {
          students.set(idx, student);
          break;
        }

      return student;
    }

    // create new Student
    student.setId(++id);
    students.add(student);
    return student;
  }

  public List<Student> getStudentsBetweenAge(int from, int to) {
    return students.stream().filter(s -> s.getAge() >= from && s.getAge() <= to ).toList();
  }

  public Student updateAge(Student s1) {
    if(s1.getId() > 0) {
      int date = s1.getDateOfBirth();
      int month = s1.getMonthOfBirth();
      int year = s1.getYearOfBirth();
      try {
        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(year, month, date);
        int agenum = Period.between(dateOfBirth, today).getYears();
        s1.setAge(agenum);
      } catch(Exception e) {
        s1.setAge(0);
      }
      
    }
    return s1;
  }
}
