package com.example.MySpringSecurity.repository;

import com.example.MySpringSecurity.entity.MemberHasRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHasRoleRepository extends JpaRepository<MemberHasRole, Integer> {
}
