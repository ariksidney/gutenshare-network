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

    private String getSchool(DocumentDto documentDto) {
        String school = null;
        if (documentDto.getSchool().isPresent()) {
            // ToDo: remove .getName() and return school object instead
            school = new School.SchoolBuilder().setName(documentDto.getSchool().get()).build().getName();
        }

        return school;
    }

    private String getDepartment(DocumentDto documentDto) {
        String department = null;
        if (documentDto.getDepartment().isPresent()) {
            // ToDo: remove .getName() and return department object instead
            department = new Department.DepartmentBuilder().setName(documentDto.getDepartment().get()).build().getName();
        }

        return department;
    }

    private String getCourse(DocumentDto documentDto) {
        String course = null;
        if (documentDto.getCourse().isPresent()) {
            // ToDo: remove .getName() and return course object instead
            course = new Course.CourseBuilder().setName(documentDto.getCourse().get()).build().getName();
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
