package com.example.nestco.services;

import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.models.entity.Member;
import com.example.nestco.dao.repository.NestcoItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NestcoItemsRepository nestcoItemsRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member socialSave(MemberDTO memberDTO) {
        String provider = memberDTO.getProvider();
        String name = memberDTO.getUsername();

        Member memberEntity = memberDTO.createMemberEntity(memberDTO);

        Member memberClone = new Member();
        List<Member> memberList = memberRepository.findAllByProvider(provider);

        for(Member item : memberList) {
            if(item.getUsername().equals(name)) {
                memberClone = item;
            }
        }

        memberClone.update(memberEntity);

        memberRepository.save(memberClone);


        return memberEntity;
    }

    public Member searchId(MemberDTO memberDTO) {
        String username = memberDTO.getUsername();
        String phoneNumber = memberDTO.getPhoneNumber();
        Member member = new Member();
        List<Member> memberList = memberRepository.findAllByUsername(username);
        for(Member item : memberList) {
            if(item.getPhoneNumber().equals(phoneNumber)) {
                member = item;
            }
        }
        return member;
    }
    // 회원이 업로드한 상품 목록 조회
    public List<NestcoItems> getUploadedItems(Member member) {
        return nestcoItemsRepository.findByUploader(member);
    }
}
