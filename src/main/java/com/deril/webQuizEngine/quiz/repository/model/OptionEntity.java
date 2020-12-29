package com.deril.webQuizEngine.quiz.repository.model;

import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class OptionEntity {

  private String value;
}
