package com.jctech.eshop;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author jc
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserTokenTest extends IntegrationTestBase {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void existentUserGetTokenAndAuthentication() throws Exception {

		String token = this.getSessionToken(this.username, this.password);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user?");
		requestBuilder
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.param("user_id", "demo")
				.header("Authorization", "Bearer " + token);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	public void nonexistentUserCannotGetToken() throws Exception {
		String username = "demo";
		String password = "password";

		String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/session").content(body)).andExpect(status().isUnauthorized())
				.andReturn();
	}

}
