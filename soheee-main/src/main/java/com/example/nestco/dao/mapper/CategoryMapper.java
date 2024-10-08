package com.example.nestco.dao.mapper;

import com.example.nestco.models.dto.CategoryDTO;
import com.example.nestco.models.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    // Category 엔티티를 CategoryDTO로 변환 (선택된 카테고리를 처리)
    public static CategoryDTO toDTO(Category category) {
        List<CategoryDTO> childrenDTOs = category.getChildren() != null ?
                category.getChildren().stream().map(CategoryMapper::toDTO).collect(Collectors.toList()) :
                new ArrayList<>();

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getParent() != null ? category.getParent().getId() : null,  // 부모 ID
                category.getDepth(),
                category.getIcon(),
                category.isDisable(),
                category.getDisplayOrder(),
                childrenDTOs
        );
    }
    // CategoryDTO를 Category 엔티티로 변환
    public static Category fromDTO(CategoryDTO categoryDTO, Category parent) {
        return Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .parent(parent)  // 부모 카테고리 설정
                .depth(categoryDTO.getDepth())
                .icon(categoryDTO.getIcon())
                .isDisable(categoryDTO.isDisable())
                .displayOrder(categoryDTO.getDisplayOrder())
                .build();
    }
}
