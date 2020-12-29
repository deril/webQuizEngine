package com.deril.webQuizEngine.quiz.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Constraint(validatedBy = {MinListSizeValidator.class})
public @interface MinListSizeConstraint {

  String message() default "Min size cannot be less then 2 elements";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
