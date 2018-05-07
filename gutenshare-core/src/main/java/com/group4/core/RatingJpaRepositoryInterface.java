package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingJpaRepositoryInterface extends JpaRepository<Rating, Integer> {

    List<Rating> findAllByDocument(Document document);
}
