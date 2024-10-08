package com.example.nestco.controller.admin;

import com.example.nestco.models.dto.SearchKeyDTO;
import com.example.nestco.models.entity.Member;
import com.example.nestco.services.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/members")
@Slf4j
public class AdminMemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String memberList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                             @PageableDefault(size = 5, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam(value = "searchKey", required = false) String searchKey,
                             @RequestParam(value = "idx", required = false) String idx) {
        SearchKeyDTO searchKeyDTO = new SearchKeyDTO();
        searchKeyDTO.setSearchKey(searchKey);

        if(idx == null) {
            Pageable newPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

            Page<Member> memberList = memberService.memberList(newPageable, searchKeyDTO);

            int totalPages = memberList.getTotalPages();
            int currentPage = newPageable.getPageNumber() + 1;

            int start = Math.max(1, currentPage -2);
            int end = Math.min(totalPages, currentPage + 2);

            List<Integer> pageNumbers = new ArrayList<>();
            for(int i = start; i <= end; i ++) {
                pageNumbers.add(i);
            }

            int firstPage = 1;
            int lastPage = totalPages;

            model.addAttribute("users", memberList);
            model.addAttribute("prev", Math.max(1, currentPage-1));
            model.addAttribute("next", Math.min(totalPages, currentPage + 1));
            model.addAttribute("hasNext", memberList.hasNext());
            model.addAttribute("hasPrev", memberList.hasPrevious());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("firstPage", firstPage);
            model.addAttribute("lastPage", lastPage);

            return "admin/userManagement";
        }

        Pageable newPageable = PageRequest.of(page - 1, pageable.getPageSize(), Sort.by("createDate").ascending());

        Page<Member> memberList = memberService.memberList(newPageable, searchKeyDTO);

        int totalPages = memberList.getTotalPages();
        int currentPage = newPageable.getPageNumber() + 1;

        int start = Math.max(1, currentPage -2);
        int end = Math.min(totalPages, currentPage + 2);

        List<Integer> pageNumbers = new ArrayList<>();
        for(int i = start; i <= end; i ++) {
            pageNumbers.add(i);
        }

        int firstPage = 1;
        int lastPage = totalPages;

        model.addAttribute("users", memberList);
        model.addAttribute("prev", Math.max(1, currentPage-1));
        model.addAttribute("next", Math.min(totalPages, currentPage + 1));
        model.addAttribute("hasNext", memberList.hasNext());
        model.addAttribute("hasPrev", memberList.hasPrevious());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("idx", idx);

        return "admin/userManagement";
    }


    @PostMapping("/block/{id}")
    public String block(@PathVariable("id") String id) {
        Member member = memberService.block(id);

        return "redirect:/admin/members";
    }

    @PostMapping("/unblock/{id}")
    public String unblock(@PathVariable("id") String id) {
        Member member = memberService.unblock(id);

        return "redirect:/admin/members";
    }
}
