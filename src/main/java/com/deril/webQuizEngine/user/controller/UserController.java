package com.deril.webQuizEngine.user.controller;

import com.deril.webQuizEngine.user.controller.resource.UserData;
import com.deril.webQuizEngine.user.error.UserNameNotAvailableException;
import com.deril.webQuizEngine.user.mapper.UserMapper;
import com.deril.webQuizEngine.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class UserController {

  private final UserService userDetailsService;
  private final UserMapper mapper;

  @PostMapping(path = "/register")
  public HttpStatus createUser(@Valid @RequestBody UserData data) throws UserNameNotAvailableException {
    userDetailsService.createUser(mapper.toDomain(data));

    return HttpStatus.OK;
  }
}
