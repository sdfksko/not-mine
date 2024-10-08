package com.example.nestco.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // 카테고리 이름 (대분류, 중분류, 소분류 모두 포함)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category parent;  // 부모 카테고리

    @ToString.Exclude
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();  // 하위 카테고리들

    @Column(nullable = false)
    private int depth;  // 0: 대분류, 1: 중분류 2: 소분류

    @Column
    @ColumnDefault("0")
    private int displayOrder;  // 카테고리 순서

    // soft delete를 위해 사용
    @Column
    private boolean isDisable = false;

    @Column
    private String icon;
}
