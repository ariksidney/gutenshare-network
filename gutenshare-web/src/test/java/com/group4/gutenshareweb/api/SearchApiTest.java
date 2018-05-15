package com.group4.gutenshareweb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group4.api.CategoryDto;
import com.group4.api.CategoryService;
import com.group4.api.DocumentDto;
import com.group4.api.DocumentService;
import com.group4.core.Course;
import com.group4.core.Department;
import com.group4.core.School;
import com.group4.core.User;
import com.group4.gutenshareweb.infrastructure.controller.SearchController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SearchController.class, secure = false)
public class SearchApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DocumentService documentService;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testGetCategories() throws Exception {
        when(categoryService.getAllCategories())
                .thenReturn(generateDummyCategories());

        this.mockMvc.perform(get("/api/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.schools[0].name", is("zhaw")))
                .andExpect(jsonPath("$.departments[0].name", is("testdept")))
                .andExpect(jsonPath("$.courses[0].name", is("funnycourse")))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void testSearchWithResults() throws Exception {
        when(documentService.getDocumentsFromSearchQuery("a test query"))
                .thenReturn(generateSearchResultForQuery1());

        this.mockMvc.perform(get("/api/search")
                .param("query", "a test query")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.[0].id", is("1")))
                .andExpect(jsonPath("$.[0].documentType", is("summary")));
    }

    @Test
    public void testSearchWithoutResults() throws Exception {
        when(documentService.getDocumentsFromSearchQuery("no results"))
                .thenReturn(Optional.empty());

        this.mockMvc.perform(get("/api/search")
                .param("query", "no results"))
                .andExpect(MockMvcResultMatchers.status().is(204));

    }

    @Test
    public void testBrowseWithResults() throws Exception {
        when(documentService.getDocumentsFromBrowse("school1", "dept1", "course1"))
                .thenReturn(generateSearchResultForQuery1());

        this.mockMvc.perform(get("/api/browse")
                .param("school", "school1")
                .param("departement", "dept1")
                .param("course", "course1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.[1].description", is("desc2")));
    }

    @Test
    public void testBrowseWithoutResults() throws Exception {
        when(documentService.getDocumentsFromBrowse(null, "dept1", "course1"))
                .thenReturn(Optional.empty());

        this.mockMvc.perform(get("/api/browse")
                .param("departement", "dept1")
                .param("course", "course1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }


    private Optional<List<DocumentDto>> generateSearchResultForQuery1() {
        List<String> tags = Arrays.asList("tag");
        DocumentDto documentDto1 = new DocumentDto("1", "title", "summary", Optional.of("school1"),
                Optional.of("dept1"), Optional.of("course1"), "pdf", Optional.of(tags), Optional.of("desc"), new User
                ());

        DocumentDto documentDto2 = new DocumentDto("2", "title2", "summary", Optional.of("school1"),
                Optional.of("dept1"), Optional.of("course1"), "docx", Optional.of(tags), Optional.of("desc2"), new
                User());

        return Optional.of(Arrays.asList(documentDto1, documentDto2));
    }

    private CategoryDto generateDummyCategories() {
        List<School> schools = Arrays.asList(new School.SchoolBuilder().setName("ZHAW").build());
        List<Department> departments = Arrays.asList(new Department.DepartmentBuilder().setName("testDept").build());
        List<Course> courses = Arrays.asList(new Course.CourseBuilder().setName("FunnyCourse").build());
        return new CategoryDto(schools, departments, courses);
    }
}
