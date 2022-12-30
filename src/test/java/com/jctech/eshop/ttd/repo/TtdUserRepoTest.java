package com.jctech.eshop.ttd.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.jctech.eshop.model.user.ERole;
import com.jctech.eshop.model.user.User;
import com.jctech.eshop.repo.UserRepo;

/**
 * @author jc
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TtdUserRepoTest {
	private User user;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	public void setUp() {
		user = new User("user2", "user2", ERole.USER, "Mark", "Smith", true);
	}

	@Test
	public void givenUserId_whenFindById_thenReturnUser() throws Exception {
		User savedUser = entityManager.persistAndFlush(user);
		// User savedUser = userRepo.save(user);
		User u = userRepo.findOneByUserId(user.getUserId()).get();
		userRepo.delete(savedUser);

		Assertions.assertThat(u).isNotNull();
		Assertions.assertThat(u.getFirstName()).isEqualTo(savedUser.getFirstName());
	}

	@Test
	public void givenUser_whenSave_thenReturnSavedUser() throws Exception {
		User savedUser = userRepo.save(user);
		userRepo.delete(savedUser);

		Assertions.assertThat(savedUser).isNotNull();
		Assertions.assertThat(savedUser.getFirstName()).isEqualTo(user.getFirstName());
	}
}
