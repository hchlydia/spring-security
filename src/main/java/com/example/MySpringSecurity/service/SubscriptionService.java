package com.example.MySpringSecurity.service;

import com.example.MySpringSecurity.vo.SubscriptionVo;

public interface SubscriptionService {

    String subscribe(SubscriptionVo vo);

    String unsubscribe(SubscriptionVo vo);
}
