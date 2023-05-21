package ru.chursinov.picturefeed.payload.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.chursinov.picturefeed.annotations.PasswordMatches;
import ru.chursinov.picturefeed.annotations.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    String email;

    @NotEmpty(message = "Please enter your name")
    String firstname;

    @NotEmpty(message = "Please enter your lastname")
    String lastname;

    @NotEmpty(message = "Please enter your username")
    String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    String password;
    String confirmPassword;

}
