package com.javarush.matsarskaya.mapper;

import com.javarush.matsarskaya.dto.UserDTO;
import com.javarush.matsarskaya.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user){
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }

    public static User toEntity(UserDTO dto){
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }
}
