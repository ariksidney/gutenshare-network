package com.group4.api;

import com.group4.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public DocumentService(DocumentStoreRepositoryInterface documentStoreRepositoryInterface,
                           DocumentJpaRepositoryInterface documentJpaRepositoryInterface,
                           SchoolJpaRepositoryInterface schoolJpaRepositoryInterface,
                           CourseJpaRepositoryInterface courseJpaRepositoryInterface,
                           DepartmentJpaRepositoryInterface departmentJpaRepositoryInterface) {
        this.documentStoreRepositoryInterface = documentStoreRepositoryInterface;
        this.documentJpaRepositoryInterface = documentJpaRepositoryInterface;
        this.schoolJpaRepositoryInterface = schoolJpaRepositoryInterface;
        this.courseJpaRepositoryInterface = courseJpaRepositoryInterface;
        this.departmentJpaRepositoryInterface = departmentJpaRepositoryInterface;
    }

    public void storeNewDocument(CreateDocumentDto documentDto) {
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
                .build();
        document.storeFile(this.documentStoreRepositoryInterface);
        this.documentJpaRepositoryInterface.save(document);
    }

    public Optional<DeliverDocumentDto> getDocumentById(String documentId) {
        Document document = this.documentJpaRepositoryInterface.getById(documentId);
        if (document == null) {
            return Optional.empty();
        } else {
            return Optional.of(getDto(document));
        }
    }

    private DeliverDocumentDto getDto(Document document) {
        return new DeliverDocumentDto(document.getTitle(),
                document.getDocumentType().toString(),
                Optional.of(document.getSchool().toString()),
                Optional.of(document.getDepartment().toString()),
                Optional.of(document.getCourse().toString()),
                document.getFileType(),
                Optional.of(document.getTags().stream().map(Object::toString).collect(Collectors.toList())),
                Optional.of(document.getDescription()),
                document.getContent(this.documentStoreRepositoryInterface));
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

}
