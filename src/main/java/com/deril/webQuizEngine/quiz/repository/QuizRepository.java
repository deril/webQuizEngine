package com.deril.webQuizEngine.quiz.repository;

import com.deril.webQuizEngine.quiz.domain.CompletedQuiz;
import com.deril.webQuizEngine.quiz.domain.Quiz;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository {

  Optional<Quiz> findById(Long id);

  Page<CompletedQuiz> findCompletedWithPagination(Pageable pageable, Long userId);

  Quiz save(Quiz quiz);

  void delete(Quiz quiz);

  Page<Quiz> findAll(Pageable pageable);

  void complete(Quiz quiz, Long userId);
}
