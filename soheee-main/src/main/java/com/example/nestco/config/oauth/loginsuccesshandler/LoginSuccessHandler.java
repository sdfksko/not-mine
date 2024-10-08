package com.example.nestco.config.oauth.loginsuccesshandler;


import com.example.nestco.models.entity.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        Member userEntity = (Member) session.getAttribute("newUser");

        if(userEntity != null) {
            session.removeAttribute("newUser");
            response.sendRedirect("/joinForm/social");
        } else {
            response.sendRedirect("/nestco");
        }
    }
}
