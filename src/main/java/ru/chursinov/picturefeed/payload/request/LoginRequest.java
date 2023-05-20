package ru.chursinov.picturefeed.payload.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotEmpty(message = "Username cannot be empty")
    String username;

    @NotEmpty(message = "Password cannot be empty")
    String password;

}
