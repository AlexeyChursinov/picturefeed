package ru.chursinov.picturefeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chursinov.picturefeed.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntitiesByUsername(String username);

    Optional<UserEntity> findUserEntitiesByEmail(String email);

    Optional<UserEntity> findUserEntitiesById(Long id);

}
