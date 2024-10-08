package com.example.nestco.services;


import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.models.dto.NestcoForm;
import com.example.nestco.models.entity.Category;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.dao.repository.NestcoItemsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class NestcoItemsService {

    private final NestcoItemsRepository nestcoItemsRepository;
    private final CategoryService categoryService;
    private final ItemThumbnailService itemThumbnailService;

    // 상품 업로드
    public NestcoItems uploadItem(NestcoForm form, List<MultipartFile> images) throws IOException{
        LocalUserDetails currentUser = getCurrentUser();
        Member uploader = currentUser.getMemberEntity();  // LocalUserDetails에서 Member 객체 추출

        // 카테고리 정보 가져오기
        Category category = categoryService.getCategoryEntityById(form.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("유효하지 않은 카테고리 ID입니다.");
        }
        // 상품 엔티티 생성
        NestcoItems newItem = NestcoItems.toUploadEntity(form, category, uploader, new ArrayList<>());

        // 상품 저장
        NestcoItems savedItem = nestcoItemsRepository.save(newItem);

        // 이미지 파일 처리 및 썸네일 생성
        if (!images.isEmpty()) {
            itemThumbnailService.uploadThumbnail(savedItem, images);
        } else {
            throw new IllegalArgumentException("이미지가 없습니다. 이미지를 업로드해주세요.");
        }

        return savedItem;
    }

    // 최신 상품 목록 조회
    public List<NestcoItems> getAllItemsSortedByDate() {
        return nestcoItemsRepository.findAllByOrderByCreateDateDesc();
    }

    // 카테고리별 상품 조회
    public List<NestcoItems> getItemsByCategory(Long categoryId) {
        Category category = categoryService.getCategoryEntityById(categoryId);
        return nestcoItemsRepository.findByCategory(category);
    }

    // 검색어로 상품 조회
    public List<NestcoItems> searchItemsByTitle(String title) {
        return nestcoItemsRepository.findByTitleContaining(title);
    }

    // 카테고리와 검색어로 상품 조회
    public List<NestcoItems> searchItemsByCategoryAndTitle(Long categoryId, String title) {
        Category category = categoryService.getCategoryEntityById(categoryId);
        return nestcoItemsRepository.findByCategoryAndTitleContaining(category, title);
    }

    // 거래 완료되지 않은 상품 조회
    public List<NestcoItems> getAvailableItems() {
        return nestcoItemsRepository.findByStatusFalse();
    }

    // 상품 ID로 조회
    public NestcoItems getItemById(Long itemId) {
        return nestcoItemsRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    private LocalUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LocalUserDetails) {
            return (LocalUserDetails) principal;
        } else {
            throw new IllegalStateException("사용자가 인증되지 않았습니다.");
        }
    }
}