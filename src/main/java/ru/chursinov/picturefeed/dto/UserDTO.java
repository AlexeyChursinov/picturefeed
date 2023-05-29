package ru.chursinov.picturefeed.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
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
