package ru.chursinov.picturefeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chursinov.picturefeed.entity.ImageEntity;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    Optional<ImageEntity> findByUserId(Long userId);

    Optional<ImageEntity> findByPostId(Long postId);

}
