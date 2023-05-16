package ru.chursinov.picturefeed.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "image")
public class ImageEntity {

    Long id;

    String name;

    byte[] imageBytes;

    Long userId;

    Long postId;

}
