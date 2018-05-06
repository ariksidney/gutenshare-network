package com.group4.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_RATING")
public class Rating {

    @Id
    @Column(name = "rating_id", nullable = false, unique = true)
    private String id;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    @JsonIgnore
    private Document document;

    @Column(name = "rating_date")
    private LocalDateTime ratingDate;

    public Rating() {
        // For JPA
    }

    public Rating(RatingBuilder ratingBuilder) {
        this.id = IdGenerator.timeBasedUUID().toString();
        this.rating = checkValidRating(ratingBuilder.rating);
        this.document = ratingBuilder.document;
        this.user = ratingBuilder.user;
        this.ratingDate = LocalDateTime.now();
    }

    public Integer getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getRatingDate() {
        return ratingDate;
    }

    private Integer checkValidRating(Integer rating) {
        if (rating >= 1 && rating <= 5) {
            return rating;
        } else {
            throw new IllegalArgumentException("Rating is invalid");
        }
    }

    public static class RatingBuilder {

        private Integer rating;
        private User user;
        private Document document;

        public RatingBuilder setRating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public RatingBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public RatingBuilder setDocument(Document document) {
            this.document = document;
            return this;
        }

        public Rating build() {
            return new Rating(this);
        }
    }
}
