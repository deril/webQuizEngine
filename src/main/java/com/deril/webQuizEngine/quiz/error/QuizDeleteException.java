package com.deril.webQuizEngine.quiz.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class QuizDeleteException extends Exception {

}
