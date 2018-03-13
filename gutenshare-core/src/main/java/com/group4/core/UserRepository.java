package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);

}
