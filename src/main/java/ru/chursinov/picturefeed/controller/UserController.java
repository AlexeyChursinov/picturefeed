package ru.chursinov.picturefeed.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chursinov.picturefeed.dto.UserDTO;
import ru.chursinov.picturefeed.entity.UserEntity;
import ru.chursinov.picturefeed.facade.UserFacade;
import ru.chursinov.picturefeed.services.UserService;
import ru.chursinov.picturefeed.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/user")
@CrossOrigin
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;
    UserFacade userFacade;
    ResponseErrorValidation responseErrorValidation;

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {

        UserEntity user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.makeUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {

        UserEntity user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userFacade.makeUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            log.error(String.valueOf(errors));
            return errors;
        }

        UserEntity user = userService.updateUser(userDTO, principal);
        UserDTO userUpdated = userFacade.makeUserDTO(user);

        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
}
