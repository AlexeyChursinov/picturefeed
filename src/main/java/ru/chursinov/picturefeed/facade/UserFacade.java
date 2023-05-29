package ru.chursinov.picturefeed.facade;

import org.springframework.stereotype.Component;
import ru.chursinov.picturefeed.dto.UserDTO;
import ru.chursinov.picturefeed.entity.UserEntity;

@Component
public class UserFacade {

    public UserDTO makeUserDTO(UserEntity user) {

        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getName())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .bio(user.getBio())
                .build();
    }
}
