package com.deril.webQuizEngine.quiz.repository.model;

import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AnswerEntity {

  Long value;
}
