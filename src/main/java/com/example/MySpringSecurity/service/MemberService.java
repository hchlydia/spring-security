package com.example.MySpringSecurity.service;

import com.example.MySpringSecurity.entity.Member;
import com.example.MySpringSecurity.vo.MemberVo;

public interface MemberService {

    Member create(MemberVo vo) throws Exception;
}
