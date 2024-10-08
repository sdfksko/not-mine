package com.example.nestco.config.local;


import com.example.nestco.models.entity.Member;
import com.example.nestco.dao.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LocalUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String userId = username;

        Member memberEntity = memberRepository.findByUserId(userId);

        if(memberEntity == null) {
            return null;
        }

        if(memberEntity.getBlock().equals("1")) {
            return null;
        }

        return new LocalUserDetails(memberEntity);
    }
}
