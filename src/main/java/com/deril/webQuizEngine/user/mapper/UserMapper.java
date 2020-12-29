package com.deril.webQuizEngine.user.mapper;

import com.deril.webQuizEngine.config.MapperConfiguration;
import com.deril.webQuizEngine.user.controller.resource.UserData;
import com.deril.webQuizEngine.user.domain.User;
import com.deril.webQuizEngine.user.repository.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {

  User toDomain(UserEntity userEntity);

  UserEntity toEntity(User user);

  @Mapping(source = "email", target = "username")
  User toDomain(UserData data);
}
