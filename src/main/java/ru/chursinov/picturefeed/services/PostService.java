package ru.chursinov.picturefeed.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chursinov.picturefeed.dto.PostDTO;
import ru.chursinov.picturefeed.entity.ImageEntity;
import ru.chursinov.picturefeed.entity.PostEntity;
import ru.chursinov.picturefeed.entity.UserEntity;
import ru.chursinov.picturefeed.exceptions.PostNotFoundException;
import ru.chursinov.picturefeed.repository.ImageRepository;
import ru.chursinov.picturefeed.repository.PostRepository;
import ru.chursinov.picturefeed.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostService {

    PostRepository postRepository;
    UserRepository userRepository;
    ImageRepository imageRepository;

    @Transactional
    public PostEntity createPost(PostDTO postDTO, Principal principal) {
        UserEntity user = getUserByPrincipal(principal);
        PostEntity post = new PostEntity();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);

        log.info("Saving Post for User: {}", user.getEmail());
        return postRepository.save(post);
    }

    @Transactional
    public List<PostEntity> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public PostEntity getPostById(Long postId, Principal principal) {
        UserEntity user = getUserByPrincipal(principal);
        return postRepository.findPostEntitiesByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: " + user.getEmail()));
    }

    @Transactional
    public List<PostEntity> getAllPostForUser(Principal principal) {
        UserEntity user = getUserByPrincipal(principal);
        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    @Transactional
    public PostEntity likePost(Long postId, String username) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));

        Optional<String> userLiked = post.getLikedUsers()
                .stream()
                .filter(u -> u.equals(username)).findAny();

        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, Principal principal) {
        PostEntity post = getPostById(postId, principal);
        Optional<ImageEntity> imageModel = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);
    }

    private UserEntity getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserEntitiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }

}
