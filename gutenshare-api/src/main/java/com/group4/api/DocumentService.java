package com.group4.api;

import com.group4.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public void storeNewDocument(DocumentDto documentDto) {
        Document document = new Document.DocumentBuilder()
                .setTitle(documentDto.getTitle())
                .setDocumentType(documentDto.getDocumentType())
                .setSchool(getSchool(documentDto))
                .setCourse(getCourse(documentDto))
                .setDepartment(getDepartment(documentDto))
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
            String courseName = documentDto.getCourse().get();
            if (this.courseJpaRepositoryInterface.existsByName(courseName)) {
                course = this.courseJpaRepositoryInterface.getByName(courseName);
            } else {
                course = new Course.CourseBuilder().setName(documentDto.getCourse().get()).build();
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
