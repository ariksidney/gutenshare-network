package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface to interact with comment database table.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
public interface CommentJpaRepositoryInterface extends JpaRepository<Comment, Integer> {

    /**
     * This method returns all comments which belong to a document.
     *
     * @param document Documents of which the comments are wanted
     * @return A list of comment objects
     */
    List<Comment> findAllByDocument(Document document);
}
