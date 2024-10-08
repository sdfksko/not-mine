package com.example.nestco.dao.repository;

import com.example.nestco.models.entity.Category;
import com.example.nestco.models.entity.NestcoItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 특정 부모 ID로 하위 카테고리 조회
    List<Category> findByParentId(Long parentId);

    // 카테고리 목록을 계층적 순서로 조회
    List<Category> findAllByOrderByDepthAscDisplayOrderAsc();

    // 특정 깊이(Depth)의 카테고리만 조회
    List<Category> findAllByDepthAndIsDisableFalse(int depth);
}