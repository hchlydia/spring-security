package com.example.MySpringSecurity.repository;

import com.example.MySpringSecurity.entity.MemberHasRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberHasRoleRepository extends JpaRepository<MemberHasRole, Integer> {

    Optional<MemberHasRole> findByMemberIdAndRoleId(Integer memberId, Integer roleId);
}
