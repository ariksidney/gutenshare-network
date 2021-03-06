package com.group4.api;

import com.group4.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DocumentService {

    private final DocumentStoreRepositoryInterface documentStoreRepositoryInterface;
    private final DocumentJpaRepositoryInterface documentJpaRepositoryInterface;
    private final SchoolJpaRepositoryInterface schoolJpaRepositoryInterface;
    private final CourseJpaRepositoryInterface courseJpaRepositoryInterface;
    private final DepartmentJpaRepositoryInterface departmentJpaRepositoryInterface;
    private final CommentJpaRepositoryInterface commentJpaRepositoryInterface;
    private final RatingJpaRepositoryInterface ratingJpaRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public DocumentService(DocumentStoreRepositoryInterface documentStoreRepositoryInterface,
                           DocumentJpaRepositoryInterface documentJpaRepositoryInterface,
                           SchoolJpaRepositoryInterface schoolJpaRepositoryInterface,
                           CourseJpaRepositoryInterface courseJpaRepositoryInterface,
                           DepartmentJpaRepositoryInterface departmentJpaRepositoryInterface,
                           CommentJpaRepositoryInterface commentJpaRepositoryInterface,
                           RatingJpaRepositoryInterface ratingJpaRepositoryInterface,
                           UserRepositoryInterface userRepositoryInterface) {
        this.documentStoreRepositoryInterface = documentStoreRepositoryInterface;
        this.documentJpaRepositoryInterface = documentJpaRepositoryInterface;
        this.schoolJpaRepositoryInterface = schoolJpaRepositoryInterface;
        this.courseJpaRepositoryInterface = courseJpaRepositoryInterface;
        this.departmentJpaRepositoryInterface = departmentJpaRepositoryInterface;
        this.commentJpaRepositoryInterface = commentJpaRepositoryInterface;
        this.ratingJpaRepositoryInterface = ratingJpaRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
    }

public void storeNewDocument(String title, String documentType, Optional<String> school,
                                                         Optional<String> department, Optional<String> course,
                                                         Optional<List<String>> tags, Optional<String>
                                                                 description, InputStream documentIs, String username,
                                                         String fileType) {
        User user = userRepositoryInterface.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("username does not exist");
        }
        CreateDocumentDto documentDto = new CreateDocumentDto(
                "",
                title,
                documentType,
                school,
                department,
                course,
                fileType,
                tags,
                description,
                documentIs,
                user
        );
        Document document = new Document.DocumentBuilder()
                .setTitle(documentDto.getTitle())
                .setDocumentType(checkDocumentType(documentDto.getDocumentType()))
                .setSchool(getSchool(documentDto))
                .setCourse(getCourse(documentDto))
                .setDepartment(getDepartment(documentDto))
                .setFileType(documentDto.getFileType())
                .setInputStream(documentDto.getInputStream())
                .setTags(getTags(documentDto))
                .setDescription(getDescription(documentDto))
                .setUser(user)
                .build();
        document.storeFile(this.documentStoreRepositoryInterface);
        this.documentJpaRepositoryInterface.save(document);
    }

    public Optional<DeliverDocumentDto> getDocumentById(String documentId) {
        Document document = this.documentJpaRepositoryInterface.getById(documentId);
        List<Comment> comments = this.commentJpaRepositoryInterface.findAllByDocument(document);
        Integer avgRating = calcAvg(ratingJpaRepositoryInterface.findAllByDocument(document));
        if (document == null) {
            return Optional.empty();
        } else {
            return Optional.of(getDeliverDto(document, comments, avgRating));
        }
    }

    public Optional<List<DocumentDto>> getDocumentsFromSearchQuery(String query) {
        List<Document> documents = this.documentJpaRepositoryInterface.findAllBySearchQuery(query);
        if (documents == null) {
            return Optional.empty();
        } else {
            List<DocumentDto> documentDtos = new ArrayList<>();
            documents.forEach(document -> documentDtos.add(getDto(document)));
            return Optional.of(documentDtos);
        }
    }

    public Optional<List<DocumentDto>> getDocumentsFromBrowse(String school, String dep, String course) {
        List<Document> documents = this.documentJpaRepositoryInterface.findBySchoolAndDeptAndCourse(school,
                dep, course);
        if (documents.isEmpty()) {
            return Optional.empty();
        } else {
            List<DocumentDto> documentDtos = new ArrayList<>();
            documents.forEach(document -> documentDtos.add(getDto(document)));
            return Optional.of(documentDtos);
        }
    }

    private DeliverDocumentDto getDeliverDto(Document document, List<Comment> comments, Integer avgRating) {
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
                avgRating,
                document.getContent(this.documentStoreRepositoryInterface),
                document.getUser());
    }

    private DocumentDto getDto(Document document) {
        return new DocumentDto(
                document.getId(),
                document.getTitle(),
                document.getDocumentType().toString(),
                Optional.of(document.getSchool().toString()),
                Optional.of(document.getDepartment().toString()),
                Optional.of(document.getCourse().toString()),
                document.getFileType(),
                Optional.of(document.getTags().stream().map(Object::toString).collect(Collectors.toList())),
                Optional.of(document.getDescription()),
                document.getUser());
    }

    private DocumentType checkDocumentType(String documentType) {
        boolean containsType = Arrays.stream(DocumentType.values()).anyMatch((t) -> t.name().equals(documentType));
        if (containsType) {
            return DocumentType.valueOf(documentType);
        } else {
            throw new IllegalArgumentException("Invalid document type");
        }
    }

    private List<Tag> getTags(DocumentDto documentDto) {
        List<Tag> tags = new ArrayList<>();
        if (documentDto.getTags().isPresent()) {
            documentDto.getTags().get().forEach(tag -> tags.add(new Tag.TagBuilder().setName(tag).build()));
        }
        return tags;
    }

    private School getSchool(DocumentDto documentDto) {
        School school = null;
        if (documentDto.getSchool().isPresent()) {
            String schoolName = documentDto.getSchool().get();
            if (this.schoolJpaRepositoryInterface.existsByName(schoolName)) {
                school = this.schoolJpaRepositoryInterface.getByName(schoolName);
            } else {
                throw new IllegalArgumentException("School is not yet registered");
            }
        }

        return school;
    }

    private Department getDepartment(DocumentDto documentDto) {
        Department department = null;
        if (documentDto.getDepartment().isPresent()) {
            String deptName = documentDto.getDepartment().get();
            if (this.departmentJpaRepositoryInterface.existsByName(deptName)) {
                department = this.departmentJpaRepositoryInterface.getByName(deptName);
            } else {
                department = new Department.DepartmentBuilder().setName(documentDto.getDepartment().get()).build();
            }
        }
        return department;
    }

    private Course getCourse(DocumentDto documentDto) {
        Course course = null;
        if (documentDto.getCourse().isPresent()) {
            String courseName = documentDto.getCourse().get().toLowerCase();
            if (this.courseJpaRepositoryInterface.existsByName(courseName)) {
                course = this.courseJpaRepositoryInterface.getByName(courseName);
            } else {
                course = new Course.CourseBuilder().setName(courseName).build();
            }
        }
        return course;
    }

    private String getDescription(DocumentDto documentDto) {
        String description = null;
        if (documentDto.getDescription().isPresent()) {
            description = documentDto.getDescription().get();
        }

        return description;
    }

    private Integer calcAvg(List<Rating> ratings) {
        if (ratings.isEmpty()) {
            return 0;
        }
        return Math.toIntExact(Math.round(ratings.stream().mapToInt(rating -> rating.getRating()).average()
                .getAsDouble()));
    }


}
