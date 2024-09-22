package com.example.MySpringSecurity.service.impl;

import com.example.MySpringSecurity.entity.MemberHasRole;
import com.example.MySpringSecurity.entity.Role;
import com.example.MySpringSecurity.repository.MemberHasRoleRepository;
import com.example.MySpringSecurity.repository.RoleRepository;
import com.example.MySpringSecurity.service.SubscriptionService;
import com.example.MySpringSecurity.vo.SubscriptionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MemberHasRoleRepository memberHasRoleRepository;

    @Override
    public String subscribe(SubscriptionVo vo) {
        //先檢查此member是否訂閱過成為vip
        List<Role> roleList = roleRepository.getRoleListByMemberId(vo.getMemberId());
        boolean isSubscribed = checkSubscribeStatus(roleList);

        if (isSubscribed) {
            return "已訂閱過，不需重複訂閱";
        } else {
            Role vipRole = roleRepository.findByRoleName(Role.VIP_MEMBER)
                    .orElseThrow(() -> new RuntimeException("VIP_MEMBER權限不存在"));
            MemberHasRole memberHasRole = new MemberHasRole();
            memberHasRole.setMemberId(vo.getMemberId());
            memberHasRole.setRoleId(vipRole.getRoleId());
            memberHasRoleRepository.save(memberHasRole);

            return "訂閱成功！請刪除Cookie重新登入";
        }
    }

    @Override
    public String unsubscribe(SubscriptionVo vo) {
        //先檢查此member是否訂閱過成為vip
        List<Role> roleList = roleRepository.getRoleListByMemberId(vo.getMemberId());
        boolean isSubscribed = checkSubscribeStatus(roleList);

        if (isSubscribed) {
            Role vipRole = roleRepository.findByRoleName(Role.VIP_MEMBER)
                    .orElseThrow(() -> new RuntimeException("VIP_MEMBER權限不存在"));

            memberHasRoleRepository
                    .findByMemberIdAndRoleId(vo.getMemberId(), vipRole.getRoleId())
                    .ifPresent(memberHasRole -> memberHasRoleRepository.delete(memberHasRole));

            return "取消訂閱成功！請刪除Cookie重新登入";
        } else {
            return "尚未訂閱，無法進行取消訂閱的動作";
        }
    }

    private boolean checkSubscribeStatus(List<Role> roleList) {
        boolean isSubscribed = false;

        for (Role role : roleList) {
            if (Role.VIP_MEMBER.equals(role.getRoleName())) {
                isSubscribed = true;
            }
        }
        return isSubscribed;
    }
}
