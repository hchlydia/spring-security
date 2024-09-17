package com.example.MySpringSecurity.service.impl;

import com.example.MySpringSecurity.entity.Member;
import com.example.MySpringSecurity.repository.MemberRepository;
import com.example.MySpringSecurity.service.MemberService;
import com.example.MySpringSecurity.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public Member create(MemberVo vo) {
        Member member = new Member();
        member.setEmail(vo.getEmail());
        member.setPassword(vo.getPassword());
        member.setName(vo.getName());
        member.setAge(vo.getAge());

        return memberRepository.save(member);
    }
}
