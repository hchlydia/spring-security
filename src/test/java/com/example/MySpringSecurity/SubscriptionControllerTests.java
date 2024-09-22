package com.example.MySpringSecurity;

import com.example.MySpringSecurity.vo.SubscriptionVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SubscriptionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional //Test的@Transactional: 測試結束後會復原資料庫操作，以免影響其他單元測試
    @Test
    public void subscribe() throws Exception {
        SubscriptionVo vo = new SubscriptionVo();
        vo.setMemberId(2);

        String json = objectMapper.writeValueAsString(vo);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/subscribe")
                .header("Content-Type", "application/json")
                .content(json)
                .with(httpBasic("normal@gmail.com", "normal"))
                .with(csrf());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));


        // 訂閱成功後可以觀看 vip movie
        RequestBuilder vipRequestBuilder = MockMvcRequestBuilders
                .post("/movie/vip")
                .with(httpBasic("normal@gmail.com", "normal"))
                .with(csrf());

        mockMvc.perform(vipRequestBuilder)
                .andExpect(status().is(200));
    }

    @Transactional
    @Test
    public void unsubscribe() throws Exception {
        SubscriptionVo vo = new SubscriptionVo();
        vo.setMemberId(3);

        String json = objectMapper.writeValueAsString(vo);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/unsubscribe")
                .header("Content-Type", "application/json")
                .content(json)
                .with(httpBasic("vip@gmail.com", "vip"))
                .with(csrf());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));


        // 取消訂閱後無法觀看 vip movie
        RequestBuilder vipRequestBuilder = MockMvcRequestBuilders
                .post("/movie/vip")
                .with(httpBasic("vip@gmail.com", "vip"))
                .with(csrf());

        mockMvc.perform(vipRequestBuilder)
                .andExpect(status().is(403));
    }
}
