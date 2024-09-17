package com.example.MySpringSecurity;

import com.example.MySpringSecurity.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MySpringSecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void register() throws Exception {
		Member member = new Member();
		member.setEmail("test1@gmail.com");
		member.setPassword("111");
		member.setName("Test 1");
		member.setAge(30);

		String json = objectMapper.writeValueAsString(member);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/register")
				.header("Content-Type", "application/json")
				.content(json);

		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk());

		//測試用這個新創建的帳號密碼登入
		RequestBuilder loginRequestBuilder = MockMvcRequestBuilders
				.post("/userLogin")
				.with(httpBasic("test1@gmail.com", "111"));

		mockMvc.perform(loginRequestBuilder)
				.andExpect(status().isOk());
	}

	@Test
	public void userLogin() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/userLogin") //用POST方法請求url
				.with(httpBasic("normal@gmail.com", "normal")); //用Spring Security test提供的方法添加使用者帳號密碼的值

		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk());
	}
}
