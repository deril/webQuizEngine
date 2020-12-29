package com.deril.webQuizEngine.utils;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.deril.webQuizEngine.quiz.controller.resource.CreateQuizResource;
import com.deril.webQuizEngine.quiz.domain.Quiz;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.test.web.servlet.ResultActions;

public final class TestUtils {

  public static final Long DEFAULT_CORRECT_OPTION = 2L;
  public static final String DEFAULT_USERNAME = "test@gmail.com";
  public static final String DEFAULT_PASSWORD = "123456";

  private TestUtils() {
  }

  public static Quiz createJavaLogoQuizWithId(Long id) {
    var quiz = new Quiz();
    quiz.setId(id);
    quiz.setTitle("The Java Logo");
    quiz.setText("What is depicted on the Java logo?");
    quiz.setOptions(List.of("Robot", "Tea leaf", "Cup of coffee", "Bug"));
    quiz.setAnswer(Set.of(DEFAULT_CORRECT_OPTION));
    return quiz;
  }

  public static CreateQuizResource createJavaLogoQuizResource() {
    return new CreateQuizResource(
        "The Java Logo",
        "What is depicted on the Java logo?",
        List.of("Robot", "Tea leaf", "Cup of coffee", "Bug"),
        Set.of(DEFAULT_CORRECT_OPTION)
    );
  }

  public static ResultActions expectQuizJsonIsValid(ResultActions actions, Quiz quiz) throws Exception {
    return actions
        .andExpect(jsonPath("$.id").value(quiz.getId()))
        .andExpect(jsonPath("$.title").value(quiz.getTitle()))
        .andExpect(jsonPath("$.text").value(quiz.getText()))
        .andExpect(jsonPath("$.options").isArray())
        .andExpect(jsonPath("$.options", hasSize(4)))
        .andExpect(jsonPath("$.options", hasItem("Robot")))
        .andExpect(jsonPath("$.options", hasItem("Tea leaf")))
        .andExpect(jsonPath("$.options", hasItem("Cup of coffee")))
        .andExpect(jsonPath("$.options", hasItem("Bug")));
  }

  public static List<Quiz> createTestQuizzes(int n) {
    return Stream.generate(Quiz::new)
        .limit(n)
        .collect(toList());
  }

//  public static User createTestUserWithDefaultName() {
//    var user = new User();
//    user.setId(100L);
//    user.setUsername(DEFAULT_USERNAME);
//    user.setPassword(DEFAULT_PASSWORD);
//    return user;
//  }
//
//  public static Quiz createQuizEntityWithId(long id) {
//    var quiz = new Quiz();
//    quiz.setId(id);
//    return quiz;
//  }
}
