package com.example.nestco.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItemThumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="thumbnail_id")
    private Long tumbnailId;    //PK

    @Column(name="image_path", nullable = false)
    private String imagePath;   //사진 파일 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private NestcoItems items;      //아이템과 다대일 관계

    @Column(name="created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createAt; //생성일

    public ItemThumbnail(NestcoItems items, String imagePath) {
        this.items = items;
        this.imagePath = imagePath;
        this.createAt = LocalDateTime.now();
    }
}
