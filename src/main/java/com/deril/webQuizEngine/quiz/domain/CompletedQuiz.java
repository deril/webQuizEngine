package com.deril.webQuizEngine.quiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Data;

@Data
public class CompletedQuiz {

  @JsonIgnore
  Long id;

  @JsonProperty(value = "id")
  Long quizId;

  Instant completedAt;
}
