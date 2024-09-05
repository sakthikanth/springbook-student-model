package com.bezkoder.spring.restapi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_reads")
public class UserReads {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "book_id")
    private long bookId;

    @Column(name = "read_count")
    private int readCount;
    public UserReads() {

    }
    
    public UserReads(long userId, long bookId, int readCount) {
        this.userId = userId;
        this.bookId = bookId;
        this.readCount = readCount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public int getReadCount() {
        return readCount;
    }


    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

}
