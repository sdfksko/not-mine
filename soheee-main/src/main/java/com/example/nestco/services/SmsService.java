package com.example.nestco.services;

import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.dto.SmsRequestDto;
import com.example.nestco.models.entity.Member;
import com.example.nestco.smsutil.SmsCertificationUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Autowired
    private SmsCertificationUtil smsCertificationUtil;

    @Autowired
    private MemberRepository memberRepository;

    public String sendSms(SmsRequestDto smsRequestDto) {
        String phoneNum = smsRequestDto.getPhoneNum();
        String certificationCode = Integer.toString((int)(Math.random() * (999999 - 100000 +1)) + 100000);
        smsCertificationUtil.sendSMS(phoneNum, certificationCode);
        return certificationCode;
    }

    public Member checkPhoneNum(SmsRequestDto smsRequestDto) {
        System.out.println(smsRequestDto.getPhoneNum());
        Member member = memberRepository.findByPhoneNumber(smsRequestDto.getPhoneNum());
        if( member == null) {
            return null;
        }
        return member;
    }

    public Member searchId(SmsRequestDto smsRequestDto, HttpSession session) {
        Member member = memberRepository.findByPhoneNumber(smsRequestDto.getPhoneNum());
        return member;
    }
}
