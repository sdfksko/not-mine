package com.example.nestco.controller.admin;

import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.services.CategoryService;
import com.example.nestco.services.NestcoItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

//    @Autowired
//    CategoryService categoryService;

//    @Autowired
//    MemberRepository memberRepository;

//    @Autowired
//    NestcoItemsService nestcoItemsService;

    @GetMapping("")
    public String adminpage(Model model) {
        model.addAttribute("totalItems", 100);
        model.addAttribute("totalUsers", 50);
        model.addAttribute("newItems", 5);
        model.addAttribute("reportedItems", 2);
//        List<NestcoItems> recentItems = model.addAttribute("recentItems", recentItems);
        return "admin/adminPage";
    }
    // 회원 관리 페이지
//    @GetMapping("/users")
//    public String userManagement(@RequestParam(value = "query", required = false) String query, Model model) {
//        model.addAttribute("showSearchBar", true);
//        List<Member> members;
//        if (query!=null && !query.isEmpty()) {
//            members = memberRepository.findAllByUsername(query);
//        }else {
//            members = memberRepository.findAll();
//        }
//        model.addAttribute("users", memberRepository.findAll());
//        return "admin/userManagement";
//    }
    // 콘텐츠 관리 페이지
//    @GetMapping("/content")
//    public String contentManagement(Model model) {
//        model.addAttribute("showSearchBar", true);
//        model.addAttribute("contents", nestcoItemsService.getAllItemsSortedByDate());
//        return "admin/contentManagement";
//    }

    // 게시글 관리 페이지
//    @GetMapping("/posts")
//    public String postsManagement(Model model) {
//        model.addAttribute("showSearchBar", true);
//        model.addAttribute("posts", postService.getAllPosts());
//        return "admin/postsManagement";
//    }

    // 공지사항 관리 페이지
//    @GetMapping("/notices")
//    public String noticesManagement(Model model) {
//        model.addAttribute("showSearchBar", true);
//        model.addAttribute("notices", noticeService.getAllNotices());
//        return "admin/noticesManagement";
//    }


}
