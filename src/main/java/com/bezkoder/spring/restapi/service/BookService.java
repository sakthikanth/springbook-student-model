package com.bezkoder.spring.restapi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.restapi.model.Book;
import com.bezkoder.spring.restapi.model.Category;
import com.bezkoder.spring.restapi.model.UserReads;
import com.bezkoder.spring.restapi.repository.BookRepository;
import com.bezkoder.spring.restapi.repository.CategoryRepository;
import com.bezkoder.spring.restapi.repository.UserReadsRepository;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private UserReadsRepository userReadsRepository;
  
  static List<Book> books = new ArrayList<Book>();
  static List<Category> categories = new ArrayList<Category>();

  static long id = 0;

  public Book findById(long id) {
    return bookRepository.findAll().stream().filter(s -> id == s.getId()).findAny().orElse(null);
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Book save(Book book) {
    return book;
  }
  public Category saveCategory(Category category) {
    return categoryRepository.save(category);
  }

  public List<Book> filterbyAuthor(String author) {
    return books.stream().filter(s -> author.toLowerCase() .equals(s.getAuthor().toLowerCase())).toList();
  }

  public UserReads getUserRead(long userId, long bookId) {
    List<UserReads> reads = userReadsRepository.findByUserId(userId).stream().filter(i -> bookId == i.getBookId()).toList();
    return reads.size() > 0 ? reads.get(0) : null;
  }

  public List<Book> filterbyCategory(String category) {
    long id = categoryRepository.findByName(category).get(0).getId();
    return bookRepository.findByCategory(id);
  }

  public List<Book> suggestBooksByRead() {
    List<UserReads> reads = userReadsRepository.findAll().stream().toList();
    Collections.sort(reads, new Comparator<UserReads>() {
        @Override
        public int compare(UserReads p1, UserReads p2) {
            return Integer.compare(p1.getReadCount(), p2.getReadCount());
        }
    });
    return bookRepository.findAll().stream().filter(i -> reads.contains(userReadsRepository.findByBookId(i.getId()))).toList(); 
  }
  
}


