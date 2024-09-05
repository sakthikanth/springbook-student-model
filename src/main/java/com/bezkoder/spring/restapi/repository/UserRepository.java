package com.bezkoder.spring.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.restapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}