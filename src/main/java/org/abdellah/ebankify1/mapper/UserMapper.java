package org.abdellah.ebankify1.mapper;

import org.abdellah.ebankify1.dto.UserDTO;
import org.abdellah.ebankify1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);

    void updateUserFromDTO(UserDTO userDTO, @MappingTarget User user);
}