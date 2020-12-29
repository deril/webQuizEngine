package com.deril.webQuizEngine.quiz.repository.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "completed_quiz")
public class CompletedQuizEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private Long quizId;

  private Long completedBy;

  private Instant completedAt;
}
