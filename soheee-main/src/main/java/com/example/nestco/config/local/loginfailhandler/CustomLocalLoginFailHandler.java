package com.example.nestco.config.local.loginfailhandler;

import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.entity.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class CustomLocalLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userId = username;

        String errorMessage = null;
        String redirectUrl = null;

        Member member = memberRepository.findByUserId(userId);

        if(member == null) {
            errorMessage = URLEncoder.encode("등록되지않은 사용자입니다.", StandardCharsets.UTF_8.toString());
            redirectUrl = "/loginForm?error=true&message=" + errorMessage;
        } else if(!bCryptPasswordEncoder.matches(password, member.getUserPassword())) {
            errorMessage = URLEncoder.encode("비밀번호가 일치하지 않습니다.", StandardCharsets.UTF_8.toString());
            redirectUrl = "/loginForm?error=true&message=" + errorMessage;
        } else if (member.getBlock().equals("1")) {
            errorMessage = URLEncoder.encode("차단된 사용자입니다.", StandardCharsets.UTF_8.toString());
            redirectUrl = "/loginForm?error=true&message=" + errorMessage;
        }


        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
