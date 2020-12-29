package com.deril.webQuizEngine.quiz.service;

import com.deril.webQuizEngine.quiz.domain.Answer;
import com.deril.webQuizEngine.quiz.domain.CompletedQuiz;
import com.deril.webQuizEngine.quiz.domain.Quiz;
import com.deril.webQuizEngine.quiz.domain.QuizResult;
import com.deril.webQuizEngine.quiz.error.QuizDeleteException;
import com.deril.webQuizEngine.quiz.error.QuizNotFound;
import com.deril.webQuizEngine.quiz.repository.QuizRepository;
import com.deril.webQuizEngine.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

  private final QuizRepository quizRepository;
  private final int pageSize = 10;

  public QuizResult solve(Long id, Answer answer, User user) throws QuizNotFound {
    Quiz quiz = findQuiz(id);

    boolean result = quiz.getAnswer().equals(answer.getAnswer());

    String feedback;
    if (result) {
      quizRepository.complete(quiz, user.getId());
      feedback = "Congratulations, you're right!";
    } else {
      feedback = "Wrong answer! Please, try again.";
    }

    return new QuizResult(result, feedback);
  }

  public Page<Quiz> getQuizzes(Integer page) {
    PageRequest paging = PageRequest.of(page, pageSize);
    return quizRepository.findAll(paging);
  }

  public Quiz addQuiz(Quiz quiz, User user) {
    quiz.setCreatedBy(user.getId());
    return quizRepository.save(quiz);
  }

  public Quiz findQuiz(Long id) throws QuizNotFound {
    return quizRepository.findById(id).orElseThrow(QuizNotFound::new);
  }

  public void deleteQuiz(Long id, User user) throws QuizNotFound, QuizDeleteException {
    Quiz quiz = findQuiz(id);

    if (!quiz.getCreatedBy().equals(user.getId())) {
      throw new QuizDeleteException();
    }

    quizRepository.delete(quiz);
  }

  public Page<CompletedQuiz> getCompletedQuizzes(Integer page, User user) {
    PageRequest paging = PageRequest.of(page, pageSize, Sort.by("completedAt").descending());
    return quizRepository.findCompletedWithPagination(paging, user.getId());
  }
}
