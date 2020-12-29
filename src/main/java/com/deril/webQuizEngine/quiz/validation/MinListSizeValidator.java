package com.deril.webQuizEngine.quiz.validation;

import static java.util.Objects.nonNull;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinListSizeValidator implements ConstraintValidator<MinListSizeConstraint, List<String>> {

  @Override
  public boolean isValid(List<String> value, ConstraintValidatorContext context) {
    return nonNull(value) && value.size() >= 2;
  }
}
