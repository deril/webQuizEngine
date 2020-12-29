package com.deril.webQuizEngine.controller;

import static com.deril.webQuizEngine.utils.TestUtils.DEFAULT_PASSWORD;
import static com.deril.webQuizEngine.utils.TestUtils.DEFAULT_USERNAME;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deril.webQuizEngine.user.controller.resource.UserData;
import com.deril.webQuizEngine.user.domain.User;
import com.deril.webQuizEngine.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private UserService service;

  @Test
  public void testRegister_whenSuccessful() throws Exception {
    var userData = new UserData(DEFAULT_USERNAME, DEFAULT_PASSWORD);

    doNothing().when(service).createUser(isA(User.class));

    mvc.perform(post("/api/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userData)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.password").doesNotExist());
  }

  @Test
  public void testRegister_whenPasswordTooShort() throws Exception {
    var userData = new UserData(DEFAULT_USERNAME, "123");

    doNothing().when(service).createUser(isA(User.class));

    mvc.perform(post("/api/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userData)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testRegister_whenUsernameIsNotEmail() throws Exception {
    var userData = new UserData("abcde", DEFAULT_PASSWORD);

    doNothing().when(service).createUser(isA(User.class));

    mvc.perform(post("/api/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userData)))
        .andExpect(status().isBadRequest());
  }
}
