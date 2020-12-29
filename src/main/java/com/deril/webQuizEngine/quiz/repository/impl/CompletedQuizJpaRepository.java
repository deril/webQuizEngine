package com.deril.webQuizEngine.quiz.repository.impl;

import com.deril.webQuizEngine.quiz.repository.model.CompletedQuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CompletedQuizJpaRepository extends PagingAndSortingRepository<CompletedQuizEntity, Long> {

  @Query(value = "SELECT q FROM completed_quiz q WHERE q.completedBy = :userId")
  Page<CompletedQuizEntity> findCompletedWithPagination(Pageable pageable, @Param("userId") Long userId);
}
