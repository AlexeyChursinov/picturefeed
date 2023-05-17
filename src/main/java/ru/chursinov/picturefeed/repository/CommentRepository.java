package ru.chursinov.picturefeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chursinov.picturefeed.entity.CommentEntity;
import ru.chursinov.picturefeed.entity.PostEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByPost(PostEntity post);

    CommentEntity findByIdAndUserId(Long commentId, Long userId);

}
