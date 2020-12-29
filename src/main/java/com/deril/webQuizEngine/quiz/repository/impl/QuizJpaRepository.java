package com.deril.webQuizEngine.quiz.repository.impl;

import com.deril.webQuizEngine.quiz.repository.model.QuizEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizJpaRepository extends PagingAndSortingRepository<QuizEntity, Long> {

}
