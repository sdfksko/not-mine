package com.example.nestco.controller.api;

import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.services.LoginRestService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginRestController {

    @Autowired
    private LoginRestService loginRestService;

    @PostMapping("/check/userId")
    public ResponseEntity<String> userId(@RequestBody MemberDTO memberDTO) {
        if (loginRestService.checkUserId(memberDTO) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다. 다른 아이디를 입력해주십시오.");
        }
        return ResponseEntity.ok("사용 가능한 아이디입니다.");
    }

    @PostMapping("/check/nickname")
    public ResponseEntity<String> checkNickname(@RequestBody MemberDTO memberDTO) {
        if (loginRestService.checkNickname(memberDTO) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임입니다. 다른 닉네임을 입력해주십시오.");
        }
        return ResponseEntity.ok("사용 가능한 닉네임입니다.");
    }

    @PostMapping("/check/email")
    public ResponseEntity<String> checkEmail(@RequestBody MemberDTO memberDTO) {
        if (loginRestService.checkEmail(memberDTO) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다. 다른 이메일을 입력해주십시오.");
        }
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }

    @PostMapping("/check/phoneNumber")
    public ResponseEntity<String> checkPhoneNumber(@RequestBody MemberDTO memberDTO) {
        if (loginRestService.checkPhoneNumber(memberDTO) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 휴대폰 번호입니다. 다른 휴대폰 번호를 입력해주십시오.");
        }
        return ResponseEntity.ok("사용 가능한 휴대폰 번호입니다.");
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody  MemberDTO memberDTO) {
        if(loginRestService.save(memberDTO) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("회원가입에 실패하였습니다.");
        }
        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }

    @PostMapping("/newPassword")
    public ResponseEntity<String> newPass(@RequestBody MemberDTO memberDTO, HttpSession session) {
        if(loginRestService.newPassword(memberDTO, session) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("새 비밀번호 생성에 실패하였습니다.");
        }
        return ResponseEntity.ok("새 비밀번호 생성에 성공하였습니다.");
    }
}
