package com.group4.api;

import com.group4.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CommentAndRateService {

    private final DocumentJpaRepositoryInterface documentJpaRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final DocumentStoreRepositoryInterface documentStoreRepositoryInterface;
    private final CommentJpaRepositoryInterface commentJpaRepositoryInterface;

    @Autowired
    public CommentAndRateService(DocumentJpaRepositoryInterface documentJpaRepositoryInterface,
                                 UserRepositoryInterface userRepositoryInterface,
                                 DocumentStoreRepositoryInterface documentStoreRepositoryInterface,
                                 CommentJpaRepositoryInterface commentJpaRepositoryInterface) {
        this.documentJpaRepositoryInterface = documentJpaRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.documentStoreRepositoryInterface = documentStoreRepositoryInterface;
        this.commentJpaRepositoryInterface = commentJpaRepositoryInterface;
    }

    public Optional<DeliverDocumentDto> addComment(String documentId, String commentString, String username) {
        Document document = documentJpaRepositoryInterface.getById(documentId);
        User user = userRepositoryInterface.findByUsername(username);
        if (document == null || user == null) {
            return Optional.empty();
        } else {
            Comment comment = new Comment.CommentBuilder().setComment(commentString).setUser(user).setDocument(document).build();
            commentJpaRepositoryInterface.save(comment);
            List<Comment> comments = commentJpaRepositoryInterface.findAllByDocument(document);
            return Optional.of(getDeliverDto(document, comments));
        }
    }

    private DeliverDocumentDto getDeliverDto(Document document, List<Comment> comments) {
        return new DeliverDocumentDto(document.getId(),
                document.getTitle(),
                document.getDocumentType().toString(),
                Optional.of(document.getSchool().toString()),
                Optional.of(document.getDepartment().toString()),
                Optional.of(document.getCourse().toString()),
                document.getFileType(),
                Optional.of(document.getTags().stream().map(Object::toString).collect(Collectors.toList())),
                Optional.of(document.getDescription()),
                document.getUploadDate(),
                comments,
                document.getContent(documentStoreRepositoryInterface));
    }
}
