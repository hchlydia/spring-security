package com.example.MySpringSecurity.controller;

import com.example.MySpringSecurity.entity.Member;
import com.example.MySpringSecurity.service.MemberService;
import com.example.MySpringSecurity.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public String register(@RequestBody MemberVo vo) {
        String hashedPassword = passwordEncoder.encode(vo.getPassword());
        vo.setPassword(hashedPassword);

        Member member = memberService.create(vo);

        return "註冊成功";
    }
}
