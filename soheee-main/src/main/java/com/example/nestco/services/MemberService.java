package com.example.nestco.services;

import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.models.dto.SearchKeyDTO;
import com.example.nestco.models.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public Page<Member> memberList(Pageable pageable, SearchKeyDTO searchKeyDTO) {
        String searchKey = searchKeyDTO.getSearchKey();
        if(searchKey != null) {
            Page<Member> memberList = memberRepository.findAllByQuery(pageable, searchKey);
            return  memberList;
        }
        Page<Member> memberList = memberRepository.findAll(pageable);

        return memberList;
    }

    public Member block(String id) {
        Member member = memberRepository.findByUserId(id);

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId(member.getUserId());
        memberDTO.setUserPassword(member.getUserPassword());
        memberDTO.setUsername(member.getUsername());
        memberDTO.setNickname(member.getNickname());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhoneNumber(member.getPhoneNumber());
        memberDTO.setPostalAddress(member.getPostalAddress());
        memberDTO.setAddress(member.getAddress());
        memberDTO.setDetailedAddress(member.getDetailedAddress());
        memberDTO.setProvider(member.getProvider());
        memberDTO.setRole(member.getRole());
        memberDTO.setCreateDate(member.getCreateDate());
        memberDTO.setBlock("1");

        Member memberClone = memberDTO.createMemberEntity(memberDTO);

        member.update(memberClone);

        memberRepository.save(member);

        return memberClone;
    }

    public Member unblock(String id) {
        Member member = memberRepository.findByUserId(id);

        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setUserId(member.getUserId());
        memberDTO.setUserPassword(member.getUserPassword());
        memberDTO.setUsername(member.getUsername());
        memberDTO.setNickname(member.getNickname());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhoneNumber(member.getPhoneNumber());
        memberDTO.setPostalAddress(member.getPostalAddress());
        memberDTO.setAddress(member.getAddress());
        memberDTO.setDetailedAddress(member.getDetailedAddress());
        memberDTO.setProvider(member.getProvider());
        memberDTO.setRole(member.getRole());
        memberDTO.setCreateDate(member.getCreateDate());
        memberDTO.setBlock("0");

        Member memberClone = memberDTO.createMemberEntity(memberDTO);

        member.update(memberClone);

        memberRepository.save(member);

        return memberClone;
    }
}
