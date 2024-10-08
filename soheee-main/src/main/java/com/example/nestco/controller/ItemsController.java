package com.example.nestco.controller;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.models.dto.CategoryDTO;
import com.example.nestco.models.dto.NestcoForm;
import com.example.nestco.models.entity.Category;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.services.CategoryService;
import com.example.nestco.services.ItemThumbnailService;
import com.example.nestco.services.NestcoItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 물품 등록, 조회, 카테고리별 아이템 조회 컨트롤러
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemsController {

    private final NestcoItemsService nestcoItemsService;
    private final CategoryService categoryService;
    private final ItemThumbnailService itemThumbnailService;

    /**
     * 물품 등록 폼 이동 (로그인한 사용자만).
     */
    @GetMapping("/upload")
    public String showUploadForm(@AuthenticationPrincipal LocalUserDetails localUserDetails, Model model) {
        if (localUserDetails != null) {
            List<CategoryDTO> parentCategories = categoryService.getTopCategories(); // 대분류 카테고리만 가져오기
            model.addAttribute("parentCategories", parentCategories);
            model.addAttribute("nestcoForm", new NestcoForm());
            return "logined/uploadForm";
        }
        return "redirect:/loginForm";
    }

    // 물품 업로드 처리
    @PostMapping("/upload")
    public String uploadItem(@ModelAttribute NestcoForm form,
                             @AuthenticationPrincipal LocalUserDetails localUserDetails,
                             @RequestParam("images") List<MultipartFile> images) throws IOException {

        nestcoItemsService.uploadItem(form, images);
        return "redirect:/nestco";
    }

    /**
     * 물품 상세 정보 조회.
     */
    @GetMapping("/{id}")
    public String viewItemDetail(@PathVariable ("id") Long itemId, Model model) {
        NestcoItems item = nestcoItemsService.getItemById(itemId);
        model.addAttribute("item", item);

        List<Long> itemIds = List.of(itemId);
        // 썸네일 이미지 경로를 가져오는 로직 추가
        Map<Long, String> thumbnailsMap = itemThumbnailService.getFirstThumbnailsForItems(itemIds);
        String imagePath = thumbnailsMap.get(itemId);  // 해당 아이템의 썸네일 경로 가져오기
        item.setImagePath(imagePath);
        model.addAttribute("imagePath", imagePath);

        return "/nestcoes/itemDetail";
    }

    /**
     * 카테고리별 물품 목록 조회.
     */
    @GetMapping("/category/{categoryId}")
    @ResponseBody
    public List<NestcoItems> getItemsByCategory(@PathVariable Long categoryId) {
        return nestcoItemsService.getItemsByCategory(categoryId);
    }
    // 대분류 카테고리 가져오기
    @GetMapping("/top")
    @ResponseBody
    public List<CategoryDTO> getTopCategories() {
        return categoryService.getTopCategories();
    }

    // 중분류 카테고리 가져오기 (대분류 선택 시)
    @GetMapping("/middle/{parentId}")
    @ResponseBody
    public List<CategoryDTO> getMiddleCategories(@PathVariable Long parentId) {
        return categoryService.getMiddleCategories(parentId);
    }

    // 소분류 카테고리 가져오기 (중분류 선택 시)
    @GetMapping("/child/{parentId}")
    @ResponseBody
    public List<CategoryDTO> getChildCategories(@PathVariable Long parentId) {
        return categoryService.getChildCategories(parentId);
    }
}


