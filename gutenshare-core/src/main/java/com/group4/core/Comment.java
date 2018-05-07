package com.group4.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_COMMENT")
public class Comment {

    @Id
    @Column(name = "comment_id", nullable = false, unique = true)
    private String id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    @JsonIgnore
    private Document document;

    @Column(name = "comment_date")
    private LocalDateTime commentDate;

    public Comment() {
        // for JPA
    }

    public Comment(CommentBuilder commentBuilder) {
        this.id = IdGenerator.timeBasedUUID().toString();
        this.comment = commentBuilder.comment;
        this.document = commentBuilder.document;
        this.user = commentBuilder.user;
        this.commentDate = LocalDateTime.now();
    }

    public String getComment() {
        return comment;
    }

    public String getUser() {
        return user.getUsername();
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public Document getDocument() {
        return document;
    }

    public static class CommentBuilder {
        private String comment;
        private User user;
        private Document document;

        public CommentBuilder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public CommentBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public CommentBuilder setDocument(Document document) {
            this.document = document;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

}
