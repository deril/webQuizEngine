package com.deril.webQuizEngine.quiz.controller.resource;

import com.deril.webQuizEngine.quiz.validation.MinListSizeConstraint;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class CreateQuizResource {

  @NotBlank
  String title;

  @NotBlank
  String text;

  @MinListSizeConstraint
  List<String> options;

  Set<Long> answer;
}
