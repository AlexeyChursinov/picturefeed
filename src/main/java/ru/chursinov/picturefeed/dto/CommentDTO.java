package ru.chursinov.picturefeed.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class CommentDTO {

    private Long id;

    @NotEmpty
    private String message;

    private String username;

}
