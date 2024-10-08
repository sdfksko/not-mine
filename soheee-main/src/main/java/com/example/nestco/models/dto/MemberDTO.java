package com.example.nestco.models.dto;

import com.example.nestco.models.entity.Member;
import lombok.Data;

@Data
public class MemberDTO {
    private String userId;
    private String userPassword;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer postalAddress;
    private String address;
    private String detailedAddress;
    private String provider;
    private String role;
    private String createDate;
    private String block;

    public Member createMemberEntity(MemberDTO memberDTO) {
        return new Member(userId, userPassword, username, nickname, email, phoneNumber, postalAddress, address, detailedAddress, provider, role, createDate, block);
    }
}


