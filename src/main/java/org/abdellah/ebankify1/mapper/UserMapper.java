package org.abdellah.ebankify1.mapper;


import org.abdellah.ebankify1.dto.UserDTO;
import org.abdellah.ebankify1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}