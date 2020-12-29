package com.deril.webQuizEngine.quiz.mapper;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.deril.webQuizEngine.config.MapperConfiguration;
import com.deril.webQuizEngine.quiz.controller.resource.CreateQuizResource;
import com.deril.webQuizEngine.quiz.domain.CompletedQuiz;
import com.deril.webQuizEngine.quiz.domain.Quiz;
import com.deril.webQuizEngine.quiz.repository.model.AnswerEntity;
import com.deril.webQuizEngine.quiz.repository.model.CompletedQuizEntity;
import com.deril.webQuizEngine.quiz.repository.model.OptionEntity;
import com.deril.webQuizEngine.quiz.repository.model.QuizEntity;
import java.time.Instant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface QuizMapper {

  @Mapping(target = "answer", defaultExpression = "java(java.util.Collections.emptySet())")
  Quiz toDomain(CreateQuizResource quizCreateResource);

  default Quiz toDomain(QuizEntity quizEntity) {
    var quiz = new Quiz();
    quiz.setId(quizEntity.getId());
    quiz.setTitle(quizEntity.getTitle());
    quiz.setText(quizEntity.getText());
    quiz.setOptions(quizEntity.getOptions().stream().map(OptionEntity::getValue).collect(toList()));
    quiz.setAnswer(quizEntity.getAnswers().stream().map(AnswerEntity::getValue).collect(toSet()));
    quiz.setCreatedBy(quizEntity.getCreatedBy());
    return quiz;
  }

  default QuizEntity toEntity(Quiz quiz) {
    var quizEntity = new QuizEntity();
    quizEntity.setId(quiz.getId());
    quizEntity.setTitle(quiz.getTitle());
    quizEntity.setText(quiz.getText());
    quizEntity.setOptions(quiz.getOptions().stream().map(o -> {
      var oe = new OptionEntity();
      oe.setValue(o);
      return oe;
    }).collect(toList()));
    quizEntity.setAnswers(quiz.getAnswer().stream().map(a -> {
      var ae = new AnswerEntity();
      ae.setValue(a);
      return ae;
    }).collect(toList()));
    quizEntity.setCreatedBy(quiz.getCreatedBy());
    return quizEntity;
  }

  default CompletedQuizEntity toCompletedEntity(Quiz quiz, Long userId) {
    var completedQuizEntity = new CompletedQuizEntity();
    completedQuizEntity.setQuizId(quiz.getId());
    completedQuizEntity.setCompletedAt(Instant.now());
    completedQuizEntity.setCompletedBy(userId);
    return completedQuizEntity;
  }

  CompletedQuiz toCompletedDomain(CompletedQuizEntity completedQuizEntity);
}
