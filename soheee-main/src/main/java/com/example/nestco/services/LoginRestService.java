package com.example.nestco.services;

import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.models.entity.Member;
import com.example.nestco.dao.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginRestService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public LoginRestService(PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }
    public Member checkUserId(MemberDTO memberDTO) {
        Member member = memberRepository.findByUserId(memberDTO.getUserId());
        return member;
    }

    public Member checkNickname(MemberDTO memberDTO) {
        System.out.println("memberDTOId: " + memberDTO.getUserId());
        Member member = memberRepository.findByNickname(memberDTO.getNickname());
        return member;
    }


    public Member checkEmail(MemberDTO memberDTO) {
        Member member = memberRepository.findByEmail(memberDTO.getEmail());
        return member;
    }

    public Member checkPhoneNumber(MemberDTO memberDTO) {
        Member member = memberRepository.findByPhoneNumber(memberDTO.getPhoneNumber());
        return member;
    }

    public Member save(MemberDTO memberDTO) {

        String prevPassword = memberDTO.getUserPassword();
        String nextPassword = passwordEncoder.encode(prevPassword);

        memberDTO.setBlock("0");

        memberDTO.setUserPassword(nextPassword);

        System.out.println(memberDTO.toString());

        memberDTO.setRole("ROLE_USER");

        Member memberEntity = memberDTO.createMemberEntity(memberDTO);

        System.out.println(memberEntity.toString());

        memberRepository.save(memberEntity);
        return memberEntity;
    }

    public Member newPassword(MemberDTO memberDTO, HttpSession session) {
        Member memberEntity = (Member) session.getAttribute("user");
        System.out.println(memberEntity.toString());
        System.out.println(memberEntity);
        String prevPassword = memberDTO.getUserPassword();
        System.out.println(prevPassword);
        String nextPassword = passwordEncoder.encode(prevPassword);

        MemberDTO memberDTOClone = new MemberDTO();

        memberDTOClone.setUserId(memberEntity.getUserId());
        memberDTOClone.setUsername(memberEntity.getUsername());
        memberDTOClone.setUserPassword(nextPassword);
        memberDTOClone.setNickname(memberEntity.getNickname());
        memberDTOClone.setEmail(memberEntity.getEmail());
        memberDTOClone.setPhoneNumber(memberEntity.getPhoneNumber());
        memberDTOClone.setPostalAddress(memberEntity.getPostalAddress());
        memberDTOClone.setAddress(memberEntity.getAddress());
        memberDTOClone.setDetailedAddress(memberEntity.getDetailedAddress());
        memberDTOClone.setProvider(memberEntity.getProvider());
        memberDTOClone.setRole(memberEntity.getRole());
        memberDTOClone.setCreateDate(memberEntity.getCreateDate());

        System.out.println(memberDTOClone.toString());

        Member member = memberDTOClone.createMemberEntity(memberDTOClone);
        System.out.println(member.toString());
        memberEntity.update(member);
        System.out.println(memberEntity.toString());
        memberRepository.save(memberEntity);

        session.removeAttribute("user");

        return memberEntity;
    }
}
