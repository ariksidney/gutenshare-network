package com.group4.api;

import com.group4.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CategoryService {

    private final SchoolJpaRepositoryInterface schoolJpaRepositoryInterface;
    private final DepartmentJpaRepositoryInterface departmentJpaRepositoryInterface;
    private final CourseJpaRepositoryInterface courseJpaRepositoryInterface;

    public CategoryService(SchoolJpaRepositoryInterface schoolJpaRepositoryInterface,
                           DepartmentJpaRepositoryInterface departmentJpaRepositoryInterface,
                           CourseJpaRepositoryInterface courseJpaRepositoryInterface) {
        this.schoolJpaRepositoryInterface = schoolJpaRepositoryInterface;
        this.departmentJpaRepositoryInterface = departmentJpaRepositoryInterface;
        this.courseJpaRepositoryInterface = courseJpaRepositoryInterface;
    }

    public CategoryDto getAllCategories() {
        List<School> schools = schoolJpaRepositoryInterface.findAll();
        List<Department> departments = departmentJpaRepositoryInterface.findAll();
        List<Course> courses = courseJpaRepositoryInterface.findAll();
        return new CategoryDto(schools, departments, courses);
    }

}
