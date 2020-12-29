package com.deril.webQuizEngine.quiz.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.deril.webQuizEngine.quiz.controller.resource.AnswerResource;
import com.deril.webQuizEngine.quiz.controller.resource.CreateQuizResource;
import com.deril.webQuizEngine.quiz.domain.Answer;
import com.deril.webQuizEngine.quiz.domain.CompletedQuiz;
import com.deril.webQuizEngine.quiz.domain.Quiz;
import com.deril.webQuizEngine.quiz.domain.QuizResult;
import com.deril.webQuizEngine.quiz.error.QuizDeleteException;
import com.deril.webQuizEngine.quiz.error.QuizNotFound;
import com.deril.webQuizEngine.quiz.mapper.QuizMapper;
import com.deril.webQuizEngine.quiz.service.QuizService;
import com.deril.webQuizEngine.user.domain.User;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/quizzes")
public class WebQuizController {

  private final QuizService quizService;
  private final QuizMapper quizMapper;

  @PostMapping(path = "/{id}/solve")
  public QuizResult solveQuiz(@PathVariable Long id, @RequestBody AnswerResource answer, @AuthenticationPrincipal User user) throws QuizNotFound {
    var ans = new Answer(answer.getAnswer());

    return quizService.solve(id, ans, user);
  }

  @PostMapping
  public Quiz createQuiz(@Valid @RequestBody CreateQuizResource createResource,
      @AuthenticationPrincipal User user) {

    return quizService.addQuiz(quizMapper.toDomain(createResource), user);
  }

  @GetMapping(path = "/{id}")
  public Quiz getQuizById(@PathVariable Long id) throws QuizNotFound {
    return quizService.findQuiz(id);
  }

  @GetMapping
  public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page) {
    return quizService.getQuizzes(page);
  }

  @ResponseStatus(NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal User user) throws QuizNotFound, QuizDeleteException {
    quizService.deleteQuiz(id, user);
  }

  @GetMapping(path = "/completed")
  public Page<CompletedQuiz> getCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page, @AuthenticationPrincipal User user) {
    return quizService.getCompletedQuizzes(page, user);
  }
}
