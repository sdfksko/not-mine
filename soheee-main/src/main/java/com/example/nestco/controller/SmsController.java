package com.example.nestco.controller;

import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.models.dto.SmsRequestDto;
import com.example.nestco.models.entity.Member;
import com.example.nestco.services.SmsService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<String> SendSMS(@RequestBody @Validated SmsRequestDto smsRequestDto) {
        String checkNumeber = smsService.sendSms(smsRequestDto);
        System.out.println(checkNumeber);
        return ResponseEntity.ok(checkNumeber);
    }

    @PostMapping("/check")
    public ResponseEntity<String> phoneNumCheck(@RequestBody SmsRequestDto smsRequestDto) {
        if(smsService.checkPhoneNum(smsRequestDto) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("등록되지 않은 휴대폰 번호입니다. 다시 입력해주십시오.");
        }
        return ResponseEntity.ok("인증번호를 전송했습니다.");
    }

    @PostMapping("/searchId")
    public ResponseEntity<String> searchId(@RequestBody SmsRequestDto smsRequestDto, HttpSession session) {
        Member member = smsService.searchId(smsRequestDto, session);
        String searchId = member.getUserId();
        if(searchId == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("아이디 찾기에 실패하였습니다.");
        }
        session.setAttribute("searchNewId", searchId);
        return ResponseEntity.ok(searchId);
    }
}
