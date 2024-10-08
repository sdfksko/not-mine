package com.example.nestco.controller;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.models.dto.CategoryDTO;
import com.example.nestco.models.entity.ItemThumbnail;
import com.example.nestco.services.CategoryService;
import com.example.nestco.services.ItemThumbnailService;
import com.example.nestco.services.LoginService;
import com.example.nestco.services.NestcoItemsService;
import com.example.nestco.models.entity.NestcoItems;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NestcoItemsService nestcoItemsService;
    private final CategoryService categoryService;
    private final ItemThumbnailService itemThumbnailService;

    /**
     * 비로그인 유저도 사용 가능한 기능 (카테고리별, 검색 조회)
     */
    @GetMapping("/nestco")
    public String showHomePage(@RequestParam(required = false) Long categoryId,
                               @RequestParam(required = false) String searchQuery,
                               Model model,
                               @AuthenticationPrincipal LocalUserDetails localUserDetails) {

        model.addAttribute("nickname", localUserDetails != null ? localUserDetails.getMemberEntity().getNickname() : null);
        List<NestcoItems> items = getItemsBasedOnCategoryAndSearch(categoryId, searchQuery);

        // 아이템 ID 리스트 생성
        List<Long> itemIds = items.stream().map(NestcoItems::getId).collect(Collectors.toList());

        // 썸네일 경로를 한번에 조회
        Map<Long, String> thumbnailMap = itemThumbnailService.getFirstThumbnailsForItems(itemIds);

        // 아이템에 썸네일 경로 설정
        for (NestcoItems item : items) {
            item.setImagePath(thumbnailMap.get(item.getId()));
        }
        List<CategoryDTO> categories = categoryService.getCategoryTree(null);
        model.addAttribute("categories", categories);
        model.addAttribute("items", items);
        return "nestcoes/home";  // home.mustache와 연결
    }
    private List<NestcoItems> getItemsBasedOnCategoryAndSearch(Long categoryId, String searchQuery) {
        if (categoryId != null && searchQuery != null && !searchQuery.isEmpty()) {
            return nestcoItemsService.searchItemsByCategoryAndTitle(categoryId, searchQuery);
        } else if (searchQuery != null && !searchQuery.isEmpty()) {
            return nestcoItemsService.searchItemsByTitle(searchQuery);
        } else if (categoryId != null) {
            return nestcoItemsService.getItemsByCategory(categoryId);
        } else {
            return nestcoItemsService.getAllItemsSortedByDate();
        }
    }

    // 하위 카테고리 불러오기
    @GetMapping("/categories/subcategories")
    @ResponseBody
    public List<CategoryDTO> getSubCategories(@RequestParam Long parentId) {
        return categoryService.getSubCategories(parentId);
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/nestco";  // 로그아웃 처리 후 로그인 페이지로 리다이렉트
    }
}

//
//    @GetMapping
//    public String showMyPage(@AuthenticationPrincipal Member member, Model model) {
//        model.addAttribute("member", member);
//        model.addAttribute("uploadedItems", memberService.getUploadedItems(member));
//        return "mypage";  // mypage.mustache와 연결
//    }









