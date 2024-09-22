package com.example.MySpringSecurity.controller;

import com.example.MySpringSecurity.entity.Member;
import com.example.MySpringSecurity.service.MemberService;
import com.example.MySpringSecurity.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody MemberVo vo) throws Exception {
        String hashedPassword = passwordEncoder.encode(vo.getPassword());
        vo.setPassword(hashedPassword);

        Member member = memberService.create(vo);

        Map<String, Object> map = new HashMap<>();
        map.put("isSuccess", true);
        return ResponseEntity.ok(map);
    }

    //"/login"已被Spring Security使用
    @PostMapping("/userLogin")
    public ResponseEntity<Map<String, Object>> login(Authentication authentication) {
        //帳號密碼驗證交由Spring Security處理，能執行到這裡表示登入成功

        Map<String, Object> map = new HashMap<>();

        //取得帳號
        String name = authentication.getName();

        //取得權限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        map.put("isSuccess", true);
        map.put("userName", name);
        map.put("authorities", authorities);

        return ResponseEntity.ok(map);
    }
}
