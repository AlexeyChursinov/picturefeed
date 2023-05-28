package ru.chursinov.picturefeed.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    Long id;

    @NotEmpty
    String firstname;

    @NotEmpty
    String lastname;

    @NotEmpty
    String username;

    String bio;

}
