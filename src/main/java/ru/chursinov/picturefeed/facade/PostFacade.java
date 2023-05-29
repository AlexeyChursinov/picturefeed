package ru.chursinov.picturefeed.facade;

import org.springframework.stereotype.Component;
import ru.chursinov.picturefeed.dto.PostDTO;
import ru.chursinov.picturefeed.entity.PostEntity;

@Component
public class PostFacade {

    public PostDTO makePostDTO(PostEntity post) {

        return PostDTO.builder()
                .username(post.getUser().getUsername())
                .id(post.getId())
                .caption(post.getCaption())
                .likes(post.getLikes())
                .usersLiked(post.getLikedUsers())
                .location(post.getLocation())
                .title(post.getTitle())
                .build();
    }
}
