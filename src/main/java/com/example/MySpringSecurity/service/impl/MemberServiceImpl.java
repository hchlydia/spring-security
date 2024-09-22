package com.example.MySpringSecurity.service.impl;

import com.example.MySpringSecurity.entity.Member;
import com.example.MySpringSecurity.entity.MemberHasRole;
import com.example.MySpringSecurity.entity.Role;
import com.example.MySpringSecurity.repository.MemberHasRoleRepository;
import com.example.MySpringSecurity.repository.MemberRepository;
import com.example.MySpringSecurity.repository.RoleRepository;
import com.example.MySpringSecurity.service.MemberService;
import com.example.MySpringSecurity.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MemberHasRoleRepository memberHasRoleRepository;


    @Override
    @Transactional
    public Member create(MemberVo vo) throws Exception {
        Member member = new Member();
        member.setEmail(vo.getEmail());
        member.setPassword(vo.getPassword());
        member.setName(vo.getName());
        member.setAge(vo.getAge());

        member = memberRepository.save(member);

        //為新註冊會員新增預設的role
        Role normalRole = roleRepository.findByRoleName(Role.NORMAL_MEMBER)
                .orElseThrow(() -> new RuntimeException("NORMAL_MEMBER權限不存在"));

        MemberHasRole memberHasRole = new MemberHasRole();
        memberHasRole.setMemberId(member.getMemberId());
        memberHasRole.setRoleId(normalRole.getRoleId());
        memberHasRoleRepository.save(memberHasRole);

        return member;
    }
}
