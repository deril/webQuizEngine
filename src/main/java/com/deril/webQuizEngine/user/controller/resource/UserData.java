package com.deril.webQuizEngine.user.controller.resource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Value;

@Value
public class UserData {

  @NotNull
  @Pattern(regexp = ".+@.+\\..+")
  String email;

  @NotNull
  @Size(min = 5)
  String password;
}
