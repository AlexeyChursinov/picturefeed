package ru.chursinov.picturefeed.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chursinov.picturefeed.payload.request.LoginRequest;
import ru.chursinov.picturefeed.payload.request.SignUpRequest;
import ru.chursinov.picturefeed.payload.response.JWTTokenSuccessResponse;
import ru.chursinov.picturefeed.payload.response.MessageResponse;
import ru.chursinov.picturefeed.security.JWTTokenProvider;
import ru.chursinov.picturefeed.security.SecurityConstants;
import ru.chursinov.picturefeed.services.UserService;
import ru.chursinov.picturefeed.validations.ResponseErrorValidation;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {

    JWTTokenProvider jwtTokenProvider;
    AuthenticationManager authenticationManager;
    ResponseErrorValidation responseErrorValidation;
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult) {

        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generatedToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest,
                                               BindingResult bindingResult) {

        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        userService.createUser(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}
