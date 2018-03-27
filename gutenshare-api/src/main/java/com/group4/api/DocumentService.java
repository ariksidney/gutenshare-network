package com.group4.api;

import com.group4.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DocumentService {

    private final DocumentStoreRepositoryInterface documentStoreRepositoryInterface;
    private final DocumentJpaRepositoryInterface documentJpaRepositoryInterface;

    @Autowired
    public DocumentService(DocumentStoreRepositoryInterface documentStoreRepositoryInterface,
                           DocumentJpaRepositoryInterface documentJpaRepositoryInterface) {
        this.documentStoreRepositoryInterface = documentStoreRepositoryInterface;
        this.documentJpaRepositoryInterface = documentJpaRepositoryInterface;
    }

    public void storeNewDocument(DocumentDto documentDto) {
        Document document = new Document.DocumentBuilder()
                .setTitle(documentDto.getTitle())
                .setDocumentType(documentDto.getDocumentType())
                .setSchool(getSchool(documentDto))
                .setDepartment(getDepartment(documentDto))
                .setCourse(getCourse(documentDto))
                .setFileType(documentDto.getFileType())
                .setInputStream(documentDto.getDocumentStream())
                .setTags(getTags(documentDto))
                .setDescription(getDescription(documentDto))
                .build();
        document.storeFile(this.documentStoreRepositoryInterface);
        this.documentJpaRepositoryInterface.save(document);
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
            school = new School.SchoolBuilder().setName(documentDto.getSchool().get()).build();
        }

        return school;
    }

    private Department getDepartment(DocumentDto documentDto) {
        Department department = null;
        if (documentDto.getDepartment().isPresent()) {
            department = new Department.DepartmentBuilder().setName(documentDto.getDepartment().get()).build();
        }

        return department;
    }

    private Course getCourse(DocumentDto documentDto) {
        Course course = null;
        if (documentDto.getCourse().isPresent()) {
            course = new Course.CourseBuilder().setName(documentDto.getCourse().get()).build();
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
