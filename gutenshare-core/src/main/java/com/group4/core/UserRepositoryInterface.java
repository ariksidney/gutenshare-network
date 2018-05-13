package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface to interact with user database table.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepositoryInterface extends JpaRepository<User, Integer> {

    /**
     * Method to find a user by username in the database.
     *
     * @param username the username to find
     * @return A user object
     */
    @Query("select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);

}
