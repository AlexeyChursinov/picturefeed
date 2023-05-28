package ru.chursinov.picturefeed.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDTO {

    Long id;

    @NotEmpty
    String title;

    @NotEmpty
    String caption;

    String location;
    String username;
    Integer likes;
    Set<String> usersLiked;

}
