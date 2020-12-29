package com.deril.webQuizEngine.quiz.repository.impl;

import static java.util.stream.Collectors.toList;

import com.deril.webQuizEngine.quiz.domain.CompletedQuiz;
import com.deril.webQuizEngine.quiz.domain.Quiz;
import com.deril.webQuizEngine.quiz.mapper.QuizMapper;
import com.deril.webQuizEngine.quiz.repository.QuizRepository;
import com.deril.webQuizEngine.quiz.repository.model.QuizEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepository {

  private final QuizJpaRepository quizRepository;
  private final CompletedQuizJpaRepository completedQuizRepository;
  private final QuizMapper quizMapper;

  @Override
  public Optional<Quiz> findById(Long id) {
    return quizRepository.findById(id).map(quizMapper::toDomain);
  }

  @Override
  public Page<CompletedQuiz> findCompletedWithPagination(Pageable pageable, Long userId) {
    return new PageImpl<>(
        completedQuizRepository.findCompletedWithPagination(pageable, userId).stream().map(quizMapper::toCompletedDomain).collect(toList())
    );
  }

  @Override
  public Quiz save(Quiz quiz) {
    QuizEntity savedQuizEntry = quizRepository.save(quizMapper.toEntity(quiz));

    return quizMapper.toDomain(savedQuizEntry);
  }

  @Override
  public void delete(Quiz quiz) {
    quizRepository.delete(quizMapper.toEntity(quiz));
  }

  @Override
  public Page<Quiz> findAll(Pageable pageable) {
    return new PageImpl<>(quizRepository.findAll(pageable).stream().map(quizMapper::toDomain).collect(toList()));
  }

  @Override
  public void complete(Quiz quiz, Long userId) {
    completedQuizRepository.save(quizMapper.toCompletedEntity(quiz, userId));
  }
}
