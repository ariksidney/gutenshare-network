package com.group4.gutenshareweb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group4.api.UserDto;
import com.group4.api.UserService;
import com.group4.gutenshareweb.infrastructure.controller.LoginController;
import com.group4.gutenshareweb.infrastructure.controller.LoginHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LoginController.class, secure = false)
public class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginHelper loginHelper;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        doNothing().when(this.userService).save(any());

        UserDto userDto = new UserDto("rudi", "secret", "first", "last", "rudi@me.com");
        String requestJson = objectMapper.writeValueAsString(userDto);

        this.mockMvc.perform(post("/login/register")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


}
