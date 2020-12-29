package com.deril.webQuizEngine.user.service;

import com.deril.webQuizEngine.user.domain.User;
import com.deril.webQuizEngine.user.error.UserNameNotAvailableException;
import com.deril.webQuizEngine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
  }

  public void createUser(User user) throws UserNameNotAvailableException {
    var existingUser = repository.findByUsername(user.getUsername());
    if (existingUser.isPresent()) {
      throw new UserNameNotAvailableException();
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    repository.save(user);
  }
}
