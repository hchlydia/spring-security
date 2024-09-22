package com.example.MySpringSecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "member_has_role")
@Getter
@Setter
public class MemberHasRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_has_role_id")
    private Integer memberHasRoleId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "role_id")
    private Integer roleId;
}
