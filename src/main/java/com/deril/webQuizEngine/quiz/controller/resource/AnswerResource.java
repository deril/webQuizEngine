package com.deril.webQuizEngine.quiz.controller.resource;

import java.util.HashSet;
import java.util.Set;
import lombok.Value;

@Value
public class AnswerResource {

  Set<Long> answer = new HashSet<>();
}
