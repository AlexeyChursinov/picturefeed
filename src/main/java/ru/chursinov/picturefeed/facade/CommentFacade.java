package ru.chursinov.picturefeed.facade;

import org.springframework.stereotype.Component;
import ru.chursinov.picturefeed.dto.CommentDTO;
import ru.chursinov.picturefeed.entity.CommentEntity;

@Component
public class CommentFacade {

    public CommentDTO makeCommentDTO(CommentEntity comment) {

        return CommentDTO.builder()
                .id(comment.getId())
                .username(comment.getUsername())
                .message(comment.getMessage())
                .build();
    }
}
