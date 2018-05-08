package com.group4.api;

import com.group4.core.Course;
import com.group4.core.Department;
import com.group4.core.School;

import java.util.List;

public class CategoryDto {

    private List<School> schools;
    private List<Department> departments;
    private List<Course> courses;

    public CategoryDto(List<School> schools, List<Department> departments, List<Course> courses) {
        this.schools = schools;
        this.departments = departments;
        this.courses = courses;
    }

    public List<School> getSchools() {
        return schools;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
