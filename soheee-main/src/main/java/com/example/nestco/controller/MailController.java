package com.example.nestco.controller;


import com.example.nestco.models.dto.MailDTO;
import com.example.nestco.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping("/emailCheck") // 이 부분은 각자 바꿔주시면 됩니다.
    public String emailCheck(@RequestBody MailDTO mailDTO, HttpSession session) throws MessagingException, UnsupportedEncodingException {
        String authCode = mailService.sendSimpleMessage(mailDTO.getEmail(), session);
        return authCode; // Response body에 값을 반환
    }
}
