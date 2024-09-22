package com.example.MySpringSecurity.security;

import com.example.MySpringSecurity.entity.Member;
import com.example.MySpringSecurity.entity.Role;
import com.example.MySpringSecurity.repository.MemberRepository;
import com.example.MySpringSecurity.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        String email = member.getEmail();
        String password = member.getPassword();

        //權限
        List<Role> roleList = roleRepository.getRoleListByMemberId(member.getMemberId());
        List<GrantedAuthority> authorities = convertToAuthorities(roleList);

        //轉換Spring Security指定的User格式
        return new User(email, password, authorities);
    }

    private List<GrantedAuthority> convertToAuthorities(List<Role> roleList) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return authorities;
    }
}
