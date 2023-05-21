package ru.chursinov.picturefeed.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chursinov.picturefeed.entity.UserEntity;
import ru.chursinov.picturefeed.entity.enums.ERole;
import ru.chursinov.picturefeed.exceptions.UserExistException;
import ru.chursinov.picturefeed.payload.request.SignUpRequest;
import ru.chursinov.picturefeed.repository.UserRepository;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity createUser(SignUpRequest userIn) {
        UserEntity user = UserEntity.builder()
                .email(userIn.getEmail())
                .name(userIn.getFirstname())
                .lastname(userIn.getLastname())
                .username(userIn.getUsername())
                .password(passwordEncoder.encode(userIn.getPassword()))
                .build();
        user.getRole().add(ERole.ROLE_USER);

        try {
            log.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials.");
        }
    }
}