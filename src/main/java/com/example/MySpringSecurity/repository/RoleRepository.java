package com.example.MySpringSecurity.repository;

import com.example.MySpringSecurity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r " +
            "from Role r " +
            "left join MemberHasRole mhr on mhr.roleId = r.roleId " +
            "left join Member m on m.memberId = mhr.memberId " +
            "where m.memberId = :memberId " +
            "order by r.roleId")
    List<Role> getRoleListByMemberId(@Param("memberId") Integer id);

    Optional<Role> findByRoleName(String roleName);
}
