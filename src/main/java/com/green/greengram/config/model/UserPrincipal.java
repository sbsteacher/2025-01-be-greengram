package com.green.greengram.config.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class UserPrincipal implements UserDetails {
    private final long memberId;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(long memberId, List<String> roles) {
        this.memberId = memberId;
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for(String role : roles){
            list.add(new SimpleGrantedAuthority(role));
        }
        this.authorities = list;

        //this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return null; }
}
