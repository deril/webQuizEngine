package com.deril.webQuizEngine.quiz.repository.model;

import com.sun.istack.NotNull;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Data;

@Data
@Entity(name = "quiz")
public class QuizEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private String title;

  @NotNull
  private String text;

  private Long createdBy;

  @ElementCollection
  @CollectionTable(name = "answer", joinColumns = @JoinColumn(name = "quiz_id"))
  private List<AnswerEntity> answers;

  @ElementCollection
  @CollectionTable(name = "option", joinColumns = @JoinColumn(name = "quiz_id"))
  private List<OptionEntity> options;
}
