package com.deril.webQuizEngine.user.repository.impl;

import com.deril.webQuizEngine.user.domain.User;
import com.deril.webQuizEngine.user.mapper.UserMapper;
import com.deril.webQuizEngine.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository repository;
  private final UserMapper mapper;

  @Override
  public Optional<User> findByUsername(String username) {
    return repository.findByUsername(username).map(mapper::toDomain);
  }

  @Override
  public void save(User user) {

    repository.save(mapper.toEntity(user));
  }
}
