package ru.chursinov.picturefeed.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chursinov.picturefeed.dto.CommentDTO;
import ru.chursinov.picturefeed.entity.CommentEntity;
import ru.chursinov.picturefeed.entity.PostEntity;
import ru.chursinov.picturefeed.entity.UserEntity;
import ru.chursinov.picturefeed.exceptions.PostNotFoundException;
import ru.chursinov.picturefeed.repository.CommentRepository;
import ru.chursinov.picturefeed.repository.PostRepository;
import ru.chursinov.picturefeed.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentService {

    CommentRepository commentRepository;
    PostRepository postRepository;
    UserRepository userRepository;

    @Transactional
    public CommentEntity saveComment(Long postId, CommentDTO commentDTO, Principal principal) {
        UserEntity user = getUserByPrincipal(principal);
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: " + user.getEmail()));

        CommentEntity comment = new CommentEntity();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        log.info("Saving comment for Post: {}", post.getId());
        return commentRepository.save(comment);
    }

    @Transactional
    public List<CommentEntity> getAllCommentsForPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));

        return commentRepository.findAllByPost(post);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }


    private UserEntity getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserEntitiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

}
