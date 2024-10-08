package com.example.nestco.controller;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.config.local.LocalUserDetailsService;
import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.models.entity.Member;
import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.services.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LocalUserDetailsService localUserDetailsService;

    HttpSession session;

    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(name="message", required = false) String message, Model model) {
        model.addAttribute("message", message);

        return "login/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "login/joinForm";
    }

    @GetMapping("/joinForm/social")
    public String socialJoinForm(Model model, @AuthenticationPrincipal LocalUserDetails localUserDetails) {
        Member memberEntity = localUserDetails.getMemberEntity();

        String email = memberEntity.getUserId();
        String username = memberEntity.getUsername();
        String provider = memberEntity.getProvider();

        model.addAttribute("email", email);
        model.addAttribute("username", username);
        model.addAttribute("provider", provider);

        return "socialJoinForm";
    }

//    @GetMapping("/joinForm/social")
//    public String socialJoinForm(Model model, HttpSession session) {
//        User memberEntity = (User) session.getAttribute("newUser");
//
//        String email = memberEntity.getUserId();
//        String username = memberEntity.getUsername();
//        String provider = memberEntity.getProvider();
//
//        model.addAttribute("email", email);
//        model.addAttribute("username", username);
//        model.addAttribute("provider", provider);
//
//        return "socialJoinForm";
//    }


    @PostMapping("/join/social")
    public String socialJoin(MemberDTO memberDTO) {
        Member memberEntity = loginService.socialSave(memberDTO);
        return "redirect:/myPage";
    }

    @GetMapping("/search/id")
    public String searchId() {
        return "searchId";
    }

    @PostMapping("/searchId")
    public String search(MemberDTO memberDTO, HttpSession session) {
        Member memberEntity = loginService.searchId(memberDTO);
        String userId = memberEntity.getUserId();
        session.setAttribute("id", userId);
        String id = (String) session.getAttribute("id");
        System.out.println(id);
        return "redirect:/show/id";
    }

    @GetMapping("/show/id")
    public String showId(Model model,  HttpSession session) {
        String userId = (String) session.getAttribute("id");
        session.removeAttribute("id");
        model.addAttribute("userId", userId);
        return "showId";
    }

    @GetMapping("/search/password")
    public String searchPassword() {
        return "searchPassword";
    }

    @GetMapping("/new/password")
    public String newPassword() {
        return "newPassword";
    }

    @GetMapping("/sms")
    public String sms() {
        return "sms";
    }

    @GetMapping("/show/searchId")
    public String showSearchId(Model model, HttpSession session) {
        String searchId = (String) session.getAttribute("searchNewId");
        session.removeAttribute("searchNewId");
        model.addAttribute("searchId", searchId);

        return "showSearchId";
    }

    @GetMapping("/myPage")
    public String myPage() {
        return "myPage";
    }


    @GetMapping("/pay")
    public String pay() {
        return "pay";
    }
}
