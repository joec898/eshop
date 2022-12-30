package com.jctech.eshop.ttd.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jctech.eshop.model.user.ERole;
import com.jctech.eshop.model.user.User;
import com.jctech.eshop.repo.UserRepo;
import com.jctech.eshop.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TtdUserServiceTest {

	private User user;

	@Mock
	private UserRepo userRepo;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	public void setUp() {
		user = new User("user2", "user2", ERole.USER, "Mark", "Smith", true);
	}

	@Test
	public void givenUserId_whenFindById_thenReturnUser() throws Exception {
		given(userRepo.findOneByUserId(user.getUserId())).willReturn(Optional.of(user));
		User u = userService.getUserByUserId(user.getUserId());

		assertNotNull(u);
		assertThat(u.getFirstName()).isEqualTo(user.getFirstName());
	}

}
