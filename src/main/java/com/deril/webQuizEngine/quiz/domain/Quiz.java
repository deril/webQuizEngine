package com.deril.webQuizEngine.quiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class Quiz {

  Long id;
  String title;
  String text;
  List<String> options;

  @JsonIgnore
  Long createdBy;

  @JsonProperty(access = Access.WRITE_ONLY)
  Set<Long> answer;
}
