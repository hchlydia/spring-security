package com.example.MySpringSecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String NORMAL_MEMBER = "ROLE_NORMAL_MEMBER";
    public static final String VIP_MEMBER = "ROLE_VIP_MEMBER";
    public static final String MOVIE_MANAGER = "ROLE_MOVIE_MANAGER";
}
