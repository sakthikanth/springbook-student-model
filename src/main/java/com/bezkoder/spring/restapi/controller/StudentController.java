package com.bezkoder.spring.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.restapi.model.Book;
import com.bezkoder.spring.restapi.model.Category;
import com.bezkoder.spring.restapi.model.Student;
import com.bezkoder.spring.restapi.model.User;
import com.bezkoder.spring.restapi.model.UserReads;
import com.bezkoder.spring.restapi.repository.BookRepository;
import com.bezkoder.spring.restapi.repository.UserReadsRepository;
import com.bezkoder.spring.restapi.repository.UserRepository;
import com.bezkoder.spring.restapi.service.BookService;
import com.bezkoder.spring.restapi.service.StudentService;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class StudentController {
 
  @Autowired
  StudentService studentService;

  @Autowired
  BookService bookService;

  @Autowired
  BookRepository bookRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserReadsRepository userReadsRepository;

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

  @PostMapping("/books")
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    try {
        Book newbook = bookService.save(new Book(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getCategory()));
        bookRepository.save(newbook);
        return new ResponseEntity<>(newbook, HttpStatus.CREATED);
    } catch (Exception e) {
      return null;
    }
  }

  @PostMapping("/categories")
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    try {
        Category ct = new Category(category.getName());
        bookService.saveCategory(ct);
        return new ResponseEntity<>(ct, HttpStatus.CREATED);
    } catch (Exception e) {
      return null;
    }
  }

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getBooks(@RequestParam(name="author", required = false) String author, @RequestParam(name="category", required = false) String category) {
    try {
        List<Book> books = null;
        if(author != null) {
          books = bookService.filterbyAuthor(author);
        } else if(category != null) {
          books = bookService.filterbyCategory(category);
        } else {
          books = bookService.findAll();
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable("id") long id,@RequestBody Book bookdata) {
    try {
      Book book = bookService.findById(id);
      book.setAuthor(bookdata.getAuthor());
      book.setTitle(bookdata.getTitle());
      book.setIsbn(bookdata.getIsbn());
      bookRepository.save(book);
      return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<Book> deleteBook(@PathVariable("id") long id,@RequestBody Book bookdata) {
    try {
      Book book = bookService.findById(id);
      bookRepository.delete(book);
      return new ResponseEntity<>(book, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return null;
    }
  }

  @PutMapping("/readbook/{userId}/{id}")
  public ResponseEntity<UserReads> readBook(@PathVariable("userId") long userId, @PathVariable("id") long bookId) {
    try {
      Book book = bookService.findById(bookId);
      UserReads read = null;
      if(book.getId() > 0 && userId > 0) {

        read = bookService.getUserRead(userId, bookId);
        if(read != null) {
          read.setReadCount(read.getReadCount() + 1);
        } else {
          read = new UserReads(userId, bookId,1);
        }
        userReadsRepository.save(read);
      } else {
        System.out.println(bookId+" "+ userId);
      }
      return new ResponseEntity<>(read, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUsers(@RequestBody User userdata) {
    try {
        User user = new User(userdata.getName(), userdata.getEmail());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    } catch (Exception e) {
      return null;
    }
  }

  @GetMapping("/suggestion/{id}")
  public ResponseEntity<List<Book>> suggestBook(@PathVariable("id") long userId) {
    try {
      User user = userRepository.findById(userId).get();
      List<Book> suggested = null;
      if(user != null) {
        suggested = bookService.suggestBooksByRead();       
      }
      return new ResponseEntity<>(suggested, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


}
