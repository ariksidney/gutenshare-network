package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepositoryInterface extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByDocument(Document document);
}
