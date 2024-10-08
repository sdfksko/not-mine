package com.example.nestco.models.entity;

import com.example.nestco.models.dto.NestcoForm;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NestcoItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // 등록글 ID

    @Column (nullable = false)
    private String title;   // 제목

    @Column (nullable = false)
    private String content; // 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;    // 아이템과 카테고리 다대일

    @ColumnDefault("0")
    private Boolean status; // 거래완료 여부

    @ColumnDefault("0")
    private int boardHits;  // 조회수

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createDate;   // 등록일

    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp updateDate;   // 수정일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member uploader;  // 업로더정보

    @Transient
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // 아이템에 연결된 썸네일 리스트 (OneToMany 관계 설정)
    @OneToMany(mappedBy = "items", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemThumbnail> thumbnails = new ArrayList<>();

    // 썸네일 추가 메서드
    public void addThumbnail(ItemThumbnail thumbnail) {
        this.thumbnails.add(thumbnail);
        thumbnail.setItems(this);
    }

    // 썸네일 제거 메서드
    public void removeThumbnail(ItemThumbnail thumbnail) {
        this.thumbnails.remove(thumbnail);
        thumbnail.setItems(null);
    }
    // 조회수 증가 메소드
    public void incrementHits() {
        this.boardHits++;
    }
    // 최근 등록 물품인지 확인
    public boolean isNew() {
        return createDate.toLocalDateTime().isAfter(LocalDateTime.now().minusDays(7));
    }

    public static NestcoItems toUploadEntity(NestcoForm form, Category category, Member uploader, List<String> imagePaths) {
        // null 방어 코드 추가
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        NestcoItems newItem = NestcoItems.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .category(category)
                .status(false)
                .boardHits(0)
                .uploader(uploader)
                .build();

        // 썸네일 리스트에 이미지 경로를 추가
        for (String imagePath : imagePaths) {
            ItemThumbnail thumbnail = new ItemThumbnail(newItem, imagePath);
            newItem.addThumbnail(thumbnail);
        }
        return newItem;
    }
}
