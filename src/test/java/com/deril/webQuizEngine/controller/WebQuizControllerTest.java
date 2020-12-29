package com.deril.webQuizEngine.controller;

import static com.deril.webQuizEngine.utils.TestUtils.DEFAULT_CORRECT_OPTION;
import static com.deril.webQuizEngine.utils.TestUtils.DEFAULT_PASSWORD;
import static com.deril.webQuizEngine.utils.TestUtils.DEFAULT_USERNAME;
import static com.deril.webQuizEngine.utils.TestUtils.createJavaLogoQuizResource;
import static com.deril.webQuizEngine.utils.TestUtils.createJavaLogoQuizWithId;
import static com.deril.webQuizEngine.utils.TestUtils.createTestQuizzes;
import static com.deril.webQuizEngine.utils.TestUtils.expectQuizJsonIsValid;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deril.webQuizEngine.quiz.controller.resource.CreateQuizResource;
import com.deril.webQuizEngine.quiz.domain.Answer;
import com.deril.webQuizEngine.quiz.domain.Quiz;
import com.deril.webQuizEngine.quiz.domain.QuizResult;
import com.deril.webQuizEngine.quiz.error.QuizNotFound;
import com.deril.webQuizEngine.quiz.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class WebQuizControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private QuizService quizService;

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testCreateQuiz_whenFourOptionsAndAnswerExist() throws Exception {
    var quizWithoutId = createJavaLogoQuizResource();
    var quizWithId = createJavaLogoQuizWithId(1L);

    when(quizService.addQuiz(any(Quiz.class), any())).thenReturn(quizWithId);

    expectQuizJsonIsValid(mvc.perform(post("/api/quizzes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(quizWithoutId)))
        .andExpect(status().isOk()), quizWithId);
  }

  @Test
  public void testCreateQuiz_whenUnauthorized() throws Exception {
    var quizWithoutId = createJavaLogoQuizResource();

    mvc.perform(post("/api/quizzes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(quizWithoutId)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testCreateQuiz_whenNoAnswer() throws Exception {
    var quizWithoutId = new CreateQuizResource(
        "The Java Logo",
        "What is depicted on the Java logo?",
        List.of("Robot", "Tea leaf", "Cup of coffee", "Bug"),
        emptySet()
    );
    var quizWithId = createJavaLogoQuizWithId(1L);

    when(quizService.addQuiz(any(Quiz.class), any())).thenReturn(quizWithId);

    expectQuizJsonIsValid(mvc.perform(post("/api/quizzes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(quizWithoutId)))
        .andExpect(status().isOk()), quizWithId);
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testCreateQuiz_whenNoOptions() throws Exception {
    var quizWithId = createJavaLogoQuizWithId(1L);
    var quizWithoutId = new CreateQuizResource(
        "The Java Logo",
        "What is depicted on the Java logo?",
        emptyList(),
        Set.of(DEFAULT_CORRECT_OPTION)
    );

    when(quizService.addQuiz(any(Quiz.class), any())).thenReturn(quizWithId);

    mvc.perform(post("/api/quizzes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(quizWithoutId)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testGetQuiz_whenExists() throws Exception {
    var quiz = createJavaLogoQuizWithId(1L);

    when(quizService.findQuiz(anyLong())).thenReturn(quiz);

    expectQuizJsonIsValid(mvc.perform(get(String.format("/api/quizzes/%s", quiz.getId())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.answer").doesNotExist()), quiz);
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testGetQuiz_whenQuizNotFound() throws Exception {
    when(quizService.findQuiz(anyLong())).thenThrow(QuizNotFound.class);

    mvc.perform(get(String.format("/api/quizzes/%d", 1)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetQuiz_whenUnauthorized() throws Exception {
    mvc.perform(get(String.format("/api/quizzes/%d", 1)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  void testGetQuizList_evaluatesPageableParameter() throws Exception {
    var quizzes = createTestQuizzes(15);

    when(quizService.getQuizzes(anyInt())).thenReturn(new PageImpl<>(quizzes));

    mvc.perform(get("/api/quizzes")
        .param("page", "5")
        .param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testGetQuizList_whenManyQuizzes() throws Exception {
    var quizzes = createTestQuizzes(15);

    when(quizService.getQuizzes(anyInt())).thenReturn(new PageImpl<>(quizzes));

    mvc.perform(get("/api/quizzes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(quizzes.size())));
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testGetQuizList_whenNoQuizzes() throws Exception {
    when(quizService.getQuizzes(anyInt())).thenReturn(new PageImpl<>(List.of()));

    mvc.perform(get("/api/quizzes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(0)));
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testSolveQuiz_whenCorrectAnswer() throws Exception {
    var successSolve = new QuizResult(true, "");
    when(quizService.solve(anyLong(), any(Answer.class), any())).thenReturn(successSolve);

    mvc.perform(post(String.format("/api/quizzes/%d/solve", 1))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(Set.of(0, 1))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testSolveQuiz_whenIncorrectAnswer() throws Exception {
    var failedSolve = new QuizResult(false, "");
    when(quizService.solve(anyLong(), any(Answer.class), any())).thenReturn(failedSolve);

    mvc.perform(post(String.format("/api/quizzes/%d/solve", 1))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(Set.of(0, 1))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(false));
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testSolveQuiz_whenNoAnswer() throws Exception {
    mvc.perform(post(String.format("/api/quizzes/%d/solve", 1)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testSolveQuiz_whenQuizNotFound() throws Exception {
    when(quizService.solve(anyLong(), any(Answer.class), any()))
        .thenThrow(QuizNotFound.class);

    mvc.perform(post(String.format("/api/quizzes/%d/solve", 1))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(Set.of(0, 1))))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testSolveQuiz_whenUnauthorized() throws Exception {
    mvc.perform(post(String.format("/api/quizzes/%d/solve", 1))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(Set.of(0, 1))))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD)
  public void testDeleteQuiz_whenSuccessful() throws Exception {
    mvc.perform(delete(String.format("/api/quizzes/%d", 1)))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void testDeleteQuiz_whenUnauthorized() throws Exception {
    mvc.perform(delete(String.format("/api/quizzes/%d", 1)))
        .andExpect(status().isUnauthorized());
  }
}
