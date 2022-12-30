package com.jctech.eshop;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort; 
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jctech.eshop.api.session.SessionResponse;

public abstract class IntegrationTestBase {

	@org.springframework.boot.web.server.LocalServerPort
	protected int port;

	@Autowired
	protected TestRestTemplate restTemplate;

	@Autowired
	private MockMvc mockMvc;

//	@Autowired
//	protected WebTestClient webTestClient;

	protected HttpHeaders headers = new HttpHeaders();
	

	protected HttpEntity<String> entity = new HttpEntity<String>(null, headers);

	protected String baseUrl = "http://localhost:";

	////////// session token
	protected String username = "demo";
	protected String password = "demo";

	public String getSessionToken(String userName, String password) {
		String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

		try {
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/session").content(body))
					.andExpect(status().isOk()).andReturn();

			String response = result.getResponse().getContentAsString();
			String token = this.retrieveToken(response);
			return token;
		} catch (Exception e) {
		}
		return null;
	}

	private String retrieveToken(String response) {
		ObjectMapper objectMapper = new ObjectMapper();
		response = response.replace("\r\n", "");
		try {
			SessionResponse resp = objectMapper.readValue(response, SessionResponse.class);
			return resp.getItem().getToken();
		} catch (Exception e) {
		}
		return null;
	}
}
