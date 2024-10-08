package com.example.nestco.config.oauth;


import com.example.nestco.config.local.LocalUserDetails;

import com.example.nestco.config.oauth.select.GoogleUserInfo;
import com.example.nestco.config.oauth.select.KakaoUserInfo;
import com.example.nestco.config.oauth.select.NaverUserInfo;
import com.example.nestco.config.oauth.select.OAuth2UserInfo;
import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.models.entity.Member;
import com.example.nestco.dao.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    HttpSession session;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
        System.out.println("getAttributes: " + oAuth2User.getAttributes());

        String username = null;
        String email = null;
        String provider = null;
        String role = "ROLE_USER";

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
            username = oAuth2UserInfo.getName();
            email = oAuth2UserInfo.getEmail();
            provider = oAuth2UserInfo.getProvider();
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
            username = oAuth2UserInfo.getName();
            email = oAuth2UserInfo.getEmail();
            provider = oAuth2UserInfo.getProvider();
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
            provider = oAuth2UserInfo.getProvider();
            email = oAuth2UserInfo.getProviderId();
            oAuth2UserInfo = new KakaoUserInfo((Map) oAuth2User.getAttributes().get("properties"));
            username = oAuth2UserInfo.getName();
            System.out.println(username);
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        List<Member> memberEntityList = memberRepository.findAllByUsername(username);

        List<Member> memberEnList = memberRepository.findAllByProvider(provider);

        MemberDTO memberDTO = new MemberDTO();

        Member memberEntity = new Member();

        for(Member member : memberEnList) {
            if(member.getProvider().equals(provider)) {
                memberEntity = member;
                break;
            }
        }

        if(memberEntityList.isEmpty() || memberEnList.isEmpty()) {
            memberDTO.setProvider(provider);
            memberDTO.setUserId(email);
            memberDTO.setUsername(username);
            memberDTO.setRole(role);
            memberDTO.setBlock("0");
            memberEntity = memberDTO.createMemberEntity(memberDTO);
            memberRepository.save(memberEntity);
            session.setAttribute("newUser", memberEntity);
        }
        return new LocalUserDetails(memberEntity, oAuth2User.getAttributes());
    }
}
