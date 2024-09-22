package com.example.MySpringSecurity.controller;

import com.example.MySpringSecurity.service.SubscriptionService;
import com.example.MySpringSecurity.vo.SubscriptionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<Map<String, Object>> subscribe(@RequestBody SubscriptionVo vo) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", subscriptionService.subscribe(vo));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Map<String, Object>> unsubscribe(@RequestBody SubscriptionVo vo) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", subscriptionService.unsubscribe(vo));
        return ResponseEntity.ok(map);
    }
}
