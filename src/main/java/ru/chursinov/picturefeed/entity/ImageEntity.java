package ru.chursinov.picturefeed.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(columnDefinition = "bytea")
    byte[] imageBytes;

    @JsonIgnore
    Long userId;

    @JsonIgnore
    Long postId;

}
