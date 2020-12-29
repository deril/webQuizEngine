package com.deril.webQuizEngine.user.repository.impl;

import com.deril.webQuizEngine.user.repository.model.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);
}
