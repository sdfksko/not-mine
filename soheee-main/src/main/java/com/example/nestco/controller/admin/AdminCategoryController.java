package com.example.nestco.controller.admin;

import com.example.nestco.models.dto.CategoryDTO;
import com.example.nestco.models.entity.Category;
import com.example.nestco.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    // 카테고리 관리 페이지
    @GetMapping
    public String categoryManagement(@RequestParam(value = "parentCategory", required = false) Long parentCategoryId,Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryTree;
        if (parentCategoryId != null) {
            // 특정 대분류에 속한 카테고리만 필터링
            categoryTree = categoryService.getSubCategories(parentCategoryId);
        } else {
            // 전체 카테고리 트리 가져오기
            categoryTree = categoryService.getCategoryTree(null);
        }
        List<CategoryDTO> topCategories = categoryService.getTopCategories(); // 대분류 리스트 가져오기
        model.addAttribute("topCategories", topCategories);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryTree", categoryTree);
        return "admin/categoryManagement";  // categoryManagement.mustache 파일 필요
    }
    // 카테고리 추가 페이지로 이동
    @GetMapping("/add")
    public String addCategoryPage(Model model, @RequestParam(value = "selectedParentId", required = false) Long selectedParentId) {
        List<CategoryDTO> categoryTree = categoryService.getCategoryTree(selectedParentId);  // 계층 구조로 된 카테고리 트리 가져오기
        model.addAttribute("categoryTree", categoryTree);  // 트리 구조 카테고리 전달
        model.addAttribute("categoryDTO", new CategoryDTO());  // 빈 DTO 전달
        return "admin/addCategory";
    }

    // 카테고리 추가 처리
    @PostMapping("/add")
    public String addCategory(@ModelAttribute CategoryDTO categoryDTO) {
        log.info("parentId: {}", categoryDTO.getParentId());
        if (categoryDTO.getParentId() != null) {
            // 2차 카테고리 선택 시, 해당 2차 카테고리를 부모로 하여 3차 카테고리로 추가
            categoryDTO.setDepth(2);  // 3차 카테고리(소분류) 깊이 설정
        } else {
            // 대분류나 중분류로 추가할 경우
            categoryDTO.setDepth(categoryDTO.getParentId() == null ? 0 : 1);  // 깊이 설정
        }
        categoryService.createCategory(categoryDTO);
        return "redirect:/admin/categories"; // 생성 후 목록 페이지로 이동
    }

    @GetMapping("/subcategories")
    @ResponseBody
    public List<CategoryDTO> getSubCategories(@RequestParam Long parentId) {
        return categoryService.getSubCategories(parentId);  // 선택된 부모 카테고리에 따른 중분류(2차 카테고리) 목록 반환
    }

    // 카테고리 수정 페이지로 이동
    @GetMapping("/edit/{id}")
    public String updateCategoryForm(@PathVariable Long id, Model model) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        List<CategoryDTO> categoryTree = categoryService.getCategoryTree(categoryDTO.getParentId());  // 선택된 부모 카테고리를 반영한 트리 가져오기

        model.addAttribute("categoryDTO", categoryDTO);  // 수정할 카테고리 데이터
        model.addAttribute("categoryTree", categoryTree);  // 트리 구조
        return "admin/editCategory";
    }

    // 카테고리 수정 처리
    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute CategoryDTO categoryDTO) {
        if (categoryDTO.getParentId() != null) {
            categoryDTO.setDepth(2);  // 3차 카테고리(소분류)
        } else {
            categoryDTO.setDepth(categoryDTO.getParentId() == null ? 0 : 1);  // 1차 또는 2차 카테고리
        }

        categoryService.updateCategory(id, categoryDTO);
        return "redirect:/admin/categories"; // 카테고리 목록으로 리다이렉트
    }
    // Soft delete 처리
    @PostMapping("/softDelete/{id}")
    public String softDeleteCategory(@PathVariable Long id) {
        categoryService.softDeleteCategory(id);
        return "redirect:/admin/categories";
    }

    // 카테고리 삭제 처리
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories"; // 카테고리 목록으로 리다이렉트
    }

//    @GetMapping("/secondCategories")
//    @ResponseBody
//    public List<CategoryDTO> getSecondCategories(@RequestParam Long parentId) {
//        return categoryService.getSubCategories(parentId);
//    }
//
//    @GetMapping("/thirdCategories")
//    @ResponseBody
//    public List<CategoryDTO> getThirdCategories(@RequestParam Long secondId) {
//        return categoryService.getSubCategories(secondId);
//    }



//    // 카테고리 수정 페이지 이동
//    @GetMapping("/edit/{id}")
//    public String editCategoryPage(@PathVariable Long id, Model model) {
//        CategoryDTO category = categoryService.getCategoryById(id);
//
//        // 부모 카테고리 선택을 위해 전체 카테고리 목록 전달
//        List<CategoryDTO> categories = categoryService.getCategoryHierarchy();
//
//        model.addAttribute("category", category); // 수정할 카테고리 정보 전달
//        model.addAttribute("categories", categories); // 부모 카테고리 목록 전달
//        return "admin/editCategory";  // 카테고리 수정 템플릿으로 이동
//    }
//    // 카테고리 수정 처리
//    @PostMapping("/edit/{id}")
//    public String updateCategory(@PathVariable Long id, @ModelAttribute CategoryDTO categoryDTO) {
//        // Service 계층에 수정 요청
//        categoryService.updateCategory(id, categoryDTO);
//        return "redirect:/admin/categories"; // 수정 후 목록 페이지로 리다이렉트
//    }
//
//    // 카테고리 삭제처리
//    @PostMapping("/delete/{id}")
//    public String deleteCategory(@PathVariable Long id) {
//        categoryService.softDeleteCategory(id);
//        return "redirect:/admin/categories";
//    }
}


