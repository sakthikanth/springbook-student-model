package com.bezkoder.spring.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.restapi.model.UserReads;

@Repository
public interface UserReadsRepository extends JpaRepository<UserReads, Long> {
    List<UserReads> findByUserId(long userId);
    List<UserReads> findByBookId(long bookId);
    
    // @Query("SELECT userId,bookId,readCount FROM users_reads order by readCount desc")
    // UserReads readsByMoreReadOrder();
}