package com.example.nestco.controller.admin;

import com.example.nestco.models.dto.SearchKeyDTO;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/contents")
public class AdminContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    public String contentList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                              @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(value = "searchKey", required = false) String searchKey) {

        SearchKeyDTO searchKeyDTO = new SearchKeyDTO();
        searchKeyDTO.setSearchKey(searchKey);

        Pageable newPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

        Page<NestcoItems> nestcoItemsList = contentService.nestcoItemsList(newPageable, searchKeyDTO);

        int totalPages = nestcoItemsList.getTotalPages();
        int currentPage = newPageable.getPageNumber() + 1;

        int start = Math.max(1, currentPage -2);
        int end = Math.min(totalPages, currentPage + 2);

        List<Integer> pageNumbers = new ArrayList<>();
        for(int i = start; i <= end; i ++) {
            pageNumbers.add(i);
        }

        int firstPage = 1;
        int lastPage = totalPages;

        model.addAttribute("contents", nestcoItemsList);
        model.addAttribute("prev", Math.max(1, currentPage-1));
        model.addAttribute("next", Math.min(totalPages, currentPage + 1));
        model.addAttribute("hasNext", nestcoItemsList.hasNext());
        model.addAttribute("hasPrev", nestcoItemsList.hasPrevious());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);

        return "admin/contentManagement";
    }

    @PostMapping("/delete/{id}")
    public String deleteContent(@PathVariable("id") Long id) {
        NestcoItems nestcoItems = contentService.delete(id);
        return "redirect:/admin/contents";
    }

}
