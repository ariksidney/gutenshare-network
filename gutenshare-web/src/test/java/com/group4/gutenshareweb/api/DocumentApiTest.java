package com.group4.gutenshareweb.api;


import com.group4.api.CommentAndRateService;
import com.group4.api.DocumentService;
import com.group4.core.DocumentJpaRepositoryInterface;
import com.group4.core.UserRepositoryInterface;
import com.group4.gutenshareweb.infrastructure.controller.DocumentController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = DocumentController.class, secure = false)
public class DocumentApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @MockBean
    private UserRepositoryInterface userRepositoryInterface;

    @MockBean
    private CommentAndRateService commentAndRateService;

    @MockBean
    private DocumentJpaRepositoryInterface documentJpaRepositoryInterface;

    @Test
    public void testStoreDocumentValid() throws Exception {
        doNothing().when(this.documentService).storeNewDocument(anyString(), anyString(), any(), any(), any(), any(),
                any(), any(), anyString(), anyString());

        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-file", "testFile",
                "application/pdf", "test data".getBytes());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/document")
                .file("document", mockMultipartFile.getBytes())
                .param("title", "testTitle")
                .param("documenttype", "EXAM")
                .param("school", "schoolTest")
                .param("department", "deptTest")
                .param("course", "testcourse")
                .param("description", "description test")
                .param("tags", "tag1, tag2")
                .param("user", "testUser");

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"CREATED\""));
    }

    @Test
    public void testStoreDocumentInvalid() throws Exception {
        doNothing().when(this.documentService).storeNewDocument(anyString(), anyString(), any(), any(), any(), any(),
                any(), any(), anyString(), anyString());

        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-file", "testFile",
                "application/pdf", "test data".getBytes());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/document")
                .file("document", mockMultipartFile.getBytes())
                .param("documenttype", "EXAM")
                .param("school", "schoolTest")
                .param("department", "deptTest")
                .param("course", "testcourse")
                .param("description", "description test")
                .param("tags", "tag1, tag2")
                .param("user", "testUser");

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
