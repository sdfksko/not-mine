package com.example.nestco.config.local;

import com.example.nestco.models.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class LocalUserDetails implements UserDetails, OAuth2User {

    private Member memberEntity;
    private Map<String, Object> attributes;

    public LocalUserDetails(Member memberEntity) {
        this.memberEntity = memberEntity;
    }

    public LocalUserDetails(Member memberEntity, Map<String, Object> attributes) {
        this.memberEntity = memberEntity;
        this.attributes = attributes;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes();
    }

    private Map<String, Object> attributes() {
        return attributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberEntity.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return memberEntity.getUserPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

}
