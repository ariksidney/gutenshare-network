package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface to interact with Rating database table.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
public interface RatingJpaRepositoryInterface extends JpaRepository<Rating, Integer> {

    /**
     * Method to get all ratings from a given document.
     *
     * @param document the document to get reviews from
     * @return List of ratings
     */
    List<Rating> findAllByDocument(Document document);
}
