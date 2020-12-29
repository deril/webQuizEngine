package com.deril.webQuizEngine.user.repository;

import com.deril.webQuizEngine.user.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

  Optional<User> findByUsername(String username);

  void save(User user);
}
