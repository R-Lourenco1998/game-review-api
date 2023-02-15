package com.gamereview.api.mapper;

import com.gamereview.api.entities.User;
import com.gamereview.api.entities.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toEntity(UserDTO userDTO);
    UserDTO toDTO(User user);
}
