package ru.chursinov.picturefeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chursinov.picturefeed.entity.PostEntity;
import ru.chursinov.picturefeed.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findAllByUserOrderByCreatedDateDesc(UserEntity user);

    List<PostEntity> findAllByOrderByCreatedDateDesc();

    Optional<PostEntity> findPostEntitiesByIdAndUser(Long id, UserEntity user);

}
