package ru.chursinov.picturefeed.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chursinov.picturefeed.dto.UserDTO;
import ru.chursinov.picturefeed.entity.UserEntity;
import ru.chursinov.picturefeed.entity.enums.ERole;
import ru.chursinov.picturefeed.exceptions.UserExistException;
import ru.chursinov.picturefeed.payload.request.SignupRequest;
import ru.chursinov.picturefeed.repository.UserRepository;

import java.security.Principal;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity createUser(SignupRequest userIn) {
        UserEntity user = new UserEntity();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER);

        try {
            log.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials.");
        }
    }

    @Transactional
    public UserEntity updateUser(UserDTO userDTO, Principal principal) {

        UserEntity user = getUserByPrincipal(principal);
        user.setName(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public UserEntity getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private UserEntity getUserByPrincipal(Principal principal) {

        String username = principal.getName();

        return userRepository.findUserEntitiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }

}
